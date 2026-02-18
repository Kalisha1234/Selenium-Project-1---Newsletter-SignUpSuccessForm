#!/bin/bash

# Path to your log file
LOG_FILE="output.log"

ERROR_TYPE=$(grep -m 1 -oE "[a-zA-Z0-9\.]+(Error|Exception)" "$LOG_FILE")

EXPECTED=$(sed -n 's/.*expected: <\(.*\)> but was.*/\1/p' "$LOG_FILE")
ACTUAL=$(sed -n 's/.*but was: <\(.*\)>/\1/p' "$LOG_FILE")

# Output the results
echo "--- Test Failure Summary ---"
echo "Error Name: $ERROR_TYPE"
echo "Expected:   $EXPECTED"
echo "Actual:     $ACTUAL"

