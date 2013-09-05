package com.plainvanilla.vipbazaar.model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 02/09/13
 * Time: 17:02
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="BILLING_DETAILS")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BillingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="BILLING_ID", nullable = false, updatable = false, insertable = false)
    Long billingId;

    @Column(name="OWNER_NAME", nullable = false)
    String ownerName;

    @ManyToOne(targetEntity = com.plainvanilla.vipbazaar.model.User.class)
    @JoinColumn(name="USER_ID")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getBillingId() {
        return billingId;
    }

    private void setBillingId(Long billingId) {
        this.billingId = billingId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
