// StudentQuery.aidl
package com.example.yucong.remoteservice;

// Declare any non-default types here with import statements

interface StudentQuery {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


            String queryStudent(int number);
}
