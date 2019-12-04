#!/bin/bash

# set -e

# Start parity and wait to initialize
docker-compose up -d parity
sleep 2

# Start ganache
docker-compose up -d ganache

# Run the tests
mvn test
TEST_RESULT=$?

# Cleanup
docker-compose down

exit $TEST_RESULT
