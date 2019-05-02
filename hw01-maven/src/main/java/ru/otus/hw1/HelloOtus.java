package ru.otus.hw1;

import com.google.common.collect.ImmutableSet;

public class HelloOtus {
    public static void main(String[] args) {
        ImmutableSet<String> immutableSet = ImmutableSet.of("str1", "str2", "str3");
        System.out.println("Количество элементов: " + immutableSet.size());
    }
}
