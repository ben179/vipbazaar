package com.plainvanilla.vipbazaar.model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 02/09/13
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "BANK_ACCOUNT")
@PrimaryKeyJoinColumn(name="BANK_ACCOUNT_ID")
public class BankAccount extends BillingDetails {

    @Column(name="ACCOUNT_NUMBER", nullable = false)
    private String number;

    @Column(name="BANK_NAME", nullable = false)
    private String bankName;

    @Column(name="SWIFT", nullable = false)
    private String swift;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "number='" + number + '\'' +
                ", bankName='" + bankName + '\'' +
                ", swift='" + swift + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccount)) return false;

        BankAccount that = (BankAccount) o;

        if (!bankName.equals(that.bankName)) return false;
        if (!number.equals(that.number)) return false;
        if (!swift.equals(that.swift)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return bankName.hashCode();
    }
}
