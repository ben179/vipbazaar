package com.plainvanilla.vipbazaar.model;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 02/09/13
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ITEM")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ITEM_ID", nullable = false, updatable = false, insertable = false)
    private Long id;

    @Column(name = "ITEM_NAME", nullable = false)
    private String name;

    @Column(name = "ITEM_DESCRIPTION", nullable = true)
    private String description;

    @Column(name = "INITIAL_PRICE", nullable = false, updatable = false)
    private int initialPrice;

    @Column(name = "RESERVE_PRICE", nullable = true)
    private int reservePrice;

    @Temporal(TemporalType.DATE)
    @Column(name = "START_DATE", nullable = false, updatable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "END_DATE", nullable = true)
    private Date endDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "APPROVAL_DATETIME", nullable = true)
    private Date approvalDatetime;


    @Enumerated(EnumType.ORDINAL)
    @Column(name = "ITEM_STATE", nullable = false)
    private ItemState itemState;

    @ElementCollection
    @CollectionTable(name = "ITEM_IMAGES", joinColumns = {@JoinColumn(name = "ITEM_ID")})
    private List<Image> images = new ArrayList<Image>();

    @ManyToOne(targetEntity = com.plainvanilla.vipbazaar.model.User.class)
    @JoinColumn(name = "SOLD_BY_USER")
    private User soldBy;

    @ManyToOne
    @JoinColumn(name = "BOUGHT_BY_USER")
    private User boughtBy;

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER)
    @Cascade(value = CascadeType.ALL)
    @Sort(type = SortType.NATURAL)
    //@IndexColumn(name = "POSITION", base = 0)
    private SortedSet<Bid> bids = new TreeSet<Bid>();
         /*
    @ManyToOne
    @JoinColumn(name="WINNING_BID_ID", nullable = true)
    private Bid successfulBid;        */

    @ManyToMany
    @JoinTable(name = "ITEM_CATEGORY", joinColumns = @JoinColumn(name = "ITEM_ID"), inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID"))
    private Set<Category> categories = new LinkedHashSet<Category>();

    public Item(String name, String description, int initialPrice, int reservePrice, Date startDate, Date endDate, Date approvalDatetime, ItemState itemState) {
        this.name = name;
        this.description = description;
        this.initialPrice = initialPrice;
        this.reservePrice = reservePrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.approvalDatetime = approvalDatetime;
        this.itemState = itemState;

        System.out.println(toString());
    }


    public Item() {
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", initialPrice=" + initialPrice +
                ", reservePrice=" + reservePrice +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", approvalDatetime=" + approvalDatetime +
                ", itemState=" + itemState +
                '}';
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
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

        if (bids.isEmpty() || bids.last().getAmount() < bid.getAmount()) {
            bids.add(bid);
            bid.setItem(this);
          //  setSuccessfulBid(bid);
        }
    }

    public void removeBid(Bid bid) {
        if (bid == null) throw new IllegalStateException();
        bids.remove(bid);
        bid.setItem(null);
        /*
        if (successfulBid.equals(bid)) {
            if (!bids.isEmpty()) {
                setSuccessfulBid(bids.get(0));
            } else {
                setSuccessfulBid(null);
            }
        }    */
    }

    public SortedSet<Bid> getBids() {
        return Collections.unmodifiableSortedSet(bids);
    }

    private void setBids(SortedSet<Bid> bids) {
        this.bids = bids;
    }

    public Bid getSuccessfulBid() {
        if (bids.isEmpty()) {
            return null;
        }
        return bids.last();
    }
         /*
    private void setSuccessfulBid(Bid successfulBid) {
        if (!bids.contains(successfulBid)) throw new IllegalStateException();
        this.successfulBid = successfulBid;
    }      */

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
        if (!DateUtils.truncatedEquals(item.getApprovalDatetime(), this.getApprovalDatetime(), Calendar.DATE)) return false;
        if (description != null ? !description.equals(item.description) : item.description != null) return false;
        if (!DateUtils.truncatedEquals(item.getStartDate(), this.getStartDate(), Calendar.DATE)) return false;
        if (!DateUtils.truncatedEquals(item.getEndDate(), this.getEndDate(), Calendar.DATE)) return false;
        if (itemState != item.itemState) return false;
        if (!name.equals(item.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 31 * itemState.hashCode();
        return result;
    }
}
