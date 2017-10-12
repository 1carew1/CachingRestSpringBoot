#!/bin/bash

for i in {1..50}
do
	 curl -o /dev/null -s -w %{time_total}\\n http://localhost:8080/api/v1/demo/2
	 sleep 2
done