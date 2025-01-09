TARGETS = array_2d_mwe1 array_2d_mwe2

all:	$(TARGETS)

OPTS =  -std=c99 -Wall -I ../../include -g

clean:
	-rm $(TARGETS)

array_2d_mwe1: array_2d_mwe1.c array_2d.c
	gcc -o $@ $(OPTS) $^

array_2d_mwe2: array_2d_mwe2.c array_2d.c
	gcc -o $@ $(OPTS) $^

memtest1: array_2d_mwe1
	valgrind --leak-check=full --show-reachable=yes $<

memtest2: array_2d_mwe2
	valgrind --leak-check=full --show-reachable=yes $<
