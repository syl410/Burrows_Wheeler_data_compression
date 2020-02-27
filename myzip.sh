#!/bin/bash

# check if user run command correctly
if [ "$#" -ne 3 ]; then
    echo "ERROR, please run command with following format:"
	echo "zip:   bash myzip.sh -zip <zip_name> <file_name>"
	echo "unzip: bash myzip.sh -unzip <zip_name> <file_name>"
	exit
fi

option=$1
zip_name=$2
file_name=$3

# check option
isZip=false
if [ $option == "-zip" ]; then
	isZip=true
elif [ $option == "-unzip" ]; then
	isZip=false
else
	echo "ERROR, incorrect option"
	echo "zip:   bash myzip.sh -zip <zip_name> <file_name>"
	echo "unzip: bash myzip.sh -unzip <zip_name> <file_name>"
	exit
fi

### zip
if $isZip; then
	# check file size
	actualsize=$(wc -c <"$file_name")
	if [ $actualsize -gt 50000001 ] ;then
		echo "WARN: Please try file < 50MB"
		exit
	fi
	
	# check if <file_name> is a file
	if [ -f $file_name ]; then
		# if <file_name> is empty, create <zip_name> directly
		if [ ! -s $file_name ]; then
			touch $zip_name
		# compress and create <zip_name>
		else
			# echo "Start compressing..."
			java BurrowsWheeler - < $file_name | java MoveToFront - | java Huffman - > $zip_name
			# echo "done!"
		fi
	else
		echo "ERROR: $file_name doesn't exist or isn't a file"
	fi
### unzip
else
	# check if <zip_name> is a file
	if [ -f $zip_name ]; then
		# if <zip_name> is empty, create <file_name> directly
		if [ ! -s $zip_name ]; then
			touch $file_name
		# decompress and create <file_name>
		else
			# echo "Start decompressing..."
			java Huffman + < $zip_name | java MoveToFront + | java BurrowsWheeler + > $file_name
			# echo "done!"
		fi
	else
		echo "ERROR: $zip_name doesn't exist or isn't a file"
	fi
fi
