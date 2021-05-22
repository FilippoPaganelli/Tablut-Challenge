#!/bin/bash

#startup parameters: role timeout serverIP

if [ "$#" -ne 3 ]; then
	echo "bad arguments, reminder:"
	echo "params: role (black or white), timeout (in seconds), serverIP"
	exit 1
fi

if ! [[ $2 =~ $re ]] ; then
   echo "timeout is not a number"
   exit 2
fi

role=$1
timeout=$2
serverIP=$3

realrole=$(echo "$role" | awk '{print tolower($0)}')

cat "ascii_art.txt"

if [ "$realrole" == "white" ]; then
    echo "white player selected"
    java -jar "jars/white.jar" "$timeout" "$serverIP" &
elif [ "$realrole" == "black" ]; then
    echo "black player selected"
    java -jar "jars/black.jar" "$timeout" "$serverIP" &
else
    echo "bad role argument, reminder:"
    echo "params: role (\"black\" or \"white\"), timeout (in seconds), serverIP"
fi

