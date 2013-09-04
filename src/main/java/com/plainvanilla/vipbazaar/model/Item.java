package com.plainvanilla.vipbazaar.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 02/09/13
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="ITEM")
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ITEM_ID", nullable = false, updatable = false, insertable = false)
    private Long itemId;

    @Column(name="ITEM_NAME", nullable = false)
    private String name;

    @Column(name="ITEM_DESCRIPTION", nullable = true)
    private String description;

    @Column(name="INITIAL_PRICE", nullable = false, updatable = false)
    private int initialPrice;

    @Column(name="RESERVE_PRICE", nullable = true)
    private int reservePrice;

    @Temporal(TemporalType.DATE)
    @Column(name="START_DATE", nullable=false, updatable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name="END_DATE", nullable=true)
    private Date endDate;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="APPROVAL_DATETIME", nullable=true)
    private Date approvalDatetime;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="ITEM_STATE", nullable=false)
    private ItemState itemState;


    @ElementCollection
    @CollectionTable(name="ITEM_IMAGES", joinColumns = {@JoinColumn(name="ITEM_ID")})
    private List<Image> images = new ArrayList<Image>();


    private User soldBy;
    private User boughtBy;

    private List<Bid> bids = new ArrayList<Bid>();
    private Bid successfulBid;
    private List<Category> categories = new ArrayList<Category>();


    public Long getItemId() {
        return itemId;
    }

    private void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public Bid getSuccessfulBid() {
        return successfulBid;
    }

    public void setSuccessfulBid(Bid successfulBid) {
        this.successfulBid = successfulBid;
    }

    public ItemState getItemState() {
        return itemState;
    }

    public void setItemState(ItemState itemState) {
        this.itemState = itemState;
    }

    public User getBoughtBy() {
        return boughtBy;
    }

    public void setBoughtBy(User boughtBy) {
        this.boughtBy = boughtBy;
    }

    public User getSoldBy() {
        return soldBy;
    }

    public void setSoldBy(User soldBy) {
        this.soldBy = soldBy;
    }

    public Date getApprovalDatetime() {
        return approvalDatetime;
    }

    public void setApprovalDatetime(Date approvalDatetime) {
        this.approvalDatetime = approvalDatetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(int initialPrice) {
        this.initialPrice = initialPrice;
    }

    public int getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(int reservePrice) {
        this.reservePrice = reservePrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
