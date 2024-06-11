#!/bin/bash

#firt line (above) is shebang, '#!pathtoshell'
#indicating that this file should be executed via bash shell
read testcase
echo "Test ChatFriend" `date`
echo "----Test:"$testcase "----------"

#compile all java files
javac -cp ../src/main/java ../src/main/java/*.java
          #path               file to compile

java -classpath ../out/production/Duke- ChatFriend<input.txt>output.txt
          #path                         class to interpret


diff output.txt EXPECTED.TXT >/dev/null  #/dev/null is a special device which discard all data sent to it
if [ $? -eq 0 ];  #check the exit status of diff by checking $?
then
   echo "Test result:PASSED"
   exit 0
else
   echo "Test result:FAILED"
   exit 1
fi

