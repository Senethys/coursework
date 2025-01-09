#include <stdio.h>
#include <stdlib.h>
#include "stack.h"


/*
* Datastrkturer och algoritmer
* Spring 19
* Assignment 2

* File:         stacktest.c
* Description:  A test program for a stack implementation.
*               Tests are mainly based on the axioms of queues, see below.
*               Also checks if the functions don't modify the data.
* Author:       Svitri Magnusson
* CS username:  kv13smn
* Date:         2019-06-16
* Input:        void
* Output:       console prints
* Limitations:  Does not test every combination of elements in different order.
* Run: gcc -std=c99 -Wall -I../include/ stack/stack.c list/list.c -o stack stacktest.c
*/

/**
 * print_ints - Print int values of the queue.
 * @data: Data to print.
 * This function simply prints any data in int form.
 * Returns: Void.
 */
void print_ints(const void *data) {
	int pointer_data = *(int*)data;
	printf("[%d]", pointer_data);
}

/*
* add_all_types() - Populates the stack with primitive datatypes.
* @q: Queue to populate.
* This function populates the stack with various types.
* Returns: Queue pointer with filled datatypes.
*/

stack *add_all_types(stack *s) {
 //Declare variables.
 char *c =  malloc(sizeof(char));
 double *d =  malloc(sizeof(*d));
 long *l =  malloc(sizeof(*l));
 bool *b =  malloc(sizeof(*b));
 int *v = malloc(sizeof(*v));

 //Assign various values.
 *c = 't';
 *d=3.14;
 *l=1234567;
 *b=false;
 *v=42;

 s = stack_push(q, c);
 s = stack_push(q, d);
 s = stack_push(q, l);
 s = stack_push(q, b);
 s = stack_push(q, v);

 return s;
}

/**
 * free_all_types() - Frees the stack with from datatypes.
 * @q: Stack to free elements of.
 * Goes through all the elements in the stack and pops and frees them.
 * Returns: void
 */
 void free_all_types(stack *s) {
   printf("Freeing elements...\n");
   while(!queue_is_empty(q)) {
     void *var = queue_front(q);
     free(var);
     stack_kill(s);
   }
 }



int main(void) {
 printf("Stack Testing...\n\n");

 printf("***Axiomatic tests:***\n\n");

 printf("AX 1 - Empty test: \n");
 is_empty_test();

 printf("AX 2 - Not empty test: \n");
 is_not_empty_test();

 printf("AX 3 - Pop test: \n");
 pop_test();

 printf("AX 4 - Top test: \n");
 top_test();

 printf("AX 5 - ¬Isempty(s) ⇒ (Push(Top(s)), Pop(s)) = s ");

 printf("Testing if push top pop functions affect stack  order in an illegal way\n");
 order_test();

 return 0;

}
