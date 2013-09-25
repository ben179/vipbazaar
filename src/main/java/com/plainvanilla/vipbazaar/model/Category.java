package com.plainvanilla.vipbazaar.model;

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
 * Time: 16:59
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="CATEGORY")
public class Category implements ModelEntity<Long> {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="CATEGORY_NAME", nullable = false, updatable=false, insertable=false)
    private Long id;

    @Version
    @Column(name="VERSION")
    private Integer version;

    @Column(name="NAME", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="PARENT_CATEGORY", nullable = true)
    private Category parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @Cascade(value=org.hibernate.annotations.CascadeType.ALL)
    private Set<Category> children = new LinkedHashSet<Category>();

    @ManyToMany(mappedBy = "categories")
    @Cascade(value=CascadeType.ALL)
    private Set<Item> items = new LinkedHashSet<Item>();

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

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public Set<Item> getItems() {
        return Collections.unmodifiableSet(items);
    }

    private void setItems(Set<Item> items) {
        this.items = items;
    }

    public void addCategory(Category category) {
        if (category == null) throw new IllegalStateException();
        children.add(category);
        category.setParent(this);
    }

    public void removeCategory(Category category) {
        children.remove(category);
        category.setParent(null);
    }

    public Set<Category> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    private void setChildren(Set<Category> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {

        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent=" + ((parent == null) ? "null" : parent.getName()) +
                ", children=" + children +
                ", items=" + items +
                '}';
    }
}
