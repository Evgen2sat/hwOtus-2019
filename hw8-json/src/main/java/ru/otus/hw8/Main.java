package ru.otus.hw8;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

        JSONObject jsonObject = new JSONObject();

        String json = jsonObject.toJson(person);

        Gson gson = new Gson();
        Person person1 = gson.fromJson(json, Person.class);

        System.out.println(person);
        System.out.println(person1);


        System.out.println(jsonObject.toJson(null));
        System.out.println(gson.toJson(null));

        System.out.println(jsonObject.toJson(1));
        System.out.println(gson.toJson(1));

        System.out.println(jsonObject.toJson(1L));
        System.out.println(gson.toJson(1L));

        System.out.println(jsonObject.toJson(1d));
        System.out.println(gson.toJson(1d));

        System.out.println(jsonObject.toJson("aaa"));
        System.out.println(gson.toJson("aaa"));

        System.out.println(jsonObject.toJson(new int[]{1, 2, 3}));
        System.out.println(gson.toJson(new int[]{1, 2, 3}));

        System.out.println(jsonObject.toJson(Collections.singletonList(1)));
        System.out.println(gson.toJson(Collections.singletonList(1)));
    }
}
