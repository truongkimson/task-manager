#!/bin/bash
#startup.sh

./wait-for-it.sh mysql1:3306
java -jar task-manager.jar db migrate config.yml
java -jar task-manager.jar server config.yml
