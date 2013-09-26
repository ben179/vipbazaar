package com.plainvanilla.test.hibernate;

import com.plainvanilla.database.LogInterceptor;
import com.plainvanilla.test.hibernate.strategy.ConversationWithDetachedObjects;
import com.plainvanilla.test.hibernate.strategy.InteractionStrategy;
import com.plainvanilla.test.hibernate.strategy.PessimisticLocking;
import com.plainvanilla.test.hibernate.strategy.SessionPerConversation;
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
 * Date: 23/09/13
 * Time: 16:25
 * To change this template use File | Settings | File Templates.
 */
public class ConversationTest {

    private static Configuration cfg;
    private static SessionFactory sf;
    private static Session s;
    private static Transaction tx;

    private static User buyer, seller;
    private static Item item;
    private static Category root;

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
                setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:daydreamersdb10").
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
                setProperty("hibernate.connection.autocommit", "false").
                setProperty("hibernate.hbm2ddl.import_files", "/WEB-INF/resources/sql/import.sql");

        cfg.setInterceptor(new LogInterceptor());

        sf = cfg.buildSessionFactory();

    }


    /**
     * Three people are changing one user entity in the database at the same time
     * they all change firstName, lastName and password (with random think time in between)
     * at the end it is tested that only the fastest of them succeeds to flush the 3-step conversation
     * -> all three properties are modified by him
     */
    @Test
    public void testSessionPerConversation() {
        testStrategy(SessionPerConversation.class);
    }

    @Test
    public void testConversationWithDetachedObjects() {
        testStrategy(ConversationWithDetachedObjects.class);
    }

    @Test
    public void testPessimisticLocking() {
        testStrategy(PessimisticLocking.class);
    }

    private void testStrategy(Class<? extends InteractionStrategy> strategy) {

        final User u = initPojo(User.class);

        saveEntity(u, sf);

        runUsers(3, sf, u.getId(), strategy);

        User userFromDB2 = (User)getEntityById(u.getId(), sf, User.class);

        // check that all three properties were modified by a single user
        assertEquals(userFromDB2.getFirstName(), userFromDB2.getLastName());
        assertEquals(userFromDB2.getLastName(), userFromDB2.getPassword());
    }

}
