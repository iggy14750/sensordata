MAIN_SRC := src/main
TEST_SRC := src/test
HEADERS  := -Iinclude

default: src/main/main.cpp bin
	gcc $(MAIN_SRC)/main.cpp $(HEADERS) -o bin/main

bin:
	mkdir bin