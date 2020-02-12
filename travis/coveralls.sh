#!/bin/bash

set -x;
mvn verify jacoco:report-aggregate coveralls:report
result=${?};
set +x;

return ${result};
