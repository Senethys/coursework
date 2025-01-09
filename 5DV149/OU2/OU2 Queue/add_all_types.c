#include <stdio.h>
#include <stdlib.h>
#include "queue.h"
#include "list.h"
#include <string.h>
#include <math.h>

void print_ints(const void *data) {
	printf("[%d]", *(int*)data);
}

void free_all_types(queue *q)
{
  printf("Freeing variables...\n");
  while(!queue_is_empty(q)) {
    void *var = queue_front(q);
    free(var);
    queue_dequeue(q);
  }
}


queue *add_all_types(queue *q) {
	int size = 13;
    //Declare dynamic variables.
	char *testString = "test letters";
	char *c =  malloc(size * sizeof(char));
	double *d =  malloc(sizeof(*d));
	long *l =  malloc(sizeof(*l));
	bool *b =  malloc(sizeof(*b));
	int *v = malloc(sizeof(*v));

  //  Assign various values.
  *c = *testString;
	*d=3.14;
	*l=1234567;
	*b=false;
	*v=42;

	q = queue_enqueue(q, c);
  q = queue_enqueue(q, d);
  q = queue_enqueue(q, l);
  q = queue_enqueue(q, b);
  q = queue_enqueue(q, v);

	return q;
}




void type_test() {
    //Creates an empty queue, and pupulates it with primitive datatypes.
    //If the implementation can't handle it, it will crash.

	queue *q = queue_empty(NULL);
	q = add_all_types(q);
	queue_print(q, print_ints);

    //Also checks if it is empty.
	if (queue_is_empty(q)) {
		fprintf(stderr, "FAIL. New queue is empty after enqueue!\n");
		queue_kill(q);
		exit(EXIT_FAILURE);
	}

	free_all_types(q);
	queue_kill(q);
	printf("PASSED.\n\n");

}



int main(void) {

	printf("Type test:\n");
	type_test();

  return 0;

}
