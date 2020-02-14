#!/bin/bash

source "$(dirname ${BASH_SOURCE})/functions.sh";

print_information;

if [[ $(is_release_branch) -eq 1 ]];
then
  echo "Release deployment will NOT be done because current branch $(retrieve_git_branch) is not ${release_branch}.";
  exit 0;
fi;

if [[ $(is_snapshot_version) -eq 0 ]];
then
  echo "Release deployment will NOT be done because project version $(retrieve_project_version) is not a release.";
  exit 0;
fi;

mvn deploy -Dmaven.test.skip=true
exit ${?};
