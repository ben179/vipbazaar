package com.plainvanilla.test.strategy;

import com.plainvanilla.vipbazaar.model.User;
import org.hibernate.*;

import static com.plainvanilla.test.utils.TestUtils.*;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 25/09/13
 * Time: 14:23
 * To change this template use File | Settings | File Templates.
 */
public class SessionPerConversation implements InteractionStrategy {

    public void execute(SessionFactory sf, Long entityId) {

        Session session = sf.openSession();
        session.setFlushMode(FlushMode.MANUAL); // do not flush at TX.commit()
        try {

            // STEP 1
            Transaction t1 = session.beginTransaction();

            User userFromDb = (User)session.get(User.class, entityId);
            userFromDb.setFistName(Thread.currentThread().getName());

            t1.commit();


            randomUserThinkTime();

            // STEP 2
            Transaction t2 = session.beginTransaction();

            userFromDb.setLastName(Thread.currentThread().getName());

            t2.commit();

            randomUserThinkTime();

            // STEP 3
            Transaction t3 = session.beginTransaction();

            userFromDb.setPassword(Thread.currentThread().getName());

            session.flush();  // the last step of the conversation -> we have to flush the session
            t3.commit();

        } catch(HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
