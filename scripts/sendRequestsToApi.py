import requests
import os
from os import walk
import json
import sys
import datetime
from requests.exceptions import ConnectionError
import random

# Constants
base_url = "http://localhost:8080/api/v1/"
classify_image_path = "classify"
batch_path = "batch"
check_cache_path = "checkcache"
images_dir = "../images/"

# Ensure no caching
request_headers = {
    'cache-control': 'private, max-age=0, no-cache'
}

request_headers_json = {
    'Content-type': 'application/json',
    'cache-control': 'private, max-age=0, no-cache'
}


class BatchInfo():
    cacheType = None
    cacheSizeMb = None
    evictionPolicy = None

    def __init__(self, cache_type, cache_size_mb, eviction_policy):
        self.cacheType = cache_type
        self.cacheSizeMb = cache_size_mb
        self.evictionPolicy = eviction_policy

# Obtain a random image a post to the endpoint
# random_photo = random.choice(imageLocations)

# Get the batch
def obtain_batch_id(batch_info):
    json_data_to_send = json.dumps(batch_info, default=lambda o: o.__dict__)
    response = requests.post(base_url + batch_path, data=json_data_to_send, headers=request_headers_json)
    resp_dict = json.loads(response.content)
    batch_id = resp_dict["id"]
    print("The batch id for this run is : ", batch_id)
    return batch_id


# Obtain all the files
def obtain_file_list(dirpath):
    file_list = []
    for (directory, dirnames, filenames) in walk(dirpath):
        for filename in filenames:
            file_list.append(directory + "/" + filename)
    return file_list


# Obtain File Contents from path
def obtain_file_contents(file_path):
    file_contents = None
    statinfo = os.stat(file_path)
    if statinfo.st_size < 10485760 and "gif" not in file_path and ".DS_Store" not in file_path:
        file = open(file_path, 'rb')
        file_contents = file.read()
    return file_contents

# Obtain Classification From JSON
def obtain_classification_from_json(the_json):
    classification = None
    if(the_json is not None):
        try :
            classification = json.loads(the_json)
        except ValueError:
            classification = None
    return classification


# Send an Image for Classification to the API
def obtain_classification(file_path, file_contents, batch_id, request_path):
    classification = None
    files = {'file': (file_path, file_contents)}
    image_endpoint = base_url + request_path + "/"
    if(batch_id is not None) :
        image_endpoint = image_endpoint + str(batch_id)
    try:
        response = requests.post(image_endpoint, files=files, headers=request_headers)
        classification = obtain_classification_from_json(response.content)
    except ConnectionError:
        print("Issue Posting : ", image_endpoint)
    return classification

# Send the Requests of all the images
def send_image_requests(file_list, batch_id, request_path):
    number_of_images_processed = 0
    number_of_images_not_processed = 0
    # Send each image to the endpoint
    for image_location in file_list:
        file_contents = obtain_file_contents(image_location)
        if (file_contents is not None):
            classification = obtain_classification(image_location, file_contents, batch_id, request_path)
            if (classification is not None):
                number_of_images_processed = number_of_images_processed + 1
                print (request_path, ": ", number_of_images_processed,  " ",  image_location, " Label :", classification['label'], " Probability : ", classification['probability'])
            else:
                number_of_images_not_processed = number_of_images_not_processed + 1
        else:
            print('File Contents was None :', image_location)
    print("Number of images processed successfully: ", number_of_images_processed)
    print("Number of images that failed to process: ", number_of_images_not_processed)

# Fill the Cache
def fill_cache(file_list, cache_size_mb) :
    print("Filling Cache. The cache size is : ", cache_size_mb, "MB")
    cache_size_bytes = float(cache_size_mb) * 1024 * 1024
    total_bytes_sent = 0
    number_of_images_processed = 0
    number_of_images_not_processed = 0
    for image_location in file_list:
        if(total_bytes_sent > cache_size_bytes) :
            break
        file_size_bytes = os.stat(image_location).st_size
        file_contents = obtain_file_contents(image_location)
        if (file_contents is not None):
            classification = obtain_classification(image_location, file_contents, None, classify_image_path)
            if (classification is not None):
                number_of_images_processed = number_of_images_processed + 1
                total_bytes_sent = total_bytes_sent + file_size_bytes
                print (classify_image_path, ": ", number_of_images_processed,  " ",  image_location, " Label :", classification['label'], " Probability : ", classification['probability'])
            else:
                number_of_images_not_processed = number_of_images_not_processed + 1
    print("Number of images cached successfully: ", number_of_images_processed)
    print("Number of images that failed to cached: ", number_of_images_not_processed)
    print("MB sent to fill cache : ", (total_bytes_sent/(1024*1024)))

# Complete the batch
def finish_batch(batch_id):
    response = requests.put(base_url + batch_path + "/" + str(batch_id))
    resp_dict = json.loads(response.content)
    completion_date = resp_dict["endDate"]
    print("Batch Completion Date :", datetime.datetime.fromtimestamp(completion_date/1000).strftime('%Y-%m-%d %H:%M:%S'))

if (__name__ == "__main__"):
    batch_info = None
    if (len(sys.argv) >= 4):
        cache_type = sys.argv[1]
        cache_size_mb = sys.argv[2]
        eviction_policy = sys.argv[3]
        batch_info = BatchInfo(cache_type, cache_size_mb, eviction_policy)
    else :
        raise Exception('In valid number of arguments should be : python3 sendRequestsToApi.py CACHE_TYPE CACHE_SIZE_MB EVICTION_POLICY')

    script_start = datetime.datetime.now()
    print("Start Time : ", script_start)

    file_pool = obtain_file_list(images_dir)

    # Fill the cache
    fill_cache(file_pool, batch_info.cacheSizeMb)

    # Create a batch
    batch_id = obtain_batch_id(batch_info)

    # Test the cache by classifying images
    send_image_requests(file_pool, batch_id, classify_image_path)
    # Check Whats left in Cache
    send_image_requests(file_pool, batch_id, check_cache_path)
    # Complete the Batch
    finish_batch(batch_id)

    script_end = datetime.datetime.now()
    print("Finish Time : ", script_end)

    time_diff = script_end - script_start
    time_diff_seconds = int(time_diff.total_seconds())
    time_diff_minutes = time_diff_seconds / 60
    print("Total time to run : ", time_diff_minutes, " minutes")
