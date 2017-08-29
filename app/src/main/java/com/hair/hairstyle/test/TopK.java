package com.hair.hairstyle.test;

/**
 * Created by yunshan on 17/8/16.
 */

public class TopK {

    int length;
    int array[];

    public static void main(String[] args) {

    }


    private void createMinHeap(int array[]) {

        for (int i = array.length / 2 - 1; i >= 0; i--) {
            heapify(i);
        }
    }

    private void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int smallest = i;

        if (l < length && array[l] < array[i]) {
            smallest = l;
        }

        if (r < length && array[r] < array[i]) {
            smallest = r;
        }

        swap(smallest, i);

        heapify(smallest);
    }


    private int right(int i) {
        return (i + 1) >> 1;
    }

    private int left(int i) {
        return ((i + 1) >> 1) - 1;
    }

    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}
