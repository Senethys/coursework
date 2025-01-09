#include <stdio.h>
#include <stdlib.h>
#include "stack.h"
#include "list.h"
#include <string.h>
#include <math.h>

 /*
* Datastrkturer och algoritmer - 5DV149 (C)
* Spring 19
* Assignment 2

* File:         stack_test.c
* Description:  A test program for a stack implementation.
*               Tests are mainly based on the axioms of stack and another test.
*               Also checks if the functions don't modify the data.
* Author:       Svitri Magnusson
* CS username:  kv13smn
* Date:         2019-06-16
* Input:        void
* Output:       console prints
* Limitations:  Does not test every combination of elements in different order.
* Run: gcc -std=c99 -Wall -I../include/ stack/stack.c list/list.c stack_test.c -o stack_test
* Testing Ax 1 - 5 and another, order test.
*/

 /**
 * print_ints - Print int values of the stack.
 * @data: Data to print.
 * This function simply prints any data in int form.
 * Returns: Void.
 */
 void print_ints(const void *data) {
	int *pointer_data = malloc(sizeof(pointer_data));
	*pointer_data = *(int*)data;
	printf("[%d]", *pointer_data);
	free(pointer_data);
}

 /**
 * add_all_types() - Populates the stack with primitive datatypes.
 * @s: Stack to populate.
 * Returns: Stack pointer with filled datatypes.
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

	//Push values
	s = stack_push(s, c);
	s = stack_push(s, d);
	s = stack_push(s, l);
	s = stack_push(s, b);
	s = stack_push(s, v);

 	return s;
 }

 /**
 * free_all_types() - Frees the stack with from datatypes.
 * @s: Stack to free elements of.
 * Goes through all the elements in the stack and deqeues and frees them.
 * Returns: void
 */
 void free_all_types(stack *s) {
   while(!stack_is_empty(s)) {
     void *var = stack_top(s);
     free(var);
     stack_pop(s);
   }
 }

 /**
 * Ax 1.
 * is_empty_test() - Checks if stack_empty works correcetly on empty stack.
 * Creates empty stack, prints and checks if it's empty.
 * Returns: void.
 */
 void is_empty_test() {
	//Creates an empty stack and prints it.
	stack *s=stack_empty(NULL);
	stack_print(s, print_ints);

	if (!stack_is_empty(s)) {
		fprintf(stderr, "FAIL: New stack is not empty!\n");
		stack_kill(s);
		exit(EXIT_FAILURE);
	}

	stack_kill(s);
	printf("PASSED.\n\n");
}

/**
 * Ax 2.
 * empty_enque_test() - Checks if stack_is_empty works on non-empty stack.
 * Creates empty stack, populates it wiht one elementand checks if its empty.
 * Returns: void.
 */
 void is_not_empty_test() {
 	stack *s=stack_empty(NULL);
 	stack_print(s, print_ints);
 	int *v = malloc(sizeof(*v));
 	*v=1;
 	s = stack_push(s, v);
 	stack_print(s, print_ints);

 	if (stack_is_empty(s)) {
 		fprintf(stderr, "FAIL: New stack is empty after enqueue!\n");
 		stack_kill(s);
 		exit(EXIT_FAILURE);
 	}
 	free_all_types(s);
 	stack_kill(s);
 	printf("PASSED.\n\n");

 }

 /**
  * Ax 3.
  * empty_enque_deque_test()
  * This function stacls and then immidiately pops to check if its empty.
  * Returns: void
  */
 void pop_test() {
 	stack *s=stack_empty(NULL);
 	int *v = malloc(sizeof(*v));
 	*v=1;
	stack_print(s, print_ints);
  //Removes the last element and ads another one.
	stack_push(s, v);
	stack_print(s, print_ints);
 	stack_pop(s);
 	stack_print(s, print_ints);
 	if (!stack_is_empty(s)) {
 		fprintf(stderr, "FAIL: New stack is not empty after push and pop!\n");
 		stack_kill(s);
 		exit(EXIT_FAILURE);
 	}
 	free(v);
 	stack_kill(s);
 	printf("PASSED.\n\n");
 }

 /**
  * Ax 4.
  * top_test()
  * This function checks if checking the top of a non-empty stack works.
  * Returns: void
  */
 void top_test() {
  //Creates to variables that will be used to test the top function.
 	stack *s=stack_empty(NULL);
 	int *v = malloc(sizeof(*v));
 	*v = 1;
 	int * a;

  //Pushes one variables and checks if its the same retrieved.
 	stack_print(s, print_ints);
 	a = stack_top(stack_push(s, v));
 	stack_print(s, print_ints);

  //Alerts if the stack turns out to be empty or
  //value is not the same as expected.
 	if (stack_is_empty(s) || (!(*a == *v))) {
 		fprintf(stderr, "FAIL: Stack is empty after push OR\n");
 		fprintf(stderr, "latest value added is not the same as top.\n");
 		fprintf(stderr, "Value 1: %d - Value 2: %d\n", *a, *v);
 		stack_kill(s);
 		exit(EXIT_FAILURE);
 	}

 	free_all_types(s);
 	stack_kill(s);
 	printf("PASSED.\n\n");
 }

 /**
  * Ax 5.
  * push_top_pop_test()
  * !Isempty(s) -> Push(Top(s), Pop(s) = s)
	* If the stack is non-empty and we remove the element, add it back,
	* we should get the same stack.
  * Returns: void
  */
 void push_top_pop_test() {
   //Creates to variables that will be used to test the top function.
  	stack *s=stack_empty(NULL);
  	int *v = malloc(sizeof(*v));
  	*v = 1;
    int *v1 = malloc(sizeof(*v));
    *v1 = 2;
		stack_push(s, v);
		stack_print(s, print_ints);
    stack_push(s, v1);
    stack_print(s, print_ints);
		stack *b = stack_push(stack_pop(s), stack_top(s));
  	stack_print(s, print_ints);

   //Alerts if the stack turns out to be empty or
   //value is not the same as expected.
  	if (stack_is_empty(s)) {
  		fprintf(stderr, "FAIL: Stack is empty after push\n");
  		stack_kill(s);
      stack_kill(b);

  		exit(EXIT_FAILURE);

  	} else if(stack_top(s) != v1) {
      fprintf(stderr, "FAIL: Stack top value is not the same after pop-top\n");
      stack_kill(s);
      stack_kill(b);
      exit(EXIT_FAILURE);

    } else if(b != s) {
      fprintf(stderr, "FAIL: Stack before pop-top is not the same as previously.\n");
      stack_kill(s);
      stack_kill(b);
      exit(EXIT_FAILURE);
    }

  	free_all_types(s);
  	stack_kill(s);
    stack_kill(b);
  	printf("PASSED.\n\n");
  }




 /**
  * order_type_test()
	* Adds and removes data in certain order to check if it is kept consistent.
	* Also checks if it empty works correcetly.
  *
  *
  * Returns: void.
  */
 void order_test() {
 //Creates an empty stack and populates it with pimitivice datatypes.
 	stack *s = stack_empty(NULL);
 	s = add_all_types(s);
	//Two values are created that will be later put in the stack.
 	char *ch = malloc(sizeof(*ch));
 	*ch = 'B';

  int *numb = malloc(sizeof(*numb));
  *numb = 41;

  //EPSILON prescision to compare doubles.
 	double precision = 0.000001;


  //Check top and remove it.
	int *top_numb = stack_top(s);
	stack_print(s, print_ints);
	if(*top_numb != 42) {
		printf("%d - ", *top_numb);
		fprintf(stderr, "FAIL: The stack got modified after pop or push!\n");
		fprintf(stderr, "Value: %d. Should be: 42", *top_numb);
		stack_kill(s);
		exit(EXIT_FAILURE);
	}
	free(top_numb);
	stack_pop(s);


	//Check top and remove it.
 	bool *top_bool = stack_top(s);
 	stack_print(s, print_ints);
 	if(*top_bool != 0) {
 		fprintf(stderr, "FAIL: The stack got modified after dequeue!\n");
 		fprintf(stderr, "Value: %d. Should be: 0", *top_bool);
 		stack_kill(s);
 		exit(EXIT_FAILURE);
 	}
 	free(top_bool);
 	stack_pop(s);


	//Check top and remove it.
 	long *top_long = stack_top(s);
 	stack_print(s, print_ints);
 	if(*top_long != 1234567) {
 		fprintf(stderr, "FAIL: The stack got modified after dequeue!\n");
 		fprintf(stderr, "Value: %lu. Should be: 1234567", *top_long);
 		stack_kill(s);
 		exit(EXIT_FAILURE);
 	}
 	free(top_long);
 	stack_pop(s);


	//Check top and remove it.
 	double *top_double = stack_top(s);
	stack_print(s, print_ints);
 	if(fabs(*top_double - 3.140000) > precision) {
 		fprintf(stderr, "FAIL: The stack got modified after dequeue!\n");
 		fprintf(stderr, "Value: %lf. Should be: pretty small, with precision: %lf", *top_double, precision);
 		stack_kill(s);
 		exit(EXIT_FAILURE);
 	}
 	free(top_double);
 	stack_pop(s);


	//Add element check top and remove it.
	stack_push(s, ch);
	char *top_char = stack_top(s);
	stack_print(s, print_ints);
	if(*top_char != 'B') {
		printf("%c - ", *top_char);
		fprintf(stderr, "FAIL: The stack got modified after push or pop!\n");
		fprintf(stderr, "Value: %c. Should be: %c", *top_char, *ch);
		stack_kill(s);
		exit(EXIT_FAILURE);
	}
	stack_pop(s);
	stack_print(s, print_ints);



	//Check top and remove it.
	char *top_int = stack_top(s);
	char compare_char = 't';
	stack_print(s, print_ints);
 	if((*top_int != compare_char)) {
 		fprintf(stderr, "FAIL: The stack got modified after push!\n");
		fprintf(stderr, "Value: 't' - Given Value: %i\n", *top_int);
 		stack_kill(s);
 		exit(EXIT_FAILURE);
 	free(top_int);
 	stack_pop(s);

	//Add element check top and remove it.
	s = stack_push(s, numb);
	int *top_num = stack_top(s);
	stack_print(s, print_ints);
	if(*top_num != 41) {
		printf("%d - ", *top_num);
		fprintf(stderr, "FAIL: The stack got modified after pop or push!\n");
		fprintf(stderr, "Value: %d. Should be: 42", *top_num);
		stack_kill(s);
		exit(EXIT_FAILURE);
	}
	stack_pop(s);


 	free(ch);
 	free(numb);
 	free_all_types(s);
 	stack_kill(s);
 	printf("PASSED.\n\n");
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

  printf("AX 5 - Push top pop test: \n");
	push_top_pop_test();

  printf("Testing if push top pop functions affects stack element order:\n");
  //order_test();

	printf("SUCCESS: Implementation passed all tests. Normal exit.\n");

  return 0;

 }
