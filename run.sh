#!/bin/sh

WORK_PATH="/var/app/current"
IP=$(hostname -I)
APPLICATION_NAME=srprs-${SPRING_PROFILES_ACTIVE}
#PINPOINT_ACTIVE=${PINPOINT_PROFILES_ACTIVE}

java \
-Dfile.encoding=UTF-8 \
-Duser.timezone=Asia/Seoul \
-jar build/libs/srprs-0.0.1-SNAPSHOT.jar