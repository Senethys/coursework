TARGETS = list_mwe1 list_mwe2

all:	$(TARGETS)

OPTS =  -std=c99 -Wall -I ../../include -g

clean:
	-rm $(TARGETS)

list_mwe1: list_mwe1.c list.c
	gcc -o $@ $(OPTS) $^

list_mwe2: list_mwe2.c list.c
	gcc -o $@ $(OPTS) $^

memtest1: list_mwe1
	valgrind --leak-check=full --show-reachable=yes $<

memtest2: list_mwe2
	valgrind --leak-check=full --show-reachable=yes $<
