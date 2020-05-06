#!/bin/sh

i=0
echo "Starting $i"
while java -Xmx256m -jar unknown-currency.jar;
do
  i=$((i+1));
  echo "Starting $i"
done

