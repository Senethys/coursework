TARGETS = dlist_mwe1 dlist_mwe2

all:	$(TARGETS)

OPTS =  -std=c99 -Wall -I ../../include -g

clean:
	-rm $(TARGETS)

dlist_mwe1: dlist_mwe1.c dlist.c
	gcc -o $@ $(OPTS) $^

dlist_mwe2: dlist_mwe2.c dlist.c
	gcc -o $@ $(OPTS) $^

memtest1: dlist_mwe1
	valgrind --leak-check=full --show-reachable=yes $<

memtest2: dlist_mwe2
	valgrind --leak-check=full --show-reachable=yes $<
