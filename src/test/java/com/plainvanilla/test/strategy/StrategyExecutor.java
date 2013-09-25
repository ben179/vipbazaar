package com.plainvanilla.test.strategy;

import com.plainvanilla.test.strategy.InteractionStrategy;
import org.hibernate.SessionFactory;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 25/09/13
 * Time: 14:28
 * To change this template use File | Settings | File Templates.
 */
public class StrategyExecutor extends Thread {

    private InteractionStrategy strategy;
    private SessionFactory sf;
    private Long entityId;

    public StrategyExecutor(String name, InteractionStrategy strategy, SessionFactory sf, Long entityId) {
        this.strategy = strategy;
        this.sf = sf;
        this.entityId = entityId;
        this.setName(name);
        start();
    }

    public void run() {
        strategy.execute(sf, entityId);
    }
}
