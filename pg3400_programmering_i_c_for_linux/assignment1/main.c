#include <stdio.h>
#include <stdlib.h>
#include "array.h"

void exitIfNoFileArgument(char *filename) {
    if (filename == NULL) {
        printf("No input-file was specified. Program terminating\n");
        exit(EXIT_FAILURE);
    }
}

void exitIfFileCouldNotBeRead(FILE *file) {
    if (file == NULL) {
        printf("Could not read the file. Program terminating.\n");
        exit(EXIT_FAILURE);
    }
}

void exitIfReadError(int value) {
    if (value == 0) {
        printf("An error occurred when reading the file. Please check the input-file.\n"
                       "Program terminating.\n");
        exit(EXIT_FAILURE);
    }
}

void exitIfMemoryAllocationFails(void *pointer) {
    if (pointer == NULL) {
        printf("Could not allocate memory. Program terminating\n");
        exit(EXIT_FAILURE);
    }
}

void printArray(Array array) {
    for (int i = 0; i < array.size; i++) {
        printf("Value = %u, ", array.nodes[i].value);
        printf("Position = %u\n", array.nodes[i].position);
    }
}

Array readFile(char *filename) {
    Array array = { NULL , 0 };
    unsigned int input = 0;
    unsigned int reallocValue = 2;
    unsigned int count = 1;

    FILE *file = fopen(filename, "r");
    exitIfFileCouldNotBeRead(file);

    printf("Reading file...");
    while (!feof(file)) {
        exitIfReadError(fscanf(file, "%u", &input));
        array.size++;

        if (array.nodes == NULL) {
            array.nodes = (Node *) malloc(sizeof(Node));
        } else {
            if (array.size == reallocValue) {
                reallocValue *= 2;
                array.nodes = (Node *) realloc(array.nodes, reallocValue * sizeof(Node));
            }
        }
        exitIfMemoryAllocationFails(array.nodes);
        Node node = { input, count };
        array.nodes[array.size - 1] = node;
        count++;
    }
    printf("File-reading complete.\n");
    array.nodes = (Node *) realloc(array.nodes, array.size * sizeof(Node));
    exitIfMemoryAllocationFails(array.nodes);
    fclose(file);
    return array;
}

int getSortingAlgorithmFromUser() {
    int sortAlgorithm = 0;
    printf("Which sort algorithm do you wish to use?\n"
                   "(1) Bubble sort\n"
                   "(2) Insertion sort\n"
                   "(3) Merge sort\n");
    scanf("%d", &sortAlgorithm);
    return sortAlgorithm;
}

void sortArray(Array array, int sortMethod) {
    switch (sortMethod) {
        case 1: bubbleSort(array);break;
        case 2: insertionSort(array);break;
        case 3: mergeSort(array, 0, array.size - 1);break;
        default:
            printf("You must specify an algorithm. Program terminating.\n");
            exit(EXIT_FAILURE);
    }
}

int getSearchValueFromUser() {
    int searchValue = 0;
    printf("Enter a value to search for:\n");
    scanf("%d", &searchValue);
    if (searchValue == 0) {
        printf("0 is not a legal input-value.\n");
        exit(EXIT_FAILURE);
    }
    return searchValue;
}

void handleSearchResult(Array array, int searchValue, int index) {
    if (index == -1) {
        printf("Could not find the value %d\n", searchValue);
    } else {
        printf("Found the value %d. The value is located at index %d"
                       " in the sorted array and had the position %d in the"
                       " orignal .txt file\n",
               searchValue, index, array.nodes[index].position);
    }
}

int main(int argc, char *argv[]) {
    char *filename = argv[1];
    exitIfNoFileArgument(filename);
    Array array = readFile(filename);

    int sortMethod = getSortingAlgorithmFromUser();
    sortArray(array, sortMethod);

    int searchValue = getSearchValueFromUser();
    int index = binarySearch(array, 0, array.size - 1, searchValue);
    handleSearchResult(array, searchValue, index);

    free(array.nodes);
    array.nodes = NULL;

    return 0;
}