#!/usr/bin/zsh

# refresh git repo
cd /home/ubuntu/java-qna
git fetch
local=`git rev-parse imjinbro`
origin=`git rev-parse origin/imjinbro`

if [ ! $local = $origin ]; then
  rm -rf ./build 
  git merge origin/imjinbro
  ./gradlew build
  target_pid=`pgrep -f java-qna` 
  kill -9 $target_pid
  java -jar ./build/libs/java-qna-1.0.0.jar &
fi

