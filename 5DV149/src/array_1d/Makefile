TARGETS = array_1d_mwe1 array_1d_mwe2 array_1d_mwe3 array_1d_mwe4

all:	$(TARGETS)

OPTS =  -std=c99 -Wall -I ../../include -g

clean:
	-rm $(TARGETS)

array_1d_mwe1: array_1d_mwe1.c array_1d.c
	gcc -o $@ $(OPTS) $^

array_1d_mwe2: array_1d_mwe2.c array_1d.c
	gcc -o $@ $(OPTS) $^

array_1d_mwe3: array_1d_mwe3.c array_1d.c
	gcc -o $@ $(OPTS) $^

array_1d_mwe4: array_1d_mwe4.c array_1d.c
	gcc -o $@ $(OPTS) $^

memtest1: array_1d_mwe1
	valgrind --leak-check=full --show-reachable=yes $<

memtest2: array_1d_mwe2
	valgrind --leak-check=full --show-reachable=yes $<

memtest3: array_1d_mwe3
	valgrind --leak-check=full --show-reachable=yes $<

memtest4: array_1d_mwe4
	valgrind --leak-check=full --show-reachable=yes $<

