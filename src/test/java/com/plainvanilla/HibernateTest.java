package com.plainvanilla;

import com.plainvanilla.vipbazaar.model.*;
import junit.framework.Test;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import static org.junit.Assert.*;

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

    private static User buyer, seller;
    private static Item item;

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

        cfg.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver").
                setProperty("hibernate.connection.url", "jdbc:hsqldb:file:daydreamersdb7").
                setProperty("hibernate.connection.username", "sa").
                setProperty("hibernate.connection.password", "").
                setProperty("hibernate.c3p0.min_size", "5").
                setProperty("hibernate.c3p0.max_size", "20").
                setProperty("hibernate.c3p0.timeout", "1800").
                setProperty("hibernate.c3p0.max_statements", "50").
                setProperty("hibernate.hbm2ddl.auto", "create-drop").
                setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect").
                setProperty("hibernate.connection.pool_size", "5").
                setProperty("hibernate.show_sql", "true").
                setProperty("hibernate.cglib.use_reflection_optimizer", "true").
                setProperty("hibernate.connection.autocommit", "false");

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
    public void testCreateEntities() {

        buyer = initPojo(User.class);
        seller = initPojo(User.class);
        item = initPojo(Item.class);

        Address address = initPojo(Address.class);
        Address address2 = initPojo(Address.class);
        BillingDetails buyerCard = initPojo(CreditCard.class);
        BillingDetails sellerAccount = initPojo(BankAccount.class);
        Bid bid1 = initPojo(Bid.class);
        bid1.setAmount(100);
        Bid bid2 = initPojo(Bid.class);
        bid2.setAmount(101);


        item.addBid(bid1);
        item.addBid(bid2);
        buyer.addBid(bid1);
        buyer.addBid(bid2);

        buyer.addBoughtItem(item);
        buyer.setHome(address);
        buyer.addBillingDetails(buyerCard);

        seller.addSoldItem(item);
        seller.setHome(address2);
        seller.addBillingDetails(sellerAccount);

        s.save(buyer);
        s.save(seller);

        assertNotNull(item.getId());
        assertNotNull(buyer.getId());

        // test cascading
        assertNotNull(buyerCard.getId());
        assertNotNull(sellerAccount.getId());
        assertNotNull(seller.getId());
        assertNotNull(bid1.getId());
        assertNotNull(bid2.getId());
        assertNotNull(item.getSuccessfulBid());
    }


    @org.junit.Test
    public void testGetEntities() {

       User seller2 = (User)s.get(User.class, (Serializable) seller.getId());
       User buyer2 = (User)s.get(User.class, (Serializable) buyer.getId());
       Item item2 = (Item)s.get(Item.class, (Serializable) item.getId());


       assertNotNull(seller2);
       assert(seller2.equals(seller));
       assertNotNull(buyer2);
       assert(buyer2.equals(buyer));
       assertNotNull(item2);
       assert(item2.equals(item));

       BillingDetails sellerBD = seller2.getDefaultBillingDetails();
       BillingDetails buyerBD = buyer2.getDefaultBillingDetails();

       assertNotNull(sellerBD);
       assert(sellerBD.equals(seller.getDefaultBillingDetails()));
       assertNotNull(buyerBD);
       assert(buyerBD.equals(buyer.getDefaultBillingDetails()));
       assertNotNull(item2.getBids());
       assertTrue(item2.getBids().size() == 2);
       assertNotNull(item2.getSuccessfulBid());
       assertTrue(item.getSuccessfulBid().equals(item2.getSuccessfulBid()));
       assertTrue(item.getBids().size() == item2.getBids().size());

    }

    @org.junit.Test
    public void testLoadEntities() {

    }


    @org.junit.Test
    public void testUpdateEntities() {

    }

    @org.junit.Test
    public void testRemoveEntities() {

    }


    public static <T> T initPojo(Class<T> pojo) {
        T instance = null;

        try {
            instance = pojo.newInstance();
            initPojos(instance);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return instance;
        }
    }

    public static <T> void initPojos(T... pojos) {

        for (T pojo : pojos) {

            Field[] fields = pojo.getClass().getDeclaredFields();
            for (Field field : fields) {

                field.setAccessible(true);

                StringBuilder sb = new StringBuilder(field.getName());
                String firstLetter = sb.substring(0, 1);
                sb.setCharAt(0, firstLetter.toUpperCase().charAt(0));
                sb.insert(0, "set");
                String setterName = sb.toString();

                Method setter = null;

                try {
                    setter = pojo.getClass().getDeclaredMethod(setterName);
                    setter.setAccessible(true);
                } catch (NoSuchMethodException e) {
                    //    System.out.println("Setter " + pojo.getClass().getName() + "." + setterName + " not found for field :" + field.getName());
                }

                // skip JPA @Id fields
                if (field.isAnnotationPresent(javax.persistence.Id.class) || (setter != null && setter.isAnnotationPresent(javax.persistence.Id.class))) {
                    continue;
                }

                Class type = field.getType();
                try {
                    if (type.equals(String.class)) {
                        field.set(pojo, RandomStringUtils.randomAlphabetic(((int)(Math.random() * 15)) + 1));
                    } else if (type.equals(Date.class)) {
                        field.set(pojo, new Date((long)(Math.random() * new Date().getTime())));
                    } else if (type.equals(int.class) || type.equals(Integer.class) || type.equals(long.class) || type.equals(Long.class)) {
                        field.set(pojo, (int) (Math.random() * 1000));
                    } else if (type.isEnum()) {
                        final Object[] constants = type.getEnumConstants();
                        final int order = (int)(Math.random() * constants.length);
                        field.set(pojo, constants[order]);
                    } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
                        boolean value = (Math.random() > 0.5);
                        field.set(pojo, value);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(pojo.toString());
        }
    }
}
