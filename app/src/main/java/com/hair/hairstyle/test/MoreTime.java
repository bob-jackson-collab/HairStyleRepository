package com.hair.hairstyle.test;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by yunshan on 17/8/16.
 */

public class MoreTime {

    public static void main(String[] args) {
        int array[] = new int[]{2, 2, 2, 2, 4, 4, 5, 5, 6};

        tong_ji(array);
    }

    private static void tong_ji(int array[]) {

        int count = 0;
        int count_2 = 0;
        int temp = 1;

        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < array.length; i += count) {

            if(i == array.length -1){

                break;
            }

            for (int j = i + 1; j < array.length; j++) {

                if (array[i] == array[j]) {
                    count++;
                }
                continue;
            }

            if (count > count_2) {
                count_2 = count;
                hashMap.put(count_2, array[i]);
            }

        }

        System.out.println(hashMap.get(count_2));
    }

    public static <T> int getMostFrequentByMap(T array[]) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int result = 0;
        int size = array.length;

        HashMap<T, LinkedList<T>> serveMap = new HashMap<>();
        for (int i = 0; i < size; i++) {
            T t = array[i];
            if (serveMap.get(t) != null) {
                serveMap.get(t).add(t);
            } else {
                LinkedList<T> linkedList = new LinkedList<>();
                linkedList.add(t);
                serveMap.put(t, linkedList);
            }

        }

        LinkedList<T> largest = null;

        for (LinkedList<T> linkedList : serveMap.values()) {
            if (largest == null) {
                largest = linkedList;
                continue;
            }
            if (largest.size() < linkedList.size()) {
                largest = linkedList;
            }
        }

        result = largest.size();
        return result;
    }
}
