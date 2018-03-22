import requests
import os
from os import walk
import json
import sys
import datetime
from os import listdir
from os.path import isfile, join
from requests.exceptions import ConnectionError
import random

# Constants
base_url = "http://localhost:8080/api/v1/"
image_path = "classify"
batch_path = "batch"
check_cache = "checkcache"
images_dir = "../images/"

# Ensure no caching
request_headers = {
    'cache-control': 'private, max-age=0, no-cache'
}


# Obtain a random image a post to the endpoint
# random_photo = random.choice(imageLocations)

# Get the batch
def obtain_batch_id(setup_comment):
    response = requests.post(base_url + batch_path, data=setup_comment, headers=request_headers)
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


# Send Image for Classification
def obtain_classification(file_path, file_contents, batch_id):
    classification = None
    files = {'file': (file_path, file_contents)}
    image_endpoint = base_url + image_path + "/" + str(batch_id)
    try:
        response = requests.post(image_endpoint, files=files, headers=request_headers)
        classification = json.loads(response.content)
    except ConnectionError:
        print("Issue Posting : ", image_endpoint)
    return classification


def send_image_requests(file_list, batch_id):
    number_of_images_processed = 0
    number_of_images_not_processed = 0
    # Send each image to the endpoint
    for image_location in file_list:
        file_contents = obtain_file_contents(image_location)
        if (file_contents is not None):
            classification = obtain_classification(image_location, file_contents, batch_id)
            if (classification is not None):
                number_of_images_processed = number_of_images_processed + 1
                print (str(number_of_images_processed) + " " + image_location)
            else:
                number_of_images_not_processed = number_of_images_not_processed + 1
        else:
            print('File Contents was None :', image_location)
    print("Number of images processed successfully: ", number_of_images_processed)
    print("Number of images that failed to process: ", number_of_images_not_processed)


if (__name__ == "__main__"):
    script_start = datetime.datetime.now()
    print("Start Time : ", script_start)

    setup_comment = "Dry Run"
    if (len(sys.argv) >= 2):
        setup_comment = sys.argv[1]
    batch_id = obtain_batch_id(setup_comment)
    file_list = obtain_file_list(images_dir)
    send_image_requests(file_list, batch_id)

    script_end = datetime.datetime.now()
    print("Finish Time : " + str(script_end))

    time_diff = script_end - script_start
    time_diff_seconds = int(time_diff.total_seconds())
    time_diff_minutes = time_diff_seconds / 60
    print("Total time to run : " + str(time_diff_minutes) + " minutes")
