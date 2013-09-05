package com.plainvanilla.vipbazaar.model;

import javax.persistence.*;
import java.util.*;

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

    @ManyToOne(targetEntity=com.plainvanilla.vipbazaar.model.User.class)
    @JoinColumn(name="SOLD_BY_USER")
    private User soldBy;

    @ManyToOne
    @JoinColumn(name="BOUGHT_BY_USER")
    private User boughtBy;

    @OneToMany(mappedBy = "item")
    private Set<Bid> bids = new LinkedHashSet<Bid>();

    @OneToOne
    @JoinTable(name="ITEM_SUCCESSFUL_BID", joinColumns = @JoinColumn(name = "ITEM_ID"), inverseJoinColumns = @JoinColumn(name = "BID_ID"))
    private Bid successfulBid;

    @ManyToMany
    @JoinTable(name="ITEM_CATEGORY", joinColumns = @JoinColumn(name="ITEM_ID"), inverseJoinColumns = @JoinColumn(name="CATEGORY_ID"))
    private Set<Category> categories = new LinkedHashSet<Category>();


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

    public void addCategory(Category category) {
        if (category == null) throw new IllegalStateException();
        categories.add(category);
        category.addItem(this);
    }

    public void removeCategory(Category category) {
        if (category == null) throw new IllegalStateException();
        categories.remove(category);
        category.removeItem(this);
    }

    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    private void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public void addBid(Bid bid) {
        if (bid == null) throw new IllegalStateException();
        bids.add(bid);
        bid.setItem(this);
    }

    public void removeBid(Bid bid) {
        if (bid == null) throw new IllegalStateException();
        bids.remove(bid);
        bid.setItem(null);
    }

    public Set<Bid> getBids() {
        return Collections.unmodifiableSet(bids);
    }

    private void setBids(Set<Bid> bids) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (initialPrice != item.initialPrice) return false;
        if (reservePrice != item.reservePrice) return false;
        if (!approvalDatetime.equals(item.approvalDatetime)) return false;
        if (description != null ? !description.equals(item.description) : item.description != null) return false;
        if (endDate != null ? !endDate.equals(item.endDate) : item.endDate != null) return false;
        if (itemState != item.itemState) return false;
        if (!name.equals(item.name)) return false;
        if (startDate != null ? !startDate.equals(item.startDate) : item.startDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = approvalDatetime.hashCode();
        result = 31 * result + itemState.hashCode();
        return result;
    }
}
