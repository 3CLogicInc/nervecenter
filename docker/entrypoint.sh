#!/bin/bash

if [ -n "$hostentry" ]
then
	IFS=',' read -r -a array <<< "$hostentry"
	for element in "${array[@]}"
	do
		echo "$element" >> /etc/hosts
	done
fi

cd /usr/share/app/deploy/

if [ -n "$debug" -a "$debug" == "true" ]
then
	if [ -n "$suspend" -a "$suspend" == "true" ]
	then
		if [-n "$Xmx" -a -n "$Xms" ]
		then
			java -jar -Xmx"$Xmx" -Xms"$Xms" sailor.jar
		else
			java -jar sailor.jar
		fi
	else
		if [-n "$Xmx" -a -n "$Xms" ]
		then
			java -jar -Xmx"$Xmx" -Xms"$Xms" sailor.jar
		else
			java -jar sailor.jar
		fi
	fi
else
	if [ -n "$Xmx" -a -n "$Xms" ]
	then
		java -jar -Xmx"$Xmx" -Xms"$Xms" sailor.jar
	else
		java -jar sailor.jar
	fi
fi
