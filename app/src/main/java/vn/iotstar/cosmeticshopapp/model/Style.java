package vn.iotstar.cosmeticshopapp.model;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

public class Style implements Serializable {
    private int id;
    private String styleName;
    private String styleImage;
    private int categoryId;
    private Timestamp createAt;
    private Timestamp updateAt;

    public Style(int id, String styleName, String styleImage, int categoryId, Timestamp createAt, Timestamp updateAt) {
        this.id = id;
        this.styleName = styleName;
        this.styleImage = styleImage;
        this.categoryId = categoryId;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getStyleImage() {
        return styleImage;
    }

    public void setStyleImage(String styleImage) {
        this.styleImage = styleImage;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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
