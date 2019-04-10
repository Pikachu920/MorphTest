package com.eric.morphtest;

public class TestClass {

    public static boolean changeMe(int i) {
        System.out.println("Here's the original message!");
        return true;
    }

    public boolean changeMe(long l) {
        throw new RuntimeException("This should never be called! You grabbed the instance method!");
    }

}
