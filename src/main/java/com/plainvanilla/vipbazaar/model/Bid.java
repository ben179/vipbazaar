package com.plainvanilla.vipbazaar.model;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 02/09/13
 * Time: 17:07
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Immutable
@Table(name="BID")
public final class Bid implements Comparable<Bid>, ModelEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="BID_ID", nullable = false, updatable = false, insertable = false)
    private Long id;

    @Version
    @Column(name="VERSION")
    private Integer version;


    @Column(name="AMOUNT", nullable = false, updatable = false)
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
        if (!DateUtils.truncatedEquals(created, bid.created, Calendar.DAY_OF_MONTH)) return false;
       // if (item != null ? !item.equals(bid.item) : bid.item != null) return false;
        if (user != null ? !user.equals(bid.user) : bid.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return amount;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", amount=" + amount +
                ", created=" + created +
                ", user=" + user +
                ", item=" + item +
                '}';
    }

    @Override
    public int compareTo(Bid another) {
        if (another == null) {
            return 1;
        }
        return this.getAmount() - another.getAmount();
    }
}
