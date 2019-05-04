#!/usr/bin/env bash

set -e

export APP_NAME=cf-hoover-ui
export REGISTRY_NAME=hooverRegistry

cf push --no-start
cf bind-service $APP_NAME $REGISTRY_NAME
cf start $APP_NAME