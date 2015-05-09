package edu.cgxy.link;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class edu.cgxy.link.LinkActivityTest \
 * edu.cgxy.link.tests/android.test.InstrumentationTestRunner
 */
public class LinkActivityTest extends ActivityInstrumentationTestCase2<LinkActivity> {

    public LinkActivityTest() {
        super("edu.cgxy.link", LinkActivity.class);
    }

}
