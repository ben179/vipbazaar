package com.plainvanilla.vipbazaar.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 02/09/13
 * Time: 17:07
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="BID")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="BID_ID", nullable = false, updatable = false, insertable = false)
    private Long bidId;

    @Column(name="AMOUNT", nullable = false)
    private int amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED", nullable = false, updatable = false)
    private Date created;

    @ManyToOne(targetEntity = com.plainvanilla.vipbazaar.model.User.class)
    @JoinColumn(name = "PLACED_BY_USER")
    private User user;

    @ManyToOne(targetEntity = com.plainvanilla.vipbazaar.model.Item.class)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Long getBidId() {
        return bidId;
    }

    private void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bid)) return false;

        Bid bid = (Bid) o;

        if (amount != bid.amount) return false;
        if (!created.equals(bid.created)) return false;
        if (item != null ? !item.equals(bid.item) : bid.item != null) return false;
        if (user != null ? !user.equals(bid.user) : bid.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return amount;
    }
}
