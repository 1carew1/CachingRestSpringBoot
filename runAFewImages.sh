#!/usr/bin/env bash

#docker run -p 8080:8080 -t cachingrest:0.0.1-SNAPSHOT
#docker run -p 8081:8080 -t cachingrest:0.0.1-SNAPSHOT
docker run -p 8082:8080 -t cachingrest:0.0.1-SNAPSHOT

#Get into psql docker image
#docker exec -it 46e3073988e0  su postgres -c 'psql'
