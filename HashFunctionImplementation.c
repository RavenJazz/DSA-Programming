#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <time.h>

#define TABLE_SIZE 1000
#define NUM_KEYS 5000

// 1. Division Method: h(k) = k mod m
int hash_division(int key, int m) {
    return key % m;
}

// 2. Multiplication Method: h(k) = floor(m * (k * A mod 1))
// A is typically (sqrt(5) - 1) / 2
int hash_multiplication(int key, int m) {
    double A = 0.6180339887; // Knuth's constant
    double val = key * A;
    double fraction = val - floor(val);
    return (int)(m * fraction);
}

// 3. Simple String Hashing (djb2 algorithm)
unsigned long hash_string(unsigned char *str) {
    unsigned long hash = 5381;
    int c;
    while ((c = *str++))
        hash = ((hash << 5) + hash) + c; // hash * 33 + c
    return hash % TABLE_SIZE;
}

// Distribution Analysis Helper
void analyze_distribution(int *counts, int size, char* name) {
    double sum = 0;
    for (int i = 0; i < size; i++) sum += counts[i];
    double mean = sum / size;
    
    double variance = 0;
    for (int i = 0; i < size; i++) 
        variance += pow(counts[i] - mean, 2);
    
    printf("%s - Variance (lower is more uniform): %.2f\n", name, variance / size);
}

int main() {
    int div_counts[TABLE_SIZE] = {0};
    int mult_counts[TABLE_SIZE] = {0};
    srand(time(NULL));

    // Generate random keys and calculate hash values
    for (int i = 0; i < NUM_KEYS; i++) {
        int key = rand() % 100000;
        div_counts[hash_division(key, TABLE_SIZE)]++;
        mult_counts[hash_multiplication(key, TABLE_SIZE)]++;
    }

    // Analyze distribution and uniformity
    printf("--- Uniformity Assessment ---\n");
    analyze_distribution(div_counts, TABLE_SIZE, "Division Method");
    analyze_distribution(mult_counts, TABLE_SIZE, "Multiplication Method");

    return 0;
}