#!/bin/bash -x

echo $*

printenv

initctl reload

exit 0