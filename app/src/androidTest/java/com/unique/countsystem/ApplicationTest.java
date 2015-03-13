package com.unique.countsystem;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.unique.countsystem.database.DbHelper;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testParsetest(){
        assertTrue(DbHelper.checkStudentId("U301317486"));
    }
}