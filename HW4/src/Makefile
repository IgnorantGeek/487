CXX      := -gcc
CXXFLAGS := -pedantic-errors -Wall
LDFLAGS  := -L/usr/lib -lm -pthread
BUILD    := .
OBJ_DIR  := $(BUILD)/objects
APP_DIR  := $(BUILD)
TARGET   := gnutella
INCLUDE  := -Iinclude/
SRC      := $(wildcard *.c)

OBJECTS := $(SRC:%.c=$(OBJ_DIR)/%.o)

all: build $(APP_DIR)/$(TARGET)

$(OBJ_DIR)/%.o: %.c
	@mkdir -p $(@D)
	$(CXX) $(CXXFLAGS) $(INCLUDE) -o $@ -c $<

$(APP_DIR)/$(TARGET): $(OBJECTS)
	@mkdir -p $(@D)
	$(CXX)	$(CXXFLAGS)	$(INCLUDE)	$(LDFLAGS)	-o	$(APP_DIR)/$(TARGET)	$(OBJECTS)

.PHONY: all build clean debug release

build:
	@mkdir -p $(APP_DIR)
	@mkdir -p $(OBJ_DIR)

debug: CXXFLAGS += -DEBUG -g
debug: all

release: CXXFLAGS += -O2
release: all

clean:
	-@rm -rvf $(OBJ_DIR)
	-@rm ./$(TARGET)
