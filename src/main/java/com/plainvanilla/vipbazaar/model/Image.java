package com.plainvanilla.vipbazaar.model;

import org.apache.commons.lang3.time.DateUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 03/09/13
 * Time: 17:13
 * To change this template use File | Settings | File Templates.
 */

@Embeddable
public class Image implements Comparable<Image> {

    @Column(name="IMAGE_PATH", nullable=false)
    private String path;

    @Column(name="IMAGE_NAME", nullable=false)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name="IMAGE_DATE", nullable = false)
    private Date date;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int compareTo(Image another) {
        if (another == null) return 1;
        return (int)(this.getDate().getTime() - another.getDate().getTime());
    }

    @Override
    public String toString() {
        return "Image{" +
                "path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image)) return false;

        Image image = (Image) o;

        if (!DateUtils.truncatedEquals(date, image.date, Calendar.DATE)) return false;
        if (!name.equals(image.name)) return false;
        if (!path.equals(image.path)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
