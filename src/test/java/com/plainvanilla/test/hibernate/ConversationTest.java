package com.plainvanilla.test.hibernate;

import com.plainvanilla.database.LogInterceptor;
import com.plainvanilla.test.hibernate.strategy.*;
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

        cfg = TestUtils.getConfiguration();
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

    public static void runUsers(int userCount, SessionFactory sf, Long entityId, Class<? extends InteractionStrategy> strategy) {
        StrategyExecutor[] users = new StrategyExecutor[userCount];
        try {
            for (int i = 0; i < userCount; i++) {
                users[i] = new StrategyExecutor("user" + i, strategy.newInstance(), sf, entityId);
            }
            waitForThreads(users);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
