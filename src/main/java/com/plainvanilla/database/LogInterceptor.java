package com.plainvanilla.database;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;

import java.io.Serializable;
import org.hibernate.type.Type;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 17/09/13
 * Time: 08:55
 * To change this template use File | Settings | File Templates.
 */
public class LogInterceptor extends EmptyInterceptor {

    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types ) {
        System.out.println("----------------------------------");
        System.out.println("ON DELETE : " + entity.toString());
    }

    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] type) {
        System.out.println("----------------------------------");
        System.out.println("ON FLUSH DIRTY : " + entity.toString());
        System.out.println("OLD STATE : " + showArray(previousState));
        System.out.println("NEW STATE : " + showArray(currentState));
        return true;
    }

    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        System.out.println("----------------------------------");
        System.out.println("ON LOAD : " + entity.toString());
        return true;
    }

    public boolean onSave(Object entity, Serializable id, Object state[], String [] propertyNames, Type[] types) {
        System.out.println("----------------------------------");
        System.out.println("ON SAVE : " + entity.toString());
        return true;
    }

    public void afterTransactionCompletion(Transaction tx) {
        System.out.print("ON TX COMPLETE : ");
        if (tx.wasCommitted()) {
            System.out.println("COMMITTED");
        }

        if (tx.wasRolledBack()) {
            System.out.println("ROLLED BACK");
        }
    }

    private static String showArray(Object [] array) {
        StringBuilder sb = new StringBuilder();
        if (array == null) {
            return new String();
        }
        for (Object item : array) {
            if (item != null)
            sb.append(item.toString()).append("|");
        }
        return sb.toString();
    }
}
