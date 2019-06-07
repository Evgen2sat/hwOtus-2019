package ru.otus.hw8;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Person person = new Person();
        person.setParam1(10);
        person.setParam2(new int[] {1,2,3,4,5,6});
        Person.setParam3('d');

        Person people = new Person();
        people.setParam1(10);
        people.setParam2(new int[] {1,2,3,4,5,6});

        person.setParam4(new ArrayList<>() { {add(people); add(people);} });

        System.out.println(JSONObject.toJson(person));
    }
}
