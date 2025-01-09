TARGETS = int_list_array_mwe

all:	$(TARGETS)

OPTS =  -std=c99 -Wall -I ../../include -g

clean:
	-rm $(TARGETS)

int_list_array_mwe: int_list_array_mwe.c int_list_array.c
	gcc -o $@ $(OPTS) $^

memtest: int_list_array_mwe
	valgrind --leak-check=full --show-reachable=yes $<
