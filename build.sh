#!/bin/bash

echo ********************************************************************
date
echo 'Checking for updates in the repo...'

cd /home/ubuntu/qna-deploy-test/

git fetch
local=`git rev-parse krapeaj`
remote=`git rev-parse origin/krapeaj`
if [ ! $local = $remote ]; then
	ps -ef | grep 'jar' | grep -v grep | awk '{print $2}' | xargs kill	
	git pull
	./gradlew clean
	./gradlew build
	java -jar ./build/libs/qna-deploy-test-1.0.0.jar &
else
	echo "No change has been made"
fi 

