TARGETS = int_array_1d_mwe

all:	$(TARGETS)

OPTS =  -std=c99 -Wall -I ../../include -g

clean:
	-rm $(TARGETS)

int_array_1d_mwe: int_array_1d_mwe.c int_array_1d.c
	gcc -o $@ $(OPTS) $^

memtest: int_array_1d_mwe
	valgrind --leak-check=full --show-reachable=yes $<
