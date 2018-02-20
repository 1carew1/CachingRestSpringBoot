# Spring Boot Cache Testing

## Introduction
The purpose of this project is to be used as part of my Dissertation Experiment for Msc. in Communication Software.

I would like to give credit to the following : 

 - teracow who wrote the original Googliser Scripts for obtaining Images from Google. The repo can be found [here](https://github.com/teracow/googliser)
 - florind who has a sample Spring Boot application utilizing Tensor Flow for classifying images. The repo can be found [here](https://github.com/florind/inception-serving-sb)

## Basic Dev Setup

A DB will be required, if you want to use the one in place you should run :

- docker create -v /var/lib/postgresql/data --name spring_app_data postgres:10.0
- docker run --name postgresdb -p 5432:5432 --volumes-from spring_app_data -e  POSTGRES_PASSWORD=password   -d postgres:10.0

You will then need to create the relevant user in this instance 

- docker exec -it postgresdb su postgres -c 'psql'
- CREATE ROLE dbuser WITH LOGIN PASSWORD 'password' ;
- ALTER ROLE dbuser CREATEDB;
- CREATE database cachetest;

If you want to use Redis, have the projct cloned and be in the root of the project :

- cd redis
- \# Edit the redis.conf as appropriate
- ./generateAndRunRedisContainer.sh

You should now have the DB and Redis Running.

If importing the project into Intellij ensure to edit the main configuration and set active profile to `dev`. 
For any other IDE or running the project locally you may need to set a spring parameter `-Dspring.profiles.active=dev`.

To run the project through a terminal, ensure you have Java installed and that the machine is 64 bit with a 64 bit OS and run :

```SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun```

## Obtaining Test Images
To download test images cd in scripts and run 

```./generateTestImages.sh```

This may take up serveral GB of storage.
Also if you are using and IDE make sure to mark the images directory as excluded to prevent overly long indexing of the project.

## Sending Request to the API
The API is not secured and can be POSTed to at `/api/v1/classify` with an image as a body.

A python script is available to fire multiple photos at the endpoint (you may need to change the url into this pythong script).
Note you will need python installed for this.

- cd scripts
- python sendRequestsToApi.py

This will write some information on the image processing to DB (when it started/finished, processing time ns (probably only ms accurate), cache hit t/f).

