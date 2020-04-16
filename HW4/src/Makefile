CXX      := -g++
CXXFLAGS := -pedantic-errors -Wall
LDFLAGS  := -L/usr/lib -lm
BUILD    := .
OBJ_DIR  := $(BUILD)/objects
APP_DIR  := $(BUILD)
TARGET   := gnutella
INCLUDE  := -Iinclude/
SRC      := $(wildcard *.cc)

OBJECTS := $(SRC:%.cc=$(OBJ_DIR)/%.o)

all: build $(APP_DIR)/$(TARGET)

$(OBJ_DIR)/%.o: %.cc
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
