package com.plainvanilla.test.hibernate.strategy;

import com.plainvanilla.vipbazaar.model.User;
import org.hibernate.*;

import static com.plainvanilla.test.utils.TestUtils.*;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 25/09/13
 * Time: 16:51
 * To change this template use File | Settings | File Templates.
 */
public class PessimisticLocking implements InteractionStrategy {

    @Override
    public void execute(SessionFactory sf, Long entityId) {

        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        try {

            User lockedUser = (User) session.load(User.class, entityId, LockOptions.UPGRADE);

            lockedUser.setFirstName(Thread.currentThread().getName());

            randomUserThinkTime();

            lockedUser.setLastName(Thread.currentThread().getName());

            randomUserThinkTime();

            lockedUser.setPassword(Thread.currentThread().getName());

            tx.commit();

        } catch (HibernateException e) {
            tx.rollback();
        } finally {
            session.close();
        }
    }
}
