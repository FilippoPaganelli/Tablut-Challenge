#!/bin/bash

#startup parameters: role timeout serverIP

echo "params: role (black or white), timeout (in seconds), serverIP"

role=$1
timeout=$2
serverIP=$3

realrole=$(echo "$role" | awk '{print tolower($0)}')

cat "ascii_art.txt"

ant $realrole -Darg1=$timeout -Darg2=$serverIP &
