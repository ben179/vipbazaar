package com.plainvanilla.vipbazaar.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 02/09/13
 * Time: 17:01
 * To change this template use File | Settings | File Templates.
 */

@Embeddable
public class Address {

    @Column(name="STREET", nullable=false)
    private String street;

    @Column(name="ZIP", nullable=false)
    private String zipCode;

    @Column(name="CITY", nullable=false)
    private String city;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
