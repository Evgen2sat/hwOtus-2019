package ru.otus.hw8;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Person person = new Person();
        person.setParam1(10);
        person.setParam2(new int[] {1,2,3,4,5,6});
        Person.setParam3('d');
        person.setParam5(new String[] {"11","22","33"});
        person.setParam6(new Integer[]{1,null,3});

        Person people = new Person();
        people.setParam1(10);
        people.setParam2(new int[] {1,2,3,4,5,6});

        person.setParam4(new ArrayList<>() { {add(people); add(people);} });

        String json = JSONObject.toJson(person);

        Gson gson = new Gson();
        Person person1 = gson.fromJson(json, Person.class);

        System.out.println(person);
        System.out.println(person1);
    }
}
