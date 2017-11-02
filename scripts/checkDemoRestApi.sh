#!/bin/bash

for i in {1..50}
do
	 curl -o /dev/null -s -w %{time_total}\\n -H 'Cache-Control: no-cache' "http://localhost:8080/api/v1/demo/2?dummyquery$i"
	 sleep 1
done


# Bernouli is 0 for odd numbers
for i in {100..150}
do
	 curl -o /dev/null -s -w %{time_total}\\n -H 'Cache-Control: no-cache' "http://localhost:8080/api/v1/complexMaths/$i"
done