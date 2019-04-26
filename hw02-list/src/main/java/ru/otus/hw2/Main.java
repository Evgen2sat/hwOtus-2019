package ru.otus.hw2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list1 = new DIYarrayList<>();
        Collections.addAll(list1, 5,3,10,9,2,6,1,4,8,7,15,13,20,19,12,16,11,14,18,17);
        List<Integer> list2 = new DIYarrayList<>(list1.size());

        Collections.copy(list2, list1);

        Collections.sort(list1, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

        for(int i = 0; i< list1.size(); i++) {
            System.out.println(list1.get(i));
        }

        for(int i = 0; i< list2.size(); i++) {
            System.out.println(list2.get(i));
        }
    }
}
