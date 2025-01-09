#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "array_1d.h"
#include "table.h"

/*
 * Implementation of a generic table for the "Datastructures and
 * algorithms" courses at the Department of Computing Science, Umea
 * University.
 *
 * Duplicates are handled by inspect and remove.
 *
 * Authors: Niclas Borlin (niclas@cs.umu.se)
 *	    Adam Dahlgren Lindstrom (dali@cs.umu.se)
 *
 * Based on earlier code by: Johan Eliasson (johane@cs.umu.se).
 *
 * Version information:
 *   2018-02-06: v1.0, first public version.
 *   2019-03-04: v1.1, bugfix in table_remove.
 */

// ===========INTERNAL DATA TYPES============

struct table {
	array_1d *entries;
	compare_function *key_cmp_func;
	free_function key_free_func;
	free_function value_free_func;
};

struct table_entry {
	void *key;
	void *value;
};



// ===========INTERNAL FUNCTION IMPLEMENTATIONS============

/**
 * table_empty() - Create an empty table.
 * @key_cmp_func: A pointer to a function to be used to compare keys.
 * @key_free_func: A pointer to a function (or NULL) to be called to
 *		   de-allocate memory for keys on remove/kill.
 * @value_free_func: A pointer to a function (or NULL) to be called to
 *		     de-allocate memory for values on remove/kill.
 *
 * Returns: Pointer to a new table.
 */
table *table_empty(compare_function *key_cmp_func,
		   free_function key_free_func,
		   free_function value_free_func)
{
	// Allocate the table header.
	table *t = calloc(1, sizeof(table));

	// Create the list to hold the table_entry-ies.
	t->entries = array_1d_create(1, 40000, free);
	// Store the key compare function and key/value free functions.
	t->key_cmp_func = key_cmp_func;
	t->key_free_func = key_free_func;
	t->value_free_func = value_free_func;

	return t;
}

/**
 * table_is_empty() - Check if a table is empty.
 * @table: Table to check.
 *
 * Returns: True if table contains no key/value pairs, false otherwise.
 */
bool table_is_empty(const table *t)
{
	return !array_1d_has_value(t->entries, 1);
}

/**
 * table_insert() - Add a key/value pair to a table.
 * @table: Table to manipulate.
 * @key: A pointer to the key value.
 * @value: A pointer to the value value.
 *
 * Insert the key/value pair into the table. No test is performed to
 * check if key is a duplicate. table_lookup() will return the latest
 * added value for a duplicate key. table_remove() will remove all
 * duplicates for a given key.
 *
 * Returns: Nothing.
 */
void table_insert(table *t, void *key, void *value)
{
	// Allocate the key/value structure.
	struct table_entry *entry = malloc(sizeof(struct table_entry));
	struct table_entry *retrieved_entry;
	// Set the pointers and insert first in the list. This will
	// cause table_lookup() to find the latest added value.
	entry->key = key;
	entry->value = value;
	for (int i=array_1d_low(t->entries); i<=array_1d_high(t->entries); i++) {
		if(!(array_1d_has_value(t->entries, i))) {
			array_1d_set_value(t->entries, entry, i);
			break;
		} else if(array_1d_has_value(t->entries, i)) {
				retrieved_entry = array_1d_inspect_value(t->entries, i);
				if (t->key_cmp_func(entry->key, retrieved_entry->key) == 0) {
					array_1d_set_value(t->entries, entry, i);
					break;
				}
			}
		}
}


/**
 * table_lookup() - Look up a given key in a table.
 * @table: Table to inspect.
 * @key: Key to look up.
 *
 * Returns: The value corresponding to a given key, or NULL if the key
 * is not found in the table. If the table contains duplicate keys,
 * the value that was latest inserted will be returned.
 */
void *table_lookup(const table *t, const void *key)
{
	int keyChecked  = *(int*)key;
	if(key != NULL || keyChecked != 0) {
		// Iterate over the list. Return first match.
	for (int i=array_1d_low(t->entries); i<=array_1d_high(t->entries); i++) {
			// Inspect the table entry
			if(array_1d_has_value(t->entries, i)) {
				struct table_entry *entry = array_1d_inspect_value(t->entries, i);
				// Check if the entry key matches the search key.
				if (t->key_cmp_func(entry->key, key) == 0) {
					// If yes, return the corresponding value pointer.
					return entry->value;
				}
			} else {
				return NULL;
			}
		}
	}
	// No match found. Return NULL.
	return NULL;
}


/**
 * table_choose_key() - Return an arbitrary key.
 * @t: Table to inspect.
 *
 * Return an arbitrary key stored in the table. Can be used together
 * with table_remove() to deconstruct the table. Undefined for an
 * empty table.
 *
 * Returns: An arbitrary key stored in the table.
 */
void *table_choose_key(const table *t)
{
	// Return first key value.
	struct table_entry *entry;
	if(array_1d_inspect_value(t->entries, array_1d_low(t->entries)) == NULL) {
		printf("Warning: Atempt to get a key of an empty table!");
	};
	entry = array_1d_inspect_value(t->entries, array_1d_low(t->entries));
	return entry->key;
}

/**
 * table_remove() - Remove a key/value pair in the table.
 * @table: Table to manipulate.
 * @key: Key for which to remove pair.
 *
 * Any matching duplicates will be removed. Will call any free
 * functions set for keys/values. Does nothing if key is not found in
 * the table.
 *
 * Returns: Nothing.
 */
void table_remove(table *t, const void *key)
{

	//Iterate over the table until a key is matched.
	for (int pos = array_1d_low(t->entries); pos<=array_1d_high(t->entries); pos++) {
		if(array_1d_has_value(t->entries, pos)) {
			struct table_entry *entry = array_1d_inspect_value(t->entries, pos);
			if (t->key_cmp_func(entry->key, key) == 0) {
				//If this is NOT the last entry in the table, go to the last one.
				if(array_1d_has_value(t->entries, pos + 1)) {
					int current_pos = pos;
					while(array_1d_has_value(t->entries, current_pos)) {
						//Upon arrival to last entry, move the last entry to the removed one.
						if(!(array_1d_has_value(t->entries, current_pos + 1))) {
							struct table_entry *copied_entry = malloc(sizeof(struct table_entry));
							struct table_entry *current_entry = array_1d_inspect_value(t->entries, current_pos);
							void *keyCopy = malloc(sizeof(*current_entry->key));
							void *valueCopy = malloc(sizeof(*current_entry->value));
							memcpy(keyCopy, current_entry->key, sizeof(copied_entry->key));
							memcpy(valueCopy, current_entry->value, sizeof(copied_entry->value));
							copied_entry->key = keyCopy;
							copied_entry->value = valueCopy;
							array_1d_set_value(t->entries, NULL, current_pos);
							array_1d_set_value(t->entries, copied_entry, pos);

							return;
						}
						current_pos++;
					}
				} else {
					array_1d_set_value(t->entries, NULL, pos);
				}
			}
		}
	}
}


/*
 * table_kill() - Destroy a table.
 * @table: Table to destroy.
 *
 * Return all dynamic memory used by the table and its elements. If a
 * free_func was registered for keys and/or values at table creation,
 * it is called each element to free any user-allocated memory
 * occupied by the element values.
 *
 * Returns: Nothing.
 */
void table_kill(table *t)
{
	// Iterate over the list. Destroy all elementss.
	// int pos = 1;
	// while (array_1d_has_value(t->entries, pos)) {
	// 	// Inspect the key/value pair.
	// 	struct table_entry *entry = array_1d_inspect_value(t->entries, pos);
	// 	// Free key and/or value if given the authority to do so.
	// 	if (t->key_free_func != NULL) {
	// 		t->key_free_func(entry->key);
	// 	}
	// 	if (t->value_free_func != NULL) {
	// 		t->value_free_func(entry->value);
	// 	}
	// 	// Move on to next element.
	// 	pos++;


	// Kill what's left of the list...
	array_1d_kill(t->entries);
	// ...and the table.
	free(t);
}

/**
 * table_print() - Print the given table.
 * @t: Table to print.
 * @print_func: Function called for each key/value pair in the table.
 *
 * Iterates over the key/value pairs in the table and prints them.
 * Will print all stored elements, including duplicates.
 *
 * Returns: Nothing.
 */
void table_print(const table *t, inspect_callback_pair print_func)
{
	//Iterate over all elements. Call print_func on keys/values.
	array_1d_print(t->entries, (inspect_callback)print_func);
}
