# Spring Boot Cache Testing

## Table of contents

   * [Introduction](#introduction)
   * [Dev Setup](#dev-setup)
      * [Importing Project](#importing-project)
      * [Database](#database)
      * [Cache](#cache)
   * [Obtaining Test Images ](#obtaining-test-images )
   * [Sending Requests](#sending-requests)
      * [The API](#the-api)
      * [Recommended API Approach](#recommended-api-approach)
      * [Python](#python)
## Introduction
The purpose of this project is to obtain cache performance data as to investigate cache performance for varying eviction algorithms and cache sizes.

I would like to give credit to the following : 

 - teracow who wrote the original Googliser Scripts for obtaining Images from Google. The repo can be found [here](https://github.com/teracow/googliser)
 - florind who has a sample Spring Boot application utilizing Tensor Flow for classifying images. The repo can be found [here](https://github.com/florind/inception-serving-sb)

## Dev Setup
### Importing Project
If importing the project into Intellij ensure to edit the main configuration and set active profile to `dev`. 
For any other IDE or running the project locally you may need to set a spring parameter `-Dspring.profiles.active=dev`.

To run the project through a terminal, ensure you have Java installed and run :

```SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun```

Note tensorflow will only work on a non ARM 64 bit OS.

### Database
Setup the database [here](documentation/DATABASESETUP.MD)

### Cache
Only Setup One

* Internal Caches
    * [no cache](documentation/caches/NOCACHE.MD)
    * [hazelcast](documentation/caches/HAZELCAST.MD)
    * [ehcache2](documentation/caches/EHCACHE2.MD)
    * [infinispan](documentation/caches/INFINISPAN.MD)

* External Caches
    * [couchbase](documentation/caches/COUCHBASE.MD)
    * [redis](documentation/caches/REDIS.MD)
    * [memcached](documentation/caches/MEMCACHED.MD)

## Obtaining Test Images
imagemagick with montage is required for obtaining the test images.
```
sudo apt-get install imagemagick
sudo apt-get install montage
```

To download test images cd into scripts and run 

```./generateTestImages.sh```

This script calls python3 scrips so python3 will need to be installed if it is not already.

This may take up serveral GB of storage.

Also if you are using an IDE make sure to mark the images directory as excluded to prevent indexing of these images.

## Sending Requests

### The API
The API is not secured. There are currently 5 endpoints :
- `/api/v1/classify` - POST
- `/api/v1/batch` - POST
- `/api/v1/batch/{batchId}` - PUT
- `/api/v1/classify/{batchId}` - POST
- `/api/v1//checkcache/{batchId}` - POST

#### /api/v1/classify
This endpoint is for classifying an image. Note currently only PNGs and JPEG/JPGs are being used for testing, GIFs are not supported.

POST a Multipart File (which should be an image) to this endpoint.
It will classify the image and return : 

```
{
    "label": "IMAGE_LABEL",
    "probability": PERCENT_PROBABILITY,
    "imageDataPoints" : "BYTE_ARRAY_OF_IMAGE"
}
```
Note this will place the image into the cache but will not store any performance metrics to the db.
Mostly this endpoint will be used for filling the cache before testing.

#### /api/v1/batch
This endpoint is for creating a test batch (a way of grouping images into the same test setup).

POST a Batch Info Object (as JSON) to this endpoint. a Batch Info Object will look like :

```
{
	"cacheType" : "ehcache",
	"cacheSizeMb" : "10",
	"evictionPolicy" : "LRU"
}
```

The response from this endpoint should look like :

```
{
    "id": 10,
    "startDate": 1522424289239,
    "cacheType": "ehcache",
    "cacheSizeMb": "10",
    "evictionPolicy": "LRU",
    "endDate": null
}
```

#### /api/v1/batch/{batchId}
This endpoint is for finishing the batch so no more images can be added to it.

PUT to this endpoint (it does not required a body). The response should have a populated end date :

```
{
    "id": 10,
    "startDate": 1522424289000,
    "cacheType": "ehcache",
    "cacheSizeMb": "10",
    "evictionPolicy": "LRU",
    "endDate": 1522424562712
}
```

#### /api/v1/classify/{batchId}
This endpoint is for classifying an image and storing cache metrics.

An active batch id is required (one without an end date set).
Other than the batchId this endpoint behaves the same as `/api/v1/classify` in that you POST a MultiPart File that is an image and get back the same object.
From the backend perspective calling this API will also save a Cache Performance entry.

#### /api/v1//checkcache/{batchId}
This is for checking if an image is already in cache. Used for seeing what keys are left in cache when finishing the batch.

For this endpoint you again POST a Multipart Image File and need an active batch id.
If the image is in cache it will return the same response as `/api/v1/classify` else it will return nothing/blank response.

This will save a Cache Remainder Entity which relates to the batch to the DB if the image was in cache.
### Recommended API Approach
The recommended way to test cache performance is to 

- Configure the desired cache
- Fill the cache - POST `/api/v1/classify`
- Create a batch - POST `/api/v1/batch`
- Run Test Sequence of Images - POST `/api/v1/classify/{batchId}`
- Once Sequence is finished check what is left in cache - POST `/api/v1//checkcache/{batchId}`
- Finish the batch - PUT `/api/v1/batch/{batchId}`

Note for filling, testing and checking remainder you will need to send many of each of those request.

### Python

A python script is available for sending the images obtained earlier and testing the cache. Note you may want to change URLs or file paths to suit your setup.

You will need python3 installed for this.

```
cd scripts
python3 sendRequestsToApi.py CACHE_TYPE CACHE_SIZE_MB EVICTION_POLICY
```

- `CACHE_TYPE` is the cache being used in the test : ehcache, redis, memcached etc.
- `CACHE_SIZE_MB` is the size of the cache in megabytes.
- `EVICTION_POLICY` is the cache eviction policy being used : LRU, LFU etc.

These 3 parameters need to be entered manually and should correspond to what is setup in the cache under test.

If you get any errors relating to imports you may need to install them such as :
```
sudo apt-get install python-setuptools
sudo easy_install pip
sudo pip install requests
```