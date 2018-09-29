// Demo.aidl
package com.example.administrator.myhookdemo;

// Declare any non-default types here with import statements

interface Demo {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    int sendData(String data);
    String getData();
}
