import requests
import os
import json
import datetime
from os import listdir
from os.path import isfile, join
from requests.exceptions import ConnectionError
import random

script_start = datetime.datetime.now()
print("Start Time : " + str(script_start))

# Constants
base_url = "http://localhost:8080/api/v1/"
image_path = "classify"
batch_path = "batch"
mypath = "../images/"

onlyDirs = [f for f in listdir(mypath) if not isfile(join(mypath, f))]
imagePosition = 0
imageLocations = []
for smallDir in onlyDirs:
    nextDir = mypath + smallDir
    for imageLocation in listdir(nextDir):
        if isfile(join(nextDir, imageLocation)):
            imageLocations.insert(imagePosition, nextDir + "/" + imageLocation)
            imagePosition = imagePosition + 1
# Obtain a random image a post to the endpoint
# random_photo = random.choice(imageLocations)

# Get the batch
response = requests.post(base_url + batch_path)
resp_dict = json.loads(response.content)
batch_id =  str(resp_dict["id"])
print("The batch id for this run is : " + batch_id)

number_of_images_processed = 0
# Send each image to the endpoint
for imageLocation in imageLocations:
    statinfo = os.stat(imageLocation)
    # Ensure the images are not gif and are small enough to send
    if statinfo.st_size < 10485760 and "gif" not in imageLocation:
        file = open(imageLocation, 'rb')
        file_contents = file.read()
        files = {'file': (imageLocation, file_contents)}
        number_of_images_processed = number_of_images_processed + 1
        try:
            image_endpoint = base_url + image_path + "/" + batch_id
            response = requests.post(image_endpoint, files=files)
            # print(imageLocation)
            # print(response.content)
        except ConnectionError:
            print("Issue Posting : " + image_endpoint)

script_end = datetime.datetime.now()
print("Finish Time : " + str(script_end))
time_diff = script_end - script_start
time_diff_seconds = int(time_diff.total_seconds())
time_diff_minutes = time_diff_seconds / 60
print("Total time to run : " + str(time_diff_minutes) + " minutes")
print("Number of Images Processed : " + str(number_of_images_processed))
