package ru.otus.hw5;

public class Main {
    public static void main(String[] args) {
        long beginTime = System.currentTimeMillis();
        for (int i=1;i<Integer.MAX_VALUE;i++) {
            try {
                Object[] objects = new Object[i*10000];
                objects[objects.length - 1] = i;
            } catch (Exception e) {
                System.out.println("time:" + (System.currentTimeMillis() - beginTime)/1000);
            }
        }
        System.out.println("time:" + (System.currentTimeMillis() - beginTime)/1000);
    }
}
