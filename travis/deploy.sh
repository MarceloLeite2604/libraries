#!/bin/sh

echo mvn deploy -Dmaven.test.skip=true
return ${?};
