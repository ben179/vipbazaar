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
public abstract class BillingDetails implements ModelEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="BILLING_ID", nullable = false)
    Long id;

    @Version
    @Column(name="VERSION")
    private Integer version;

    @Column(name="OWNER_NAME", nullable = true)
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

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    private void setVersion(Integer version) {
        this.version = version;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public String toString() {
        return "BillingDetails{" +
                "id=" + id +
                ", ownerName='" + ownerName + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BillingDetails)) return false;

        BillingDetails that = (BillingDetails) o;

        if (ownerName != null ? !ownerName.equals(that.ownerName) : that.ownerName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return ownerName != null ? ownerName.hashCode() : 0;
    }
}
