package vn.iotstar.cosmeticshopapp.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Category implements Serializable {
    private int categoryId;
    private String categoryName;
    private String categoryImage;
    private Timestamp createAt;
    private Timestamp updateAt;

    public Category(int categoryId, String categoryName, String categoryImage, Timestamp createAt, Timestamp updateAt) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
