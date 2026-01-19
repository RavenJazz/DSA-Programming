#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#define TABLE_SIZE 101 // Prime number for better distribution

// Division Method: h(k) = k mod m
int hash_division(int key) {
    return key % TABLE_SIZE;
}

// Multiplication Method: h(k) = floor(m * (k * A mod 1))
int hash_multiplication(int key) {
    double A = 0.618033; // (sqrt(5)-1)/2
    double val = key * A;
    double fractional_part = val - (int)val;
    return (int)(TABLE_SIZE * fractional_part);
}

// Simple String Hashing
unsigned int hash_string(char *str) {
    unsigned int hash = 0;
    while (*str) {
        hash = (hash << 5) + *str++;
    }
    return hash % TABLE_SIZE;
}
#define EMPTY -1
#define DELETED -2

typedef struct {
    int key;
    int value;
} Entry;

Entry hashTable[TABLE_SIZE];

void init_table() {
    for (int i = 0; i < TABLE_SIZE; i++) {
        hashTable[i].key = EMPTY;
    }
}

// Insertion with Linear Probing
void insert(int key, int value) {
    int index = hash_division(key);
    int start_index = index;

    while (hashTable[index].key != EMPTY && hashTable[index].key != DELETED) {
        if (hashTable[index].key == key) { // Update existing
            hashTable[index].value = value;
            return;
        }
        index = (index + 1) % TABLE_SIZE; // Linear Probing
        if (index == start_index) return; // Table full
    }
    hashTable[index].key = key;
    hashTable[index].value = value;
}

// Search operation
int search(int key) {
    int index = hash_division(key);
    int start_index = index;

    while (hashTable[index].key != EMPTY) {
        if (hashTable[index].key == key) return hashTable[index].value;
        index = (index + 1) % TABLE_SIZE;
        if (index == start_index) break;
    }
    return -1; // Not found
}

// Deletion with marker to handle clustering issues
void delete(int key) {
    int index = hash_division(key);
    int start_index = index;

    while (hashTable[index].key != EMPTY) {
        if (hashTable[index].key == key) {
            hashTable[index].key = DELETED;
            return;
        }
        index = (index + 1) % TABLE_SIZE;
        if (index == start_index) break;
    }
}