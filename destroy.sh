#!/usr/bin/env bash

set -x

export APP_NAME=cf-hoover-ui

cf app ${APP_NAME} --guid

if [ $? -eq 0 ]; then
	cf stop $APP_NAME
	cf delete $APP_NAME -r -f
else
    echo "$APP_NAME does not exist"
fi
