#!/bin/bash

# Print a colon-separated list of all the jar files in the given directories.

for dir in "$@"
do
  for jar in "$dir"/*.jar
  do
    if [ -n "$R" ]
    then
      R="$R:"
    fi
    R="$R${jar}"
  done
done

echo "$R"
