TARGETS := HW01_4111064232_1 HW01_4111064232_2 HW01_4111064232_3
SRC_DIR := src
BIN_DIR := bin
TEST_DIR := testdata

# Define the targets and sources
SOURCES := $(patsubst %,$(SRC_DIR)/%.java,$(TARGETS))
COMMON_SRC := $(SRC_DIR)/ArrayData.java
CLASSES := $(patsubst %,$(BIN_DIR)/%.class,$(TARGETS))

# Colors
GREEN := \033[0;32m
RED := \033[0;31m
NC := \033[0m # No Color

# Default target
all: $(CLASSES)

# Clean up compiled files and output files
clean:
	rm -f $(BIN_DIR)/*.class
	rm -f $(TEST_DIR)/*.out

# Compile Java files
$(BIN_DIR)/%.class: $(SRC_DIR)/%.java $(COMMON_SRC)
	@mkdir -p $(BIN_DIR)
	javac -d $(BIN_DIR) $^

# Run the programs and check the output
run: all
	@$(foreach var,$(TARGETS),                                                   \
		printf "Running $(var)... ";                                             \
		java -cp $(BIN_DIR) $(var) > $(TEST_DIR)/$(var).out;                         \
		if diff $(TEST_DIR)/$(var).out $(TEST_DIR)/$(var).gold > /dev/null; then \
			printf "$(GREEN)pass$(NC)\n";                                        \
			rm -f $(TEST_DIR)/$(var).out;                                        \
		else                                                                     \
			printf "$(RED)fail$(NC)\n";                                          \
			diff $(TEST_DIR)/$(var).out $(TEST_DIR)/$(var).gold;                 \
		fi;)

# Phony targets
.PHONY: all clean run