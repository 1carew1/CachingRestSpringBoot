docker stop rediscontainer
docker rm rediscontainer
docker build -t redis_low_mem .
docker run --name  rediscontainer -p 6379:6379 -d redis_low_mem