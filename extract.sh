#!/bin/bash

# Path to your log file
LOG_FILE="output.log"

# 1. Extract the Error Type
# Looks for the first occurrence of a word ending in 'Error' or 'Exception'
ERROR_TYPE=$(grep -m 1 -oE "[a-zA-Z0-9\.]+(Error|Exception)" "$LOG_FILE")

# 2. Extract the Expected value
# Matches the text between 'expected: <' and '>'
EXPECTED=$(sed -n 's/.*expected: <\(.*\)> but was.*/\1/p' "$LOG_FILE")

# 3. Extract the Actual value
# Matches the text after 'but was: <' and before the closing '>'
ACTUAL=$(sed -n 's/.*but was: <\(.*\)>/\1/p' "$LOG_FILE")

# Output the results
echo "--- Test Failure Summary ---"
echo "Error Name: $ERROR_TYPE"
echo "Expected:   $EXPECTED"
echo "Actual:     $ACTUAL"

echo "TEST_ERROR_TYPE=$ERROR_TYPE" >> $GITHUB_ENV
echo "TEST_EXPECTED=$EXPECTED" >> $GITHUB_ENV
echo "TEST_ACTUAL=$ACTUAL" >> $GITHUB_ENV
