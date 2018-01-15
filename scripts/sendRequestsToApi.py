from os import listdir
from os.path import isfile, join
import requests
from requests.exceptions import ConnectionError
import random

# Constants
endpoint_url = "http://localhost:8080/api/v1/classify"
mypath="../images/"

onlyDirs = [f for f in listdir(mypath) if not isfile(join(mypath, f))]
imagePosition = 0
imageLocations = []
for smallDir in onlyDirs :
    nextDir = mypath + smallDir
    for imageLocation in listdir(nextDir) :
        if isfile(join(nextDir, imageLocation)) :
            imageLocations.insert(imagePosition, nextDir + "/" + imageLocation)
            imagePosition = imagePosition + 1
# Obtain a random image a post to the endpoint
#random_photo = random.choice(imageLocations)

# Send each image to the endpoint
for imageLocation in imageLocations :
    file = open(imageLocation, 'rb')
    file_contents = file.read()
    files = {'file': ("file", file_contents)}
    try :
        response = requests.post(endpoint_url, files=files)
        print(imageLocation)
        print(response.content)
    except ConnectionError:
        print("Issue Posting : " + imageLocation + " to " + endpoint_url)
