TARGETS = int_list_mwe

all:	$(TARGETS)

OPTS =  -std=c99 -Wall -I ../../include -g

clean:
	-rm $(TARGETS)

int_list_mwe: int_list_mwe.c int_list.c
	gcc -o $@ $(OPTS) $^

memtest: int_list_mwe
	valgrind --leak-check=full --show-reachable=yes $<
