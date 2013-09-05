package com.plainvanilla.vipbazaar.model;

import org.hibernate.annotations.CollectionType;

import javax.persistence.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 02/09/13
 * Time: 14:43
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="USER_ID", nullable = false, insertable = false, updatable = false)
    private Long userId;

    @Column(name="USER_LOGIN", nullable = false, insertable = false, updatable = false, unique = true)
    private String userName;

    @Column(name="USER_PASSWORD", nullable = false)
    private String password;

    @Column(name="FIRST_NAME")
    private String fistName;

    @Column(name="LAST_NAME", nullable = false)
    private String lastName;

    @Column(name="EMAIL", nullable = false)
    private String email;

    @Column(name="RANKING")
    private int ranking;

    @Column(name="IS_ADMIN", nullable = false)
    private boolean admin;

    @ElementCollection
    @CollectionTable(name="USER_IMAGES", joinColumns = {@JoinColumn(name="USER_ID")})
    private List<Image> images = new ArrayList<Image>();

    @OneToMany(mappedBy = "soldBy")
    private Set<Item> soldItems = new LinkedHashSet<Item>();

    @OneToMany(mappedBy = "boughtBy")
    private Set<Item> boughtItems = new LinkedHashSet<Item>();

    @OneToMany(mappedBy = "user")
    private Set<BillingDetails> billingDetails = new LinkedHashSet<BillingDetails>();

    @OneToOne
    @JoinTable(name="USER_DEFAULT_BILLING_DETAILS", joinColumns = @JoinColumn(name="USER_ID"), inverseJoinColumns = @JoinColumn(name="BILLING_ID"))
    private BillingDetails defaultBillingDetails;

    @AttributeOverrides({
            @AttributeOverride(name="street", column=@Column(name="HOME_STREET")),
            @AttributeOverride(name="zip", column=@Column(name="HOME_ZIP")),
            @AttributeOverride(name="city", column=@Column(name="HOME_CITY"))
    })
    @Embedded
    private Address home;


    @AttributeOverrides({
        @AttributeOverride(name="street", column=@Column(name="BILLING_ADDRESS", nullable=true)),
        @AttributeOverride(name="zip", column=@Column(name="BILLING_ZIP", nullable=true)),
        @AttributeOverride(name="city", column=@Column(name="BILLING_CITY", nullable=true))
    })
    @Embedded
    private Address billing;

    @AttributeOverrides({
            @AttributeOverride(name="street", column=@Column(name="SHIPPING_ADDRESS", nullable=true)),
            @AttributeOverride(name="zip", column=@Column(name="SHIPPING_ZIP", nullable=true)),
            @AttributeOverride(name="city", column=@Column(name="SHIPPING_CITY", nullable=true))
    })
    @Embedded
    private Address shipping;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public void addBoughtItem(Item item) {

        if (item == null) throw new IllegalStateException();

        boughtItems.add(item);
        item.setBoughtBy(this);
    }

    public void removeBoughtItem(Item item) {
        boughtItems.remove(item);
        item.setBoughtBy(null);
    }

    private void setBoughtItems(Set<Item> boughtItems) {
        this.boughtItems = boughtItems;
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

    public void addBillingDetails(BillingDetails bd){
        if (bd == null) throw new IllegalStateException();
        billingDetails.add(bd);
        bd.setUser(this);
    }

    public void removeBillingDetails(BillingDetails bd) {
        if (bd == null) throw new IllegalStateException();
        billingDetails.remove(bd);
        bd.setUser(null);
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
        this.defaultBillingDetails = defaultBillingDetails;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (admin != user.admin) return false;
        if (billing != null ? !billing.equals(user.billing) : user.billing != null) return false;
        if (!defaultBillingDetails.equals(user.defaultBillingDetails)) return false;
        if (!email.equals(user.email)) return false;
        if (!fistName.equals(user.fistName)) return false;
        if (!home.equals(user.home)) return false;
        if (!lastName.equals(user.lastName)) return false;
        if (!password.equals(user.password)) return false;
        if (shipping != null ? !shipping.equals(user.shipping) : user.shipping != null) return false;
        if (!userName.equals(user.userName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return lastName.hashCode();
    }
}
