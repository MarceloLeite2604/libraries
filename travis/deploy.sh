#!/bin/sh
set -x;
echo mvn deploy -Dmaven.test.skip=true
result=${?};
set +x;

exit ${result};
