#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_LINE 1024
#define MAX_RECORDS 500

typedef struct {
    char name[100];
    char address[200];
    char city[50];
    char zip[20];
    char phone[20];
} Record;

Record book[MAX_RECORDS];
int record_count = 0;

// Function to check if an exact duplicate exists
int is_exact_duplicate(Record new_rec) {
    for (int i = 0; i < record_count; i++) {
        if (strcmp(book[i].name, new_rec.name) == 0 &&
            strcmp(book[i].address, new_rec.address) == 0 &&
            strcmp(book[i].city, new_rec.city) == 0 &&
            strcmp(book[i].zip, new_rec.zip) == 0 &&
            strcmp(book[i].phone, new_rec.phone) == 0) {
            return 1;
        }
    }
    return 0;
}

void insert_record(Record new_rec) {
    if (is_exact_duplicate(new_rec)) {
        return; // Deny insertion
    }
    if (record_count < MAX_RECORDS) {
        book[record_count++] = new_rec;
    }
}

void update_record(Record upd_rec) {
    for (int i = 0; i < record_count; i++) {
        if (strcmp(book[i].name, upd_rec.name) == 0) {
            book[i] = upd_rec;
        }
    }
}

void delete_record(char* name) {
    for (int i = 0; i < record_count; i++) {
        if (strcmp(book[i].name, name) == 0) {
            for (int j = i; j < record_count - 1; j++) {
                book[j] = book[j + 1];
            }
            record_count--;
            i--; // Check the new record at this index
        }
    }
}

void show_records(char* name, FILE* out) {
    int found = 0;
    for (int i = 0; i < record_count; i++) {
        if (name == NULL || strcmp(book[i].name, name) == 0) {
            fprintf(out, "%s#%s#%s#%s#%s\n", book[i].name, book[i].address, book[i].city, book[i].zip, book[i].phone);
            found = 1;
        }
    }
}

int main(int argc, char *argv[]) {
    char *input_file = NULL;
    char *output_file = "book.dat";
    int flush_first = 0;
    int show_flag = 0;

    // 1. Argument Parsing
    for (int i = 1; i < argc; i++) {
        if (strcmp(argv[i], "-i") == 0) input_file = argv[++i];
        else if (strcmp(argv[i], "-o") == 0) output_file = argv[++i];
        else if (strcmp(argv[i], "-of") == 0) { output_file = argv[++i]; flush_first = 1; }
        else if (strcmp(argv[i], "-s") == 0) {
            show_flag = 1;
            if (i + 1 < argc && argv[i+1][0] != '-') output_file = argv[++i];
        }
    }

    if (flush_first) {
        FILE* f = fopen(output_file, "w");
        if (f) fclose(f);
    }

    // 2. Process Input File
    if (input_file) {
        FILE* in = fopen(input_file, "r");
        if (!in) return 1;

        char line[MAX_LINE];
        while (fgets(line, sizeof(line), in)) {
            char* op = strtok(line, "#");
            if (!op) continue;

            Record r;
            if (strcmp(op, "insert") == 0 || strcmp(op, "update") == 0) {
                strcpy(r.name, strtok(NULL, "#"));
                strcpy(r.address, strtok(NULL, "#"));
                strcpy(r.city, strtok(NULL, "#"));
                strcpy(r.zip, strtok(NULL, "#"));
                strcpy(r.phone, strtok(NULL, "\n#"));
                
                if (strcmp(op, "insert") == 0) insert_record(r);
                else update_record(r);
            } 
            else if (strcmp(op, "delete") == 0) {
                char* name = strtok(NULL, "\n#");
                delete_record(name);
            }
        }
        fclose(in);
    }

    // 3. Final Show/Save
    FILE* out = fopen(output_file, "a");
    if (show_flag) show_records(NULL, out);
    fclose(out);

    return 0;
}