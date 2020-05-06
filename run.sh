#!/bin/sh

i=0
echo "Starting $i"
while java -Xmx256m -cp unknown-currency-all.jar de.nwoehler.unknown.currency.Main;
do
  i=$((i+1));
  echo "Starting $i"
done

