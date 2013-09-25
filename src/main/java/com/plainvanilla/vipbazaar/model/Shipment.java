package com.plainvanilla.vipbazaar.model;

import org.apache.commons.lang3.time.DateUtils;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 02/09/13
 * Time: 17:09
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="SHIPMENT")
public class Shipment implements ModelEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="SHIPMENT_ID", nullable = false, updatable = false, insertable = false)
    private Long id;

    @Version
    @Column(name="VERSION")
    private Integer version;

    @Column(name="INSPECTION_DAYS")
    private int inspectionPeriodDays;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="SHIPMENT_STATE", nullable = false)
    private ShipmentState state;

    @Temporal(TemporalType.DATE)
    @Column(name="CREATED", nullable = false, updatable = false)
    private Date created;

    @OneToOne(mappedBy = "shipment")
    private Item item;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="SHIPMENT_BUYER")
    private User buyer;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="SHIPMENT_SELLER")
    private User seller;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "SHIPMENT_ADDRESS", nullable = true)),
            @AttributeOverride(name = "zipCode", column = @Column(name = "SHIPMENT_ZIP", nullable = true)),
            @AttributeOverride(name = "city", column = @Column(name = "SHIPMENT_CITY", nullable = true))
    })
    private Address delivery;

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

    public int getInspectionPeriodDays() {
        return inspectionPeriodDays;
    }

    public void setInspectionPeriodDays(int inspectionPeriodDays) {
        this.inspectionPeriodDays = inspectionPeriodDays;
    }

    public ShipmentState getState() {
        return state;
    }

    public void setState(ShipmentState state) {
        this.state = state;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Address getDelivery() {
        return delivery;
    }

    public void setDelivery(Address delivery) {
        this.delivery = delivery;
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "id=" + id +
                ", inspectionPeriodDays=" + inspectionPeriodDays +
                ", state=" + state +
                ", created=" + created +
                ", item=" + item +
                ", buyer=" + buyer +
                ", seller=" + seller +
                ", delivery=" + delivery +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shipment)) return false;

        Shipment shipment = (Shipment) o;

        if (inspectionPeriodDays != shipment.inspectionPeriodDays) return false;
        if (!DateUtils.truncatedEquals(created, shipment.created, Calendar.DATE)) return false;
        if (!delivery.equals(shipment.delivery)) return false;
        if (state != shipment.state) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = state.hashCode();
        result = 31 * result + created.hashCode();
        return result;
    }
}
