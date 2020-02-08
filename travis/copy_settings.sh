#!/bin/sh

cp travis/settings.xml ${HOME}/.m2/settings.xml;
cp travis/settings-secure.xml ${HOME}/.m2/settings-secure.xml;

exit 0;
