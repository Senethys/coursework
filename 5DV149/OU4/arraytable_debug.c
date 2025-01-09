#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "table.h"

/*
 * Minimum working example for table.c. Inserts 4 key-value pairs into
 * a table, including one duplicate. Makes two lookups and prints the
 * result. The responsibility to deallocate the key-value pairs is NOT
 * handed over to the table. Thus, all pointers must be stored outside
 * the table.
 *
 * Authors: Niclas Borlin (niclas@cs.umu.se)
 *	    Adam Dahlgren Lindstrom (dali@cs.umu.se)
 *
 * Version information:
 *   2018-02-06: v1.0, first public version.
 *   2019-03-05: v1.01, improved docs.
 */

// Create a dynamic copy of the string str.
static char* make_string_copy(const char *str)
{
	char *copy = calloc(strlen(str) + 1, sizeof(char));
	strcpy(copy, str);
	return copy;
}

// Interpret the supplied key and value pointers and print their content.
static void print_int_string_pair(const void *key, const void *value)
{
	//const int *k=key;
	const char *s=value;
	printf("%d, %s", 	*(int*)key, s);
}

// Compare two keys (int *).
static int compare_ints(const void *k1, const void *k2)
{
	printf("__________________Comparing__________________ \n\n");
	printf("key1: %d\n", *(int *)k1);
	printf("key1 Memory: %p\n", k1);

	printf("key2: %d\n", *(int *)k2);
	printf("key2 Memory: %p\n", k2);
	if (k1 || k2 == NULL) {
		return NULL;
	}
	int key1 = *(int *)k1;
	int key2 = *(int *)k2;

	if ( key1 == key2 )
		return 0;
	if ( key1 < key2 )
		return -1;
	return 1;
}

int main(void)
{
	// Keep track of the key-value pairs we allocate.
	table *t = table_empty(string_compare, free, free);

	char *key1 = copy_string("key1");
	char *value1 = copy_string("value1");
	printf("\n");
	table_print(t, table_print);
	printf("\n");
	table_insert(t, key1, value1);
	table_print(t, table_print);
	printf("\n");
	table_remove(t, key1);
	table_print(t, table_print);
	printf("\n");
	if (!table_is_empty(t)){
					printf("Removing the last element from a table does not "
								 "result in an empty table.\n");
					exit(EXIT_FAILURE);
	}
	printf("Inserting one element and removing it, checking that the "
				 "table gets empty - OK\n");
	table_kill(t);
	return 0;
}
