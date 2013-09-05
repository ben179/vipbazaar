package com.plainvanilla.vipbazaar.model;

import javax.persistence.*;
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
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="COMMENT_ID", nullable = false, insertable = false, updatable = false)
    private Long commentId;

    @Column(name="TEXT", nullable=false)
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED", nullable = false, updatable = false)
    private Date created;

    @Column(name="RATING")
    private int rating;

    @ManyToOne(targetEntity = com.plainvanilla.vipbazaar.model.Item.class)
    @JoinColumn(name = "ITEM_ID")
    private Item about;

    @ManyToOne(targetEntity = com.plainvanilla.vipbazaar.model.User.class)
    @JoinColumn(name = "USER_ID")
    private User from;

    public Long getCommentId() {
        return commentId;
    }

    private void setCommentId(Long commentId) {
        this.commentId = commentId;
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
}
