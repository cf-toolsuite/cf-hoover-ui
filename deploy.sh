#!/usr/bin/env bash

set -e

export APP_NAME=cf-hoover-ui

cf push --no-start
cf create-service p-service-registry standard $APP_NAME-registry
cf bind-service $APP_NAME $APP_NAME-registry
cf start $APP_NAME