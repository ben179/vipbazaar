package com.plainvanilla.vipbazaar.model;

import org.apache.commons.lang3.time.DateUtils;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 02/09/13
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="COMMENT")
public class Comment implements ModelEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="COMMENT_ID", nullable = false, insertable = false, updatable = false)
    private Long id;

    @Version
    @Column(name="VERSION")
    private Integer version;

    @Column(name="TEXT", nullable=false)
    private String text;

    @Temporal(TemporalType.DATE)
    @Column(name="CREATED", nullable = false, updatable = false)
    private Date created = new Date();

    @Column(name="RATING")
    private int rating;

    @ManyToOne(targetEntity = com.plainvanilla.vipbazaar.model.Item.class)
    @JoinColumn(name = "ITEM_ID")
    private Item about;

    @ManyToOne(targetEntity = com.plainvanilla.vipbazaar.model.User.class)
    @JoinColumn(name = "USER_ID")
    private User from;

    public Comment() {}
    public Comment(String text) { this.text = text; }

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

    public Item getAbout() {
        return about;
    }

    public void setAbout(Item about) {
        this.about = about;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", created=" + created +
                ", rating=" + rating +
                ", about=" + about +
                ", from=" + from +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;

        Comment comment = (Comment) o;

        if (rating != comment.rating) return false;
        if (!DateUtils.truncatedEquals(created, comment.created, Calendar.DATE)) return false;
        if (text != null ? !text.equals(comment.text) : comment.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return rating;
    }
}
