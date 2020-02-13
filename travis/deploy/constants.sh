#!/bin/bash

# Include guard.
if [ -z "${DEPLOY_CONSTANTS_SH}" ];
then
    DEPLOY_CONSTANTS_SH=1;
else
    return;
fi;

regex_check_snapshot="-SNAPSHOT$";
snapshot_branch="develop";
release_branch="master";
