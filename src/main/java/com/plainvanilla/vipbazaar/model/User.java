package com.plainvanilla.vipbazaar.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 02/09/13
 * Time: 14:43
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "USER")
public class User implements ModelEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "USER_ID", nullable = false)
    private Long id;

    @Column(name = "USER_LOGIN", nullable = false, unique = true)
    private String userName;

    @Column(name = "USER_PASSWORD", nullable = false)
    private String password;

    @Column(name = "FIRST_NAME")
    private String fistName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "RANKING")
    private int ranking;

    @Column(name = "IS_ADMIN", nullable = false)
    private boolean admin;

    @ElementCollection
    @CollectionTable(name = "USER_IMAGES", joinColumns = {@JoinColumn(name = "USER_ID")})
    private List<Image> images = new ArrayList<Image>();

    @OneToMany(mappedBy = "soldBy")
    @Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Set<Item> soldItems = new LinkedHashSet<Item>();

    @OneToMany(mappedBy = "boughtBy")
    @Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Set<Item> boughtItems = new LinkedHashSet<Item>();

    @OneToMany(mappedBy = "user")
    @Cascade(value=org.hibernate.annotations.CascadeType.ALL)
    private Set<BillingDetails> billingDetails = new LinkedHashSet<BillingDetails>();

    @OneToMany(mappedBy = "user")
    @Cascade(value=org.hibernate.annotations.CascadeType.ALL)
    private Set<Bid> bids = new LinkedHashSet<Bid>();

    @OneToOne
    @Cascade(value=org.hibernate.annotations.CascadeType.ALL)
    @JoinTable(name = "USER_DEFAULT_BILLING_DETAILS", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "BILLING_ID"))
    private BillingDetails defaultBillingDetails;

    @OneToMany(mappedBy = "buyer")
    @Cascade(value=org.hibernate.annotations.CascadeType.ALL)
    private Set<Shipment> receivedShipments = new HashSet<Shipment>();

    @OneToMany(mappedBy = "seller")
    @Cascade(value=org.hibernate.annotations.CascadeType.ALL)
    private Set<Shipment> sentShipments = new HashSet<Shipment>();

    @OneToMany(mappedBy="from", orphanRemoval = true)
    @Cascade(value=org.hibernate.annotations.CascadeType.ALL)
    private Set<Comment> comments = new HashSet<Comment>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "HOME_STREET")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "HOME_ZIP")),
            @AttributeOverride(name = "city", column = @Column(name = "HOME_CITY"))
    })
    private Address home;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "BILLING_ADDRESS", nullable = true)),
            @AttributeOverride(name = "zipCode", column = @Column(name = "BILLING_ZIP", nullable = true)),
            @AttributeOverride(name = "city", column = @Column(name = "BILLING_CITY", nullable = true))
    })
    private Address billing;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "SHIPPING_ADDRESS", nullable = true)),
            @AttributeOverride(name = "zipCode", column = @Column(name = "SHIPPING_ZIP", nullable = true)),
            @AttributeOverride(name = "city", column = @Column(name = "SHIPPING_CITY", nullable = true))
    })
    private Address shipping;

    public User() {

    }

    public User(String login, String password, String firstName, String lastName, String email, int ranking, boolean admin) {
        setUserName(login);
        setPassword(password);
        setFistName(firstName);
        setLastName(lastName);
        setEmail(email);
        setAdmin(admin);
        setRanking(ranking);

        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "User{" +
                "ranking=" + ranking +
                ", id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fistName='" + fistName + '\'' +
                ", email='" + email + '\'' +
                ", admin=" + admin +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void addSoldItem(Item item) {
        if (item == null) throw new IllegalStateException();
        soldItems.add(item);
        item.setSoldBy(this);
    }

    public void removeSoldItem(Item item) {
        soldItems.remove(item);
        item.setSoldBy(null);
    }

    public Set<Item> getSoldItems() {
        return Collections.unmodifiableSet(soldItems);
    }

    private void setSoldItems(Set<Item> soldItems) {
        this.soldItems = soldItems;
    }

    public Set<Item> getBoughtItems() {
        return Collections.unmodifiableSet(boughtItems);
    }

    private void setBoughtItems(Set<Item> boughtItems) {
        this.boughtItems = boughtItems;
    }

    public void addBoughtItem(Item item) {

        if (item == null) throw new IllegalStateException();

        boughtItems.add(item);
        item.setBoughtBy(this);
    }

    public void removeBoughtItem(Item item) {
        boughtItems.remove(item);
        item.setBoughtBy(null);
    }

    public Address getHome() {
        return home;
    }

    public void setHome(Address home) {
        this.home = home;
    }

     public Address getBilling() {
        return billing;
    }

    public void setBilling(Address billing) {
        this.billing = billing;
    }

    public Address getShipping() {
        return shipping;
    }

    public void setShipping(Address shipping) {
        this.shipping = shipping;
    }

    public void addBillingDetails(BillingDetails bd) {
        if (bd == null) throw new IllegalStateException();
        billingDetails.add(bd);
        bd.setUser(this);
        if (billingDetails.size() == 1) {
            defaultBillingDetails = (BillingDetails)billingDetails.toArray()[0];
        }
    }

    public void removeBillingDetails(BillingDetails bd) {
        if (bd == null) throw new IllegalStateException();
        billingDetails.remove(bd);
        bd.setUser(null);
        if (defaultBillingDetails.equals(bd)) {
            defaultBillingDetails = null;
        }

        if (billingDetails.size() == 1) {
            defaultBillingDetails = (BillingDetails)billingDetails.toArray()[0];
        }
    }

    public Set<BillingDetails> getBillingDetails() {
        return Collections.unmodifiableSet(billingDetails);
    }

    private void setBillingDetails(Set<BillingDetails> billingDetails) {
        this.billingDetails = billingDetails;
    }

    public BillingDetails getDefaultBillingDetails() {
        return defaultBillingDetails;
    }

    public void setDefaultBillingDetails(BillingDetails defaultBillingDetails) {
        if (!billingDetails.contains(defaultBillingDetails)) throw new IllegalStateException();
        this.defaultBillingDetails = defaultBillingDetails;
        defaultBillingDetails.setUser(this);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void addBid(Bid bid) {
        if (bid == null) throw new IllegalStateException();
        bids.add(bid);
        bid.setUser(this);
    }

    public void removeBid(Bid bid) {
        if (!bids.contains(bid)) throw new IllegalStateException();
        bids.remove(bid);
        bid.setUser(null);
    }

    public Set<Bid> getBids() {
        return Collections.unmodifiableSet(bids);
    }


    private void setBids(Set<Bid> bids) {
        this.bids = bids;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!userName.equals(user.userName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return lastName.hashCode();
    }

    public Set<Shipment> getReceivedShipments() {
        return Collections.unmodifiableSet(receivedShipments);
    }

    private void setReceivedShipments(Set<Shipment> receivedShipments) {
        this.receivedShipments = receivedShipments;
    }

    public Set<Shipment> getSentShipments() {
        return Collections.unmodifiableSet(sentShipments);
    }

    private void setSentShipments(Set<Shipment> sentShipments) {
        this.sentShipments = sentShipments;
    }


    public void addComment(String text, Item about) {
        if (text == null) throw new IllegalStateException();
        addComment(new Comment(text), about);
    }

    public void addComment(Comment comment, Item about) {
        if (about == null) throw new IllegalStateException();
        comment.setAbout(about);
        comment.setFrom(this);
        comments.add(comment);
    }

    public void addComment(Comment comment) {
        if (comment == null) throw new IllegalStateException();
        comments.add(comment);
        comment.setFrom(this);
    }

    public void removeComment(Comment comment) {
        if (comment == null) {
            throw new IllegalStateException();
        }
        comments.remove(comment);
        comment.setFrom(null);
    }

    public Set<Comment> getComments() {
        return Collections.unmodifiableSet(comments);
    }

    private void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
