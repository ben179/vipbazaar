package com.plainvanilla.test.hibernate.strategy;

import com.plainvanilla.vipbazaar.model.User;
import org.hibernate.*;

import static com.plainvanilla.test.utils.TestUtils.*;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 25/09/13
 * Time: 14:54
 * To change this template use File | Settings | File Templates.
 */
public class ConversationWithDetachedObjects implements InteractionStrategy {

    private interface UserCallback {

        void updateUser(User u);

    }

    public void execute(SessionFactory sf, Long entityId) {

        try {

            User userFromDB = (User)getEntityById(entityId, sf, User.class);

            attachDetachedUser(userFromDB, sf, new UserCallback() {
                @Override
                public void updateUser(User u) {
                    u.setFirstName(Thread.currentThread().getName());
                }
            });

            randomUserThinkTime();

            attachDetachedUser(userFromDB, sf, new UserCallback() {
                @Override
                public void updateUser(User u) {
                    u.setLastName(Thread.currentThread().getName());
                }
            });

            randomUserThinkTime();

            attachDetachedUser(userFromDB, sf, new UserCallback() {
                @Override
                public void updateUser(User u) {
                    u.setPassword(Thread.currentThread().getName());
                }
            });

        } catch (HibernateException e) {
            // to be handled
        }

    }


    private void attachDetachedUser(User user, SessionFactory sf, UserCallback callback) {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        try {

            callback.updateUser(user);
            session.saveOrUpdate(user);
            tx.commit();

        } catch (HibernateException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }

    }
}
