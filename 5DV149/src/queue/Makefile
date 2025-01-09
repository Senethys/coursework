TARGETS = queue_mwe1 queue_mwe2

all:	$(TARGETS)

OPTS =  -std=c99 -Wall -I ../../include -g

clean:
	-rm $(TARGETS)

queue_mwe1: queue_mwe1.c queue.c ../list/list.c
	gcc -o $@ $(OPTS) $^

queue_mwe2: queue_mwe2.c queue.c ../list/list.c
	gcc -o $@ $(OPTS) $^

memtest1: queue_mwe1
	valgrind --leak-check=full --show-reachable=yes $<

memtest2: queue_mwe2
	valgrind --leak-check=full --show-reachable=yes $<
