#!/bin/bash

source "$(dirname ${BASH_SOURCE})/functions.sh";

print_information;

if [[ ! is_snapshot_branch ]];
then
  >&2 echo "Snapshot deployment will NOT be done because current branch $(retrieve_git_branch) is not ${snapshot_branch}.";
  exit 1;
fi;

if [[ ! is_snapshot_version ]];
then
  >&2 echo "Snapshot deployment will NOT be done because project version $(retrieve_project_version) is not a snapshot.";
  exit 1;
fi;

mvn deploy -Dmaven.test.skip=true
exit ${?};
