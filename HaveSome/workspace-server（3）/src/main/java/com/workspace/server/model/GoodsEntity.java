package com.workspace.server.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "goods", schema = "shiro", catalog = "")
public class GoodsEntity {
    private int gId;
    private String gName;
    private String gUrl;
    private double gprice;

    @Id
    @Column(name = "GId", nullable = false)
    public int getgId() {
        return gId;
    }

    public void setgId(int gId) {
        this.gId = gId;
    }

    @Basic
    @Column(name = "GName", nullable = false, length = 100)
    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    @Basic
    @Column(name = "GUrl", nullable = false, length = 100)
    public String getgUrl() {
        return gUrl;
    }

    public void setgUrl(String gUrl) {
        this.gUrl = gUrl;
    }

    @Basic
    @Column(name = "Gprice", nullable = false, precision = 0)
    public double getGprice() {
        return gprice;
    }

    public void setGprice(double gprice) {
        this.gprice = gprice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodsEntity that = (GoodsEntity) o;
        return gId == that.gId &&
                Double.compare(that.gprice, gprice) == 0 &&
                Objects.equals(gName, that.gName) &&
                Objects.equals(gUrl, that.gUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(gId, gName, gUrl, gprice);
    }
}
