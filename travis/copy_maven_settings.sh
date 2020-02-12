#!/bin/sh

set -x;
cp travis/settings.xml ${HOME}/.m2/settings.xml;
result=${?};
set +x;
return ${result};
