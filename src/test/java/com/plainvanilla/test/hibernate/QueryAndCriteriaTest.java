package com.plainvanilla.test.hibernate;

import com.plainvanilla.test.utils.TestUtils;
import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;

import static com.plainvanilla.test.utils.TestUtils.*;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 27/09/13
 * Time: 10:02
 * To change this template use File | Settings | File Templates.
 */
public class QueryAndCriteriaTest {

    private static Configuration cfg = null;


    @BeforeClass
    public static void init() {
        cfg = TestUtils.getConfiguration();
    }
}
