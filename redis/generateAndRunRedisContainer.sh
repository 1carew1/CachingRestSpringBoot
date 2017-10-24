docker stop rediscontainer
docker rm rediscontainer
docker build -t redis_low_mem .
docker run --name  rediscontainer -p 6379:6379 -d redis_low_mem


# Note when the Cache Size is set to 1mb you can put 381 cubes in before it is full and needs to replace
# Note when the Cache Size is set to 2mb you can put 2151 cubes in before it is full and needs to replace
# Note when the Cache Size is set to 4mb you can put 5735 cubes in before it is full and needs to replace
# Note when the Cache Size is set to 8mb you can put 12903 cubes in before it is full and needs to replace
# Note when the Cache Size is set to 16mb you can put 27239 cubes in before it is full and needs to replace