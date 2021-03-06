#!/bin/bash

pushd tsap
find -name '*.pyc' -exec rm {} \;
find -name '*.pyo' -exec rm {} \;
popd

source /home/user/Documents/Projects/bep/setenv_x86.sh &&
./build.sh && #-p `pwd`/../../p4a-at3 &&
pushd ../tsap &&
ant clean debug &&
git checkout -- res/values/asset_versions.xml
adb uninstall org.tribler.tsap
adb push bin/MainActivity-debug.apk /sdcard/
adb shell pm install -l /sdcard/MainActivity-debug.apk
popd
./adb_killswift.sh &&
adb logcat -c &&
adb shell am start -n org.tribler.tsap/org.renpy.android.PythonActivity &&
echo Listening for logcat output.. &&
python pidcat/pidcat.py org.tribler.tsap -t
