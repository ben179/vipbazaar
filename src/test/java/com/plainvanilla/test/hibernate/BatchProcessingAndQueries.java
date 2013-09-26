package com.plainvanilla.test.hibernate;

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
public class BatchProcessingAndQueries {


    public static final int USER_COUNT = 100;
    private static Configuration cfg = null;
    private static SessionFactory sf = null;


    @BeforeClass
    public static void init() {
        cfg = new Configuration();

        cfg.addAnnotatedClass(Address.class).
                addAnnotatedClass(BankAccount.class).
                addAnnotatedClass(Bid.class).
                addAnnotatedClass(BillingDetails.class).
                addAnnotatedClass(Category.class).
                addAnnotatedClass(Comment.class).
                addAnnotatedClass(CreditCard.class).
                addAnnotatedClass(CreditCardType.class).
                addAnnotatedClass(Image.class).
                addAnnotatedClass(Item.class).
                addAnnotatedClass(ItemState.class).
                addAnnotatedClass(Shipment.class).
                addAnnotatedClass(ShipmentState.class).
                addAnnotatedClass(User.class);

        cfg.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver").
                setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:daydreamersdb10").
                setProperty("hibernate.connection.username", "sa").
                setProperty("hibernate.connection.password", "").
                setProperty("hibernate.hbm2ddl.auto", "create-drop").
                setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect").
                setProperty("hibernate.connection.pool_size", "5").
                setProperty("hibernate.show_sql", "true").
                setProperty("hibernate.cglib.use_reflection_optimizer", "true").
                setProperty("hibernate.connection.autocommit", "false").
                setProperty("hibernate.jdbc.batch_size", "20"). // we must set batch size to something reasonable
                setProperty("hibernate.cache.use_second_level_cache", "false"); // we should disable 2nd level cache

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
            assertEquals(q.list().size(), USER_COUNT);


        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }

    }

}
