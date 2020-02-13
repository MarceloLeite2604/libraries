#!/bin/bash

regex_check_snapshot="-SNAPSHOT$";
snapshot_branch="develop";
release_branch="master";
project_version=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout);
git_branch=$(git rev-parse --abbrev-ref HEAD);

echo "Project version is ${project_version}.";
echo "Git branch is ${git_branch}.";

if [[ ${git_branch} != ${release_branch} ]] && [[ ${git_branch} != ${snapshot_branch} ]];
then
  echo "Current branch ${git_branch} is not snapshot (${snapshot_branch}) nor release (${release_branch}) branch. Deploy will not be done."
  exit 1;
fi;
 
if [[ ${git_branch} == ${release_branch} ]] && [[ ${project_version} =~ ${regex_check_snapshot} ]];
then
  >&2 echo "Cannot deploy a snapshot version on \"${release_branch}\" branch.";
  exit 1;
fi;

if [[ ${git_branch} == ${snapshot_branch}} ]] && [[ ! ${project_version} =~ ${regex_check_snapshot} ]];
then
  >&2 echo "Cannot deploy a release version on \"${snapshot_branch}\" branch.";
  exit 1;
fi;

echo mvn deploy -Dmaven.test.skip=true
exit ${?};
