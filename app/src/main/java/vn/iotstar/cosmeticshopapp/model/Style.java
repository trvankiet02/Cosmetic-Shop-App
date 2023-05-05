package vn.iotstar.cosmeticshopapp.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Style {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("styleImage")
    @Expose
    private String styleImage;
    @SerializedName("isSelling")
    @Expose
    private Boolean isSelling;
    @SerializedName("createAt")
    @Expose
    private String createAt;
    @SerializedName("updateAt")
    @Expose
    private String updateAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyleImage() {
        return styleImage;
    }

    public void setStyleImage(String styleImage) {
        this.styleImage = styleImage;
    }

    public Boolean getIsSelling() {
        return isSelling;
    }

    public void setIsSelling(Boolean isSelling) {
        this.isSelling = isSelling;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

}
