#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Logic for parsing the command line arguments (-i, -o, -of, -s)
void parse_args(int argc, char *argv[], char **input, char **output, int *flush, int *show) {
    for (int i = 1; i < argc; i++) {
        if (strcmp(argv[i], "-i") == 0) *input = argv[++i];
        else if (strcmp(argv[i], "-o") == 0) *output = argv[++i];
        else if (strcmp(argv[i], "-of") == 0) { *output = argv[++i]; *flush = 1; }
        else if (strcmp(argv[i], "-s") == 0) {
            *show = 1;
            if (i + 1 < argc && argv[i+1][0] != '-') *output = argv[++i];
        }
    }
}

// Function to insert a record with duplicate checking
void insert_record(FILE *db, Record newRec) {
    Record temp;
    rewind(db);
    while (fread(&temp, sizeof(Record), 1, db)) {
        if (memcmp(&temp, &newRec, sizeof(Record)) == 0) {
            printf("Duplicate denied.\n");
            return;
        }
    }
    fseek(db, 0, SEEK_END);
    fwrite(&newRec, sizeof(Record), 1, db);
}

int main(int argc, char *argv[]) {
    char *inputFile = NULL, *dbFile = "book.dat";
    int flush = 0, show = 0;

    parse_args(argc, argv, &inputFile, &dbFile, &flush, &show);

    if (flush) {
        FILE *f = fopen(dbFile, "wb"); 
        if(f) fclose(f);
    }

    FILE *db = fopen(dbFile, "rb+");
    if (!db) db = fopen(dbFile, "wb+");

    // Process input commands from inputFile using fgets() and strtok()
    // Commands: insert#..., update#..., delete#..., show#...
    
    if (show) {
        // Loop through db and print all records
    }

    fclose(db);
    return 0;
}