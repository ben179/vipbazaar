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

    @Column(name = "STREET", nullable = false)
    private String street;
    @Column(name = "ZIP", nullable = false)
    private String zipCode;
    @Column(name = "CITY", nullable = false)
    private String city;

    public Address() {

    }

    public Address(String street, String zipCode, String city) {
        setCity(city);
        setStreet(street);
        setZipCode(zipCode);

        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        if (zipCode != null ? !zipCode.equals(address.zipCode) : address.zipCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return zipCode != null ? zipCode.hashCode() : 0;
    }
}
