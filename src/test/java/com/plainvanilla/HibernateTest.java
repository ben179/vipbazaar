package com.plainvanilla;

import com.plainvanilla.vipbazaar.model.*;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Annotated;
import junit.framework.Test;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 06/09/13
 * Time: 11:14
 * To change this template use File | Settings | File Templates.
 */

public class HibernateTest {

    private static Configuration cfg;
    private static SessionFactory sf;
    private static Session s;
    private static Transaction tx;


    @BeforeClass
    public static void setUpConfiguration() {

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

        cfg.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
        cfg.setProperty("hibernate.connection.url", "jdbc:hsqldb:file:daydreamersdb");
        cfg.setProperty("hibernate.connection.username", "sa");
        cfg.setProperty("hibernate.connection.password", "");

        cfg.setProperty("hibernate.c3p0.min_size", "5");
        cfg.setProperty("hibernate.c3p0.max_size", "20");
        cfg.setProperty("hibernate.c3p0.timeout", "1800");
        cfg.setProperty("hibernate.c3p0.max_statements", "50");

        cfg.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        cfg.setProperty("hibernate.connection.pool_size", "5");
        cfg.setProperty("hibernate.show_sql", "true");
        cfg.setProperty("hibernate.cglib.use_reflection_optimizer", "true");
        cfg.setProperty("hibernate.connection.autocommit", "false");

        sf = cfg.buildSessionFactory();

    }

    @Before
    public void setUpSession() {
        s = sf.openSession();
        tx = s.beginTransaction();
    }

    @After
    public void commit() {
        if (s != null && tx != null) {
            tx.commit();
            s.close();
        }
    }

    @org.junit.Test
    public void testCreateUser() {

        User user = new User();
        user.setAdmin(false);
        user.setEmail("vanilla@email.cz");
        user.setFistName("Borek");
        user.setLastName("Sipek");
        user.setPassword("hamidi");
        user.setRanking(5);
        user.setUserName("ben179");

        Address add = new Address();
        add.setCity("Prague");
        add.setStreet("Myslikova 31");
        add.setZipCode("11000");

        user.setHome(add);

        s.save(user);

    }





}
