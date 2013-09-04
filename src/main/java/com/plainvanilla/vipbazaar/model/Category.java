package com.plainvanilla.vipbazaar.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 02/09/13
 * Time: 16:59
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="CATEGORY_NAME", nullable = false, updatable=false, insertable=false)
    private Long categoryId;

    @Column(name="NAME", nullable = false)
    private String name;

    private Category parent;

    private List<Category> children = new ArrayList<Category>();

    public Long getCategoryId() {
        return categoryId;
    }

    private void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    private List<Item> items = new ArrayList<Item>();

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
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
}
