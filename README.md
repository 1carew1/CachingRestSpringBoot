# Spring Boot Cache Testing

## Introduction
The purpose of this project is to be used as part of my Dissertation Experiment for Msc. in Communication Software.

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

**Internal Caches**
- [no cache](documentation/caches/NOCACHE.MD)
- [hazelcast](documentation/caches/HAZELCAST.MD)
- [ehcache2](documentation/caches/EHCACHE2.MD)
- [infinispan](documentation/caches/INFINISPAN.MD)

**External Caches**
- [couchbase](documentation/caches/COUCHBASE.MD)
- [redis](documentation/caches/REDIS.MD)
- [memcached](documentation/caches/MEMCACHED.MD)

## Obtaining Test Images
You will need to install a program called montage and another imagemagick.
```
sudo apt-get install montage
sudo apt-get install imagemagick
```

To download test images cd in scripts and run 

```./generateTestImages.sh```

This may take up serveral GB of storage.
Also if you are using an IDE make sure to mark the images directory as excluded to prevent indexing of these images.

## Sending Request to the API
The API is not secured and can be POSTed to at `/api/v1/classify` with an image as a body.

A python script is available to fire multiple photos at the endpoint (you may need to change the url into this pythong script).
Note you will need python installed for this.

- cd scripts
- python sendRequestsToApi.py

If you get any errors relating to imports you may need to install them such as :
- sudo apt-get install python-setuptools
- sudo easy_install pip
- sudo pip install requests

This will write some information on the image processing to DB (when it started/finished, processing time ns (probably only ms accurate), cache hit t/f).

