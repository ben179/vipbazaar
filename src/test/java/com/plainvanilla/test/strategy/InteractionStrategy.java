package com.plainvanilla.test.strategy;

import org.hibernate.SessionFactory;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 25/09/13
 * Time: 14:22
 * To change this template use File | Settings | File Templates.
 */
public interface InteractionStrategy {

    public void execute(SessionFactory sf, Long entityId);
}
