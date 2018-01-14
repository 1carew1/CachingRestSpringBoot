#!/usr/bin/env bash
BASE_DIR="../images/"
for file in "${BASE_DIR}"*; do
  NEXT_FOLDER=$(basename "$file")
  for image in "$BASE_DIR${NEXT_FOLDER}/"*; do
          FILE_PATH="$BASE_DIR$NEXT_FOLDER/$(basename "$image")"
          curl -k -i -H 'Cache-Control: no-cache' -H  "Content-Type=multipart/form-data" -F "file=@\"$FILE_PATH\"" -X POST http://localhost:8080/api/v1/classify
  done
done