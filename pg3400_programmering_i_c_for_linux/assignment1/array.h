typedef struct Array {
    struct Node *nodes;
    unsigned int size;
} Array;

typedef struct Node {
    unsigned int value;
    unsigned int position;
} Node;

void bubbleSort(Array array);
void insertionSort(Array array);
void merge(Array array, int low, int mid, int high);
void mergeSort(Array array, int low, int high);
int binarySearch(Array array, int low, int high, int search);