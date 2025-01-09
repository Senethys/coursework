#include <stdio.h>
#include <stdlib.h>
#include "queue.h"
#include "list.h"
#include <string.h>
#include <math.h>

/*
* Datastrkturer och algoritmer
* Spring 19
* Assignment 2

* File:         queuetest.c
* Description:  A test program for a queue implementation.
*               Tests are mainly based on the axioms of queues, see below.
*               Also checks if the functions don't modify the data.
* Author:       Svitri Magnusson
* CS username:  kv13smn
* Date:         2019-06-16
* Input:        void
* Output:       console prints
* Limitations:  Does not test every combination of elements in different order.
* Run: gcc -std=c99 -Wall -I../include/ queue/queue.c list/list.c -o que queuetest.c


Ax 1 Isempty (Empty)
Ax 2 !(Isempty(Enqueue(v,q)))
Ax 3 Isempty(q) -> Dequeue(Enqueue(v, q)) = q
Ax 4 !(Isempty(q)) -> Dequeue (Enqueue(v, q)) = Enqueue(v, Dequeue(q))
Ax 5 Isempty(q) -> Fron(Enqueue(v,q)) = v
Ax 6 !(Isempty(q)) -> Front(Enqueue(v, q)) = Front(q)

*/


/**
 * print_ints - Print int values of the queue.
 * @data: Data to print.
 * This function simply prints any data in int form.
 * Returns: Void.
 */

void print_ints(const void *data)
{
	printf("[%d]", *(int*)data);
}

/**
 * add_all_types() - Populates the queue with primitive datatypes.
 * @q: Queue to populate.
 *
 * Returns: Queue pointer with filled datatypes.
 */

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



/**
 * free_all_types() - Frees the queue with from datatypes.
 * @q: Queue to populate.
 *
 * Returns: void
 */
 void free_all_types(queue *q)
 {
   printf("Freeing variables...\n");
   while(!queue_is_empty(q)) {
     void *var = queue_front(q);
     free(var);
     queue_dequeue(q);
   }
 }


/**
 * Ax 1.
 * empty_queue_test() - Checks if queue_empty works correcetly on empty queue.
 * @q: Queue to test.
 *
 * Returns: void.
 */

void empty_queue_test()
{
    //Creates empty queue, prints and checks if its empty.

	queue *q=queue_empty(NULL);
	queue_print(q, print_ints);

	if (!queue_is_empty(q)) {
		fprintf(stderr, "FAIL. New queue is not empty!\n");
		queue_kill(q);
		exit(EXIT_FAILURE);
	}

	queue_kill(q);
	printf("PASSED.\n\n");
}

/**
 * Ax 2.
 * empty_enque_test() - Checks if queue_empty works correcetly on non empty queue.
 * @q: Queue to test.
 *
 * Returns: void.
 */

 void empty_enque_test()
 {
     //Creates empty queue, populates it and checks if its empty.
 	queue *q=queue_empty(NULL);
 	queue_print(q, print_ints);
 	int *v = malloc(sizeof(*v));
 	*v=1;
 	q = queue_enqueue(q, v);
 	queue_print(q, print_ints);
 	if (queue_is_empty(q)) {
 		fprintf(stderr, "FAIL. New queue is empty after enqueue!\n");
 		queue_kill(q);
 		exit(EXIT_FAILURE);
 	}
 	free_all_types(q);
 	queue_kill(q);
 	printf("PASSED.\n\n");

 }

/**
 * empty_enque_deque_test() - Function translated to axioms:
 * Ax 3 Isempty(q) -> Dequeue(Enqueue(v, q)) = q
 * Ax 4 !(Isempty(q)) -> Dequeue (Enqueue(v, q)) = Enqueue(v, Dequeue(q))
 *
 * This function enqueues and then immidiately dequeues to check if its empty.
 *
 * Returns: void
 */

 void empty_enque_deque_test()
 {

     //Create empty queue and populate it.
 	queue *q=queue_empty(NULL);
 	int *v = malloc(sizeof(*v));
 	int *v2 = malloc(sizeof(*v2));
 	*v=1;
 	*v2=2;
   //Ax 3 Isempty(q) -> Dequeue(Enqueue(v, q)) = q
   //Puts an element in the queue and immidiately removes it.
 	q = queue_dequeue(queue_enqueue(q, v));
 	queue_print(q, print_ints);
     //Checks if its impty.
 	if (!queue_is_empty(q)) {
 		fprintf(stderr, "FAIL. New queue is not empty after enqueue and deqeue!\n");
 		queue_kill(q);
 		exit(EXIT_FAILURE);
 	}

   //Ax 4 !(Isempty(q)) -> Dequeue (Enqueue(v, q)) = Enqueue(v, Dequeue(q))
     //Removes the last element and ads another one.
 	q = queue_enqueue(queue_dequeue(queue_enqueue(q, v)), v2);
 	queue_print(q, print_ints);
 	if (queue_is_empty(q)) {
 		fprintf(stderr, "FAIL. New queue is empty after denqueue-enqeue!\n");
 		queue_kill(q);
 		exit(EXIT_FAILURE);
 	}
 	free(v);
 	free(v2);
 	queue_kill(q);
 	printf("PASSED.\n\n");
 }




/**
 * empty_enque_deque_test() - Function translated to axioms:
 *
 * Ax 5 Isempty(q) -> Front(Enqueue(v,q)) = v
 * Ax 6 !(Isempty(q)) -> Front(Enqueue(v, q)) = Front(q)
 *
 * This function populates queue and checks if front works appropriately.
 *
 * Returns: void
 */

 void queue_front_test()
 {
     //Creates to variables that will be used to test the front function.
 	queue *q=queue_empty(NULL);
 	int *v = malloc(sizeof(*v));
 	int *v2 = malloc(sizeof(*v2));

 	*v = 1;
 	*v2 = 2;

 	int * a;
 	int * b;

     //Enqueus one variables and checks if its the same retrieved.
 	queue_print(q, print_ints);
 	a = queue_front(queue_enqueue(q, v));
 	queue_print(q, print_ints);

     //Alerts if the queue turns out to be empty or
     //value is not the same as expected.
 	if (queue_is_empty(q) || (!(*a == *v))) {
 		fprintf(stderr, "FAIL. Queue is empty after enqueue and front or\n");
 		fprintf(stderr, "latest value added is not the same as front.\n");
 		fprintf(stderr, "Value 1: %d - Value 2: %d\n", *a, *v);
 		queue_kill(q);
 		exit(EXIT_FAILURE);
 	}

     //Does the same as previous but with extra value.
 	b = queue_front(queue_enqueue(q, v2));
 	queue_print(q, print_ints);

     //Alerts if the queue turns out to be empty or
     //value is not the same as expected.
 	if (queue_is_empty(q) || (!(*b == *a))) {
 		fprintf(stderr, "FAIL. New queue is empty after enqueue and front or\n");
 		fprintf(stderr, "latest value added is not the same as front.\n");
 		fprintf(stderr, "Value 1: %d - Value 2: %d\n", *a, *b);
 		queue_kill(q);
 		exit(EXIT_FAILURE);
 	}

 	free_all_types(q);
 	queue_kill(q);
 	printf("PASSED.\n\n");
 }

/**
 * type_test() - Tests if queue can populate different types of data.
 *
 * Returns: void.
 */

void type_test()
{
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
	queue_kill(q);
	printf("PASSED.\n\n");

}

/**
 * capacity_test() - Checks if sum of added values are the same as dequeue'd.
 * This function populates the queue with increasing integers.
 * A same
 * Returns: void.
 */


void capacity_test()
{

    //These variables track accumulated value after enque and after dequeue.
	int *checkSum1 = calloc(1, sizeof(*checkSum1));
	int *checkSum2 = calloc(1, sizeof(*checkSum2));

    //Create a queue datastructure with dynamic memory.
	queue *q=queue_empty(NULL);
	q = add_all_types(q);

    //Enqueue 101 values and accumulate the total sum, save it to checkSum1.
	int *test = 0;
	for(int i = 0; i <= 100; i++) {
    *test = i;
		q = queue_enqueue(q, test);
		*checkSum1 += i;
	}

    //Checks if its empty.
	queue_print(q, print_ints);
	if (queue_is_empty(q)) {
		fprintf(stderr, "FAIL. Empty queue is not empty after 10000 values!\n");
		queue_kill(q);
		exit(EXIT_FAILURE);
	}

    //Deqeueu the same values and accumulate value to checkSum2.
	for(int i = 100; i >= 0; i--) {
		q = queue_dequeue(q);
		*checkSum2 += i;
	}

    //Compare the checksums. If they are the same, the queue did not modify
    //any values.
	queue_print(q, print_ints);
	if(*checkSum1 != *checkSum2) {
		fprintf(stderr, "FAIL. Totval addednot the same removed: %d  %d\n\n", *checkSum1, *checkSum2);
		queue_kill(q);
		exit(EXIT_FAILURE);
	}

	free(checkSum1);
	free(checkSum2);
	queue_kill(q);
	printf("PASSED.\n\n");

}

/**
 * order_type_test() - Adds and removes data in certain order to check if it
 *                     is kept consistent. Also checks if it empty works correcetly.
 *
 *
 * Returns: void.
 */

 void order_type_test()
 {
     //Creates an empty queue and populates it with pimitivice datatypes.
 	queue *q = queue_empty(NULL);
 	q = add_all_types(q);

     //Två variabler med primitiva datatyper skapas för att senare läggas till
     //i kön.
 	char *ch = malloc(sizeof(*ch));
 	*ch = 'B';

   int *numb = malloc(sizeof(*numb));
   *numb = 42;

   //EPSILON prescision för att jämnföra två double.
 	double precision = 0.000001;

   //Följande kod lägger till och tar bort element från kön, och skriver ut den.
   //Om kö implementationen förändrar ordningen på elementen kommer programmet
   //att krasha för att ordingen av vilka datatyper som kan skrivas ut är förutsbestämda.
   //Samtliga enqueue och dequeue operationer testas för att kolla om listan
   //blir tom.
   //E:Enqueue D:Dequeue
   //E, E, E, E, E, E, D, D, D, D, D, E, E, D, D

     //Lär av front och ta bort...
 	int *front_int = queue_front(q);
 	queue_print(q, print_ints);
 	if((*front_int != 't')) {
 		fprintf(stderr, "FAIL. The queue got modified after dequeue!\n");
 		fprintf(stderr, "Value: %s. Should be: \"test letters\"\n");
 		fprintf(stderr, "Value: %i. Should be: %i", *front_int);
 		queue_kill(q);
 		exit(EXIT_FAILURE);
 	}
 	free(front_int);
 	queue_dequeue(q);

     //Läs av front och ta bort...
 	double *front_double = queue_front(q);
 	queue_print(q, print_ints);
 	if(fabs(*front_double - 3.140000) > precision) {
 		fprintf(stderr, "FAIL. The queue got modified after dequeue!\n");
 		fprintf(stderr, "Value: %lf. Should be: pretty small, with precision: %lf", *front_double, precision);
 		queue_kill(q);
 		exit(EXIT_FAILURE);
 	}
 	free(front_double);
 	queue_dequeue(q);

     //Läs av front och ta bort...
 	long *front_long = queue_front(q);
 	queue_print(q, print_ints);
 	if(*front_long != 1234567) {
 		fprintf(stderr, "FAIL. The queue got modified after dequeue!\n");
 		fprintf(stderr, "Value: %lu. Should be: 1234567", *front_long);
 		queue_kill(q);
 		exit(EXIT_FAILURE);
 	}
 	free(front_long);
 	queue_dequeue(q);

     //Läs av front och ta bort...
 	bool *front_bool = queue_front(q);
 	queue_print(q, print_ints);
 	if(*front_bool != 0) {
 		fprintf(stderr, "FAIL. The queue got modified after dequeue!\n");
 		fprintf(stderr, "Value: %d. Should be: 0", *front_bool);
 		queue_kill(q);
 		exit(EXIT_FAILURE);
 	}
 	free(front_bool);
 	queue_dequeue(q);

     //Läs av front och ta bort...
 	int *front_digit = queue_front(q);
 	queue_print(q, print_ints);
 	if(*front_digit != 42) {
 		fprintf(stderr, "FAIL. The queue got modified after dequeue!\n");
 		fprintf(stderr, "Value: %d. Should be: 42", *front_digit);
 		queue_kill(q);
 		exit(EXIT_FAILURE);
 	}
 	free(front_digit);
 	queue_dequeue(q);
 	queue_print(q, print_ints);

 	if (!(queue_is_empty(q))) {
 		fprintf(stderr, "FAIL. New queue is not empty!\n");
 		queue_kill(q);
 		exit(EXIT_FAILURE);
 	}

     //Lägg till ett element, läs av och ta bort.
 	q = queue_enqueue(q, ch);
 	char *front_char = queue_front(q);
 	queue_print(q, print_ints);
 	if(*front_char != *ch) {
 		printf("%c - ", *front_char);
 		fprintf(stderr, "FAIL. The queue got modified after dequeue or enqueue!\n");
 		fprintf(stderr, "Value: %c. Should be: %c", *front_char, *ch);
 		queue_kill(q);
 		exit(EXIT_FAILURE);
 	}
  q = queue_enqueue(q, numb);
 	queue_dequeue(q);
 	queue_print(q, print_ints);

 	if ((queue_is_empty(q))) {
 		fprintf(stderr, "FAIL. New queue is not empty!\n");
 		queue_kill(q);
 		exit(EXIT_FAILURE);
 	}

     //Lägg till ett element, läs av och ta bort.
 	int *front_numb = queue_front(q);
 	queue_print(q, print_ints);
 	if(*front_numb != 42) {
 		printf("%d - ", *front_numb);
 		fprintf(stderr, "FAIL. The queue got modified after dequeue or enqueue!\n");
 		fprintf(stderr, "Value: %d. Should be: 42", *front_numb);
 		queue_kill(q);
 		exit(EXIT_FAILURE);
 	}
 	queue_dequeue(q);
 	queue_print(q, print_ints);


 	if (!(queue_is_empty(q))) {
 		fprintf(stderr, "FAIL. New queue is not empty!\n");
 		queue_kill(q);
 		exit(EXIT_FAILURE);
 	}

 	free(ch);
 	free(numb);
 	free_all_types(q);
 	queue_kill(q);
 	printf("PASSED.\n\n");
 }



/**
 * test_queue_structs() - Provar om kö implementeringen kan hantera matriser.
 *
 *
 * Returns: void.
 */






//						------------------------------------------
/****************************************MAIN**********************************/
//					         --------------------_____-----------------


int main(void)
{
	printf("Initiating Queue Testing...\n\n");


	printf("***Axiomatic tests:***\n\n");

	printf("AX 1 - Empty test: \n");
	empty_queue_test();

	printf("AX 2 - Enqueue test: \n");
	empty_enque_test();

	printf("AX 3 & 4 - Enqueue dequeue test: \n");
	empty_enque_deque_test();

	printf("AX 5 & 6 - Empty & Nonempty queue Front test: \n");
	queue_front_test();

	printf("Testing adding different types:\n");
	type_test();

	printf("Testing if any function affects list element order:\n");
	order_type_test();

  return 0;

}
