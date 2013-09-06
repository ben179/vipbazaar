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
   /*
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="BANK_ACCOUNT_ID", nullable = false, updatable = false, insertable = false)
    private Long bankAccountId;*/

    @Column(name="ACCOUNT_NUMBER", nullable = false)
    private String number;

    @Column(name="BANK_NAME", nullable = false)
    private String bankName;

    @Column(name="SWIFT", nullable = false)
    private String swift;
     /*
    public Long getBankAccountId() {
        return bankAccountId;
    }

    private void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }      */

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
}
