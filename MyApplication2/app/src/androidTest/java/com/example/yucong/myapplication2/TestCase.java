package com.example.yucong.myapplication2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TestCase  {
    Context appContext=null;
    @Test
    public void useAppContext() {
        // Context of the app under test.
       appContext = InstrumentationRegistry.getTargetContext();
    }

}
