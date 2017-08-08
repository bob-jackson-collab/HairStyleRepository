package com.hair.hairstyle.test;

/**
 * Created by yunshan on 17/8/7.
 */

public class Test {
    public static void main(String[] args) {

        int arry[] = new int[]{3, 4, 2, 1, 2, 7, 9};

        int i = binarySearch(arry, arry.length, 7);

//        System.out.println(i);

//        quickSort(arry, 0, arry.length - 1);

//        for (int i1 : arry) {
//            System.out.print(i1 + "  ");
//        }
        heapSort(arry);

    }


    //二分法查找
    static int binarySearch(int[] array, int size, int value) {
        int lo = 0;
        int hi = size - 1;

        while (lo <= hi) {
            final int mid = (lo + hi) >>> 1;
            final int midVal = array[mid];

            if (midVal < value) {
                lo = mid + 1;
            } else if (midVal > value) {
                hi = mid - 1;
            } else {
                return mid;  // value found
            }
        }
        return ~lo;  // value not present
    }

    //快速排序

    static void quickSort(int array[], int left, int right) {

        int i, j, rand;
        if (left >= right) {
            return;
        }
        i = left;
        j = right;
        rand = array[left];

        while (i != j) {

            while (array[j] >= rand && i < j) {
                j--;
            }

            while (array[i] <= rand && i < j) {
                i++;
            }

            if (i < j) {
                int t = array[i];
                array[i] = array[j];
                array[j] = t;
            }
        }

        array[left] = array[i];
        array[i] = rand;

        quickSort(array, left, i - 1);
        quickSort(array, i + 1, right);

    }

    //堆排序
    public static void heapAdjust(int array[], int parent, int length) {
        int temp = array[parent];
        int child = parent*2  + 1;

        while (child < length) {
            //如果右孩子的节点大于左孩子的节点
            if (child + 1 < length && array[child] < array[child + 1]) {
                child++;
            }

            if (array[child] < temp) {
                break;
            }

            array[parent] = array[child];

            parent = child;
            child = child*2 + 1;
        }

        array[parent] = temp;
    }

    public static void heapSort(int[] list) {
        // 循环建立初始堆
        for (int i = list.length / 2; i >= 0; i--) {
            heapAdjust(list, i, list.length);
        }

        // 进行n-1次循环，完成排序
        for (int i = list.length - 1; i > 0; i--) {
            // 最后一个元素和第一元素进行交换
            int temp = list[i];
            list[i] = list[0];
            list[0] = temp;

            // 筛选 R[0] 结点，得到i-1个结点的堆
            heapAdjust(list, 0, i);
            System.out.format("第 %d 趟: \t", list.length - i);

            for (int i1 : list) {
                System.out.print(i1 + "  ");
            }
        }
    }
}
