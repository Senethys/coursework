#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "table.h"

/*
 * Minimum working example for table.c. Inserts 3 key-value pairs into
 * a table, no duplicates. Makes one lookup and prints the result. The
 * responsibility to deallocate the key-value pairs is NOT handed over
 * to the table. Use table_choose_key() to extract keys and values to
 * be able to destroy the table without memory leaks or externally
 * stored pointers.
 *
 * Authors: Niclas Borlin (niclas@cs.umu.se)
 *	    Adam Dahlgren Lindstrom (dali@cs.umu.se)
 *
 * Version information:
 *   2019-03-05: v1.0, first public version.
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
	const int *k=key;
	const char *s=value;
	printf("[%d, %s]\n", *k, s);
}

// Compare two keys (int *).
static int compare_ints(const void *k1, const void *k2)
{
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
	// Keep the deallocation responsibility.
	table *t = table_empty(compare_ints, NULL, NULL);

	int *key;
	char *value;

	key = malloc(sizeof(int));
	*key = 90187;
	value = make_string_copy("Umea");
	table_insert(t, key, value);

	key = malloc(sizeof(int));
	*key = 90184;
	value = make_string_copy("Umea");
	table_insert(t, key, value);

	key = malloc(sizeof(int));
	*key = 98185;
	value = make_string_copy("Kiruna");
	table_insert(t, key, value);

	printf("Table after inserting 3 pairs:\n");
	table_print(t, print_int_string_pair);

	int v=90187;
	const char *s=table_lookup(t,&v);
	printf("Lookup of postal code %d: %s.\n",v,s);

	// Clean cleanup when we know the table has never stored duplicates.
	while (!table_is_empty(t)) {
		// Get one key from the table.
		key=table_choose_key(t);
		// Lookup the corresponding value.
		value=table_lookup(t,key);
		// Remove the key-value pair from the table.
		table_remove(t,key);
		// De-allocate key and value.
		free(key);
		free(value);
	}
	// Kill what is left by the table.
	table_kill(t);
}
