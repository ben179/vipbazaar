package com.plainvanilla.test.hibernate;

import com.plainvanilla.test.utils.TestUtils;
import com.plainvanilla.vipbazaar.model.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;
import static com.plainvanilla.test.utils.TestUtils.*;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 26/09/13
 * Time: 12:38
 * To change this template use File | Settings | File Templates.
 */
public class BatchProcessingTest {
    public static final int USER_COUNT = 100;
    private static Configuration cfg = null;
    private static SessionFactory sf = null;


    @BeforeClass
    public static void init() {
        cfg = TestUtils.getConfiguration();
        sf = cfg.buildSessionFactory();
    }

    @Test
    public void batchInsert() {

        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        for (int i  = 0; i < USER_COUNT; i++) {
            User u = initPojo(User.class);
            session.saveOrUpdate(u);

            if (i % 20 == 0) {
                session.flush();
                session.clear();
            }

        }

        tx.commit();
        session.close();
    }

    @Test
    public void updateBatch(){
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        ScrollableResults users = session.createQuery("from User").setCacheMode(CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);

        int count = 0;

        while(users.next()) {
            User user = (User) users.get(0);
            user.setFirstName("Martin");
            user.setLastName("Bazmek");
            user.setPassword("tralala");

            if (++count % 20 == 0) {
                session.flush();
                session.clear();
            }
        }

        tx.commit();
        session.close();

    }


    @Test
    public void testResults() {

        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        try {

            Query q = session.createQuery("from User");
            assertEquals(q.list().size(), USER_COUNT);

            Query q2 = session.createQuery("from User u where u.firstName = 'Martin' and u.lastName='Bazmek' and u.password='tralala'");
            assertEquals(q2.list().size(), USER_COUNT);


        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }

    }

}
