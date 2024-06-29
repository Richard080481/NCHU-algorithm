#!/bin/bash
declare -a TESTCASES=("testcase1" "testcase2")
BIN_DIR=$1
var=$2
TEST_DIR=testdata

GREEN="\033[0;32m"
RED="\033[0;31m"
NC="\033[0m"

# Define the test cases to loop through

printf "Running ${var}...\n";
# Loop through each test case
for TESTCASE in "${TESTCASES[@]}"; do
    OUTNAME="${TEST_DIR}/${var}.${TESTCASE}.out"
    GOLDNAME="${TEST_DIR}/${TESTCASE}.gold"
    INNAME="${TEST_DIR}/${TESTCASE}.in"
    java -cp ${BIN_DIR} ${var} ${INNAME} > ${OUTNAME}

    if diff ${OUTNAME} ${GOLDNAME} > /dev/null; then
        printf "${TESTCASE}... ${GREEN}pass${NC}\n"
        rm -f ${OUTNAME}
    else
        printf "${TESTCASE}... ${RED}fail${NC}\n"
        diff ${OUTNAME} ${GOLDNAME}
    fi
done
