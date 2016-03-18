#include "array.h"

void bubbleSort(Array array) {
    int iLimit = array.size - 1;
    for (int i = 0; i < iLimit; i++) {
        int jLimit = array.size - i - 1;
        for (int j = 0; j < jLimit; j++) {
            if (array.nodes[j].value > array.nodes[j + 1].value) {
                Node swap = array.nodes[j];
                array.nodes[j] = array.nodes[j + 1];
                array.nodes[j + 1] = swap;
            }
        }
    }
}

void insertionSort(Array array) {
    for (int i = 1; i < array.size; i++) {
        int j = i;
        while (j > 0 && array.nodes[j].value < array.nodes[j - 1].value) {
            Node swap = array.nodes[j];
            array.nodes[j] = array.nodes[j - 1];
            array.nodes[j - 1] = swap;
            j--;
        }
    }
}

void merge(Array array, int low, int mid, int high) {
    int leftSize = mid - low + 1;
    int rightSize = high - mid;
    Node leftArray[leftSize];
    Node rightArray[rightSize];

    for (int i = 0; i < leftSize; i++) {
        leftArray[i] = array.nodes[low + i];
    }
    for (int j = 0; j < rightSize; j++) {
        rightArray[j] = array.nodes[mid + 1 + j];
    }

    int i = 0;
    int j = 0;
    int k = low;

    while (i < leftSize && j < rightSize) {
        if (leftArray[i].value <= rightArray[j].value) {
            array.nodes[k] = leftArray[i];
            i++;
        } else {
            array.nodes[k] = rightArray[j];
            j++;
        }
        k++;
    }

    while (i < leftSize) {
        array.nodes[k] = leftArray[i];
        k++;
        i++;
    }

    while (j < rightSize) {
        array.nodes[k] = rightArray[j];
        k++;
        j++;
    }
}

void mergeSort(Array array, int low, int high) {
    if (low < high) {
        int mid = (low + high) / 2;
        mergeSort(array, low, mid);
        mergeSort(array, mid + 1, high);
        merge(array, low, mid, high);
    }
}

int binarySearch(Array array, int low, int high, int search) {
    if (low > high) {
        return -1;
    }
    int mid = (low + high) / 2;
    if (array.nodes[mid].value == search) {
        return mid;
    } else if (array.nodes[mid].value > search) {
        return binarySearch(array, low, mid - 1, search);
    } else {
        return binarySearch(array, mid + 1, high, search);
    }
}