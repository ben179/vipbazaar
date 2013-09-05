package com.plainvanilla.vipbazaar.model;

import javax.persistence.*;
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
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="SHIPMENT_ID", nullable = false, updatable = false, insertable = false)
    private Long shipmentId;

    @Column(name="INSPECTION_DAYS")
    private int inspectionPeriodDays;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="SHIPMENT_STATE", nullable = false)
    private ShipmentState state;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED", nullable = false, updatable = false)
    private Date created;

    @OneToOne
    @JoinTable(name="ITEM_SHIPMENT", joinColumns = @JoinColumn(name = "SHIPMENT_ID"), inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
    private Item item;

    @OneToOne
    @JoinTable(name="BUYER_SHIPMENT", joinColumns = @JoinColumn(name = "SHIPMENT_ID"), inverseJoinColumns = @JoinColumn(name="USER_ID"))
    private User buyer;

    @OneToOne
    @JoinTable(name="SELLER_SHIPMENT", joinColumns =  @JoinColumn(name="SHIPMENT_ID"), inverseJoinColumns = @JoinColumn(name="USER_ID"))
    private User seller;

    @Embedded
    private Address delivery;

    public Long getShipmentId() {
        return shipmentId;
    }

    private void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
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
}
