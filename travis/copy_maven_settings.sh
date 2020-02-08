#!/bin/sh

set -x;
cp travis/settings.xml ${HOME}/.m2/settings.xml;
cp travis/settings-security.xml ${HOME}/.m2/settings-security.xml;
set +x;

exit 0;
