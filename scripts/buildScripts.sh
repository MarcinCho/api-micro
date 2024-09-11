#!/usr/bin/env bash

pwd
cd librarysearch/
mvn compile jib:build


pwd
cd ../api-pull
mvn clean install

# Here I'll have to do something about vite