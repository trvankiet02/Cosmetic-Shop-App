package vn.iotstar.cosmeticshopapp.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Category implements Serializable {
    private int id;
    private String name;
    private String categoryImage;
    private Boolean isSelling;

    private Timestamp createAt;
    private Timestamp updateAt;

    public Category(int categoryId, String categoryName, String categoryImage, Timestamp createAt, Timestamp updateAt) {
        this.id = categoryId;
        this.name = categoryName;
        this.categoryImage = categoryImage;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelling() {
        return isSelling;
    }

    public void setSelling(Boolean selling) {
        isSelling = selling;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }
}
