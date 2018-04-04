#!/bin/bash
# Only run within this directory
# May take some time to run
./googliser.sh "kittens"
./googliser.sh "cats"
./googliser.sh "puppies"
./googliser.sh "dogs"
./googliser.sh "animals"
./googliser.sh "lions"
./googliser.sh "tigers"
./googliser.sh "bears"
./googliser.sh "planet"
./googliser.sh "house"
python3 removeUnwatedFiles.py "../images"
git clone https://github.com/1carew1/image_uniqueifier
python3 image_uniqueifier/image_uniqueify.py "../images"
python3 reduceDirectorySize.py "../images"