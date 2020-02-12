#!/bin/bash

mvn jacoco:report coveralls:report
echo "bash <(curl -s https://codecov.io/bash)"
