TARGETS = stack_mwe1 stack_mwe2

all:	$(TARGETS)

OPTS =  -std=c99 -Wall -I ../../include -g

clean:
	-rm $(TARGETS)

stack_mwe1: stack_mwe1.c stack.c
	gcc -o $@ $(OPTS) $^

stack_mwe2: stack_mwe2.c stack.c
	gcc -o $@ $(OPTS) $^

memtest1: stack_mwe1
	valgrind --leak-check=full --show-reachable=yes $<

memtest2: stack_mwe2
	valgrind --leak-check=full --show-reachable=yes $<
