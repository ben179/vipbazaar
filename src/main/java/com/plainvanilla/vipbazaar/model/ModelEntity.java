package com.plainvanilla.vipbazaar.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 17/09/13
 * Time: 08:59
 * To change this template use File | Settings | File Templates.
 */
public interface ModelEntity<T extends Serializable> {
    T getId();
}
