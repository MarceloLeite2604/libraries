#!/bin/bash

# Include guard.
if [ -z "${DEPLOY_FUNCTIONS_SH}" ];
then
  DEPLOY_FUNCTIONS_SH=1;
else
  return;
fi;

source "$(dirname ${BASH_SOURCE})/constants.sh";

retrieve_project_version() {
  echo "$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)";
}

retrieve_git_branch() {
  echo "${TRAVIS_BRANCH:-$(git rev-parse --abbrev-ref HEAD)}";
}

is_release_branch() {
  if [[ $(retrieve_git_branch) == ${release_branch} ]];
  then
    return 0;
  else
    return 1;
  fi;
}

is_snapshot_branch() {
  if [[ $(retrieve_git_branch) == ${snapshot_branch} ]];
  then
    return 0;
  else
    return 1;
  fi;
}

is_snapshot_version() {
  if [[ $(retrieve_project_version) =~ ${regex_check_snapshot} ]];
  then
    return 0;
  else
    return 1;
  fi;
}

print_information() {
  echo "Project version is $(retrieve_project_version).";
  echo "Git branch is $(retrieve_git_branch).";
}
