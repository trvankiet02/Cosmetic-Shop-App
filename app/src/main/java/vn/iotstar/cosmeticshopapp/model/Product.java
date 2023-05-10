package vn.iotstar.cosmeticshopapp.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product implements Serializable
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("promotionalPrice")
    @Expose
    private Integer promotionalPrice;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("madeOf")
    @Expose
    private String madeOf;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("madeIn")
    @Expose
    private String madeIn;
    @SerializedName("store")
    @Expose
    private Store store;
    @SerializedName("isSelling")
    @Expose
    private Boolean isSelling;
    @SerializedName("createAt")
    @Expose
    private String createAt;
    @SerializedName("updateAt")
    @Expose
    private Object updateAt;
    @SerializedName("productImages")
    @Expose
    private List<ProductImage> productImages;
    @SerializedName("productQuantities")
    @Expose
    private List<ProductQuantity> productQuantities;
    private final static long serialVersionUID = -8478122976543636160L;

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPromotionalPrice() {
        return promotionalPrice;
    }

    public void setPromotionalPrice(Integer promotionalPrice) {
        this.promotionalPrice = promotionalPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMadeOf() {
        return madeOf;
    }

    public void setMadeOf(String madeOf) {
        this.madeOf = madeOf;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMadeIn() {
        return madeIn;
    }

    public void setMadeIn(String madeIn) {
        this.madeIn = madeIn;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
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

    public Object getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Object updateAt) {
        this.updateAt = updateAt;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public List<ProductQuantity> getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(List<ProductQuantity> productQuantities) {
        this.productQuantities = productQuantities;
    }
}