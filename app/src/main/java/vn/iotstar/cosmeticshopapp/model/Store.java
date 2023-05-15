package vn.iotstar.cosmeticshopapp.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Store implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("storeImage")
    @Expose
    private String storeImage;
    @SerializedName("featureImage")
    @Expose
    private String featureImage;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("createAt")
    @Expose
    private String createAt;
    @SerializedName("updateAt")
    @Expose
    private String updateAt;
    @SerializedName("userFollowStores")
    @Expose
    private List<UserFollowStore> userFollowStores;
    @SerializedName("ewallet")
    @Expose
    private Integer ewallet;
    private final static long serialVersionUID = 2002024235378252062L;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }

    public String getFeatureImage() {
        return featureImage;
    }

    public void setFeatureImage(String featureImage) {
        this.featureImage = featureImage;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
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

    public List<UserFollowStore> getUserFollowStores() {
        return userFollowStores;
    }

    public void setUserFollowStores(List<UserFollowStore> userFollowStores) {
        this.userFollowStores = userFollowStores;
    }

    public Integer getEwallet() {
        return ewallet;
    }

    public void setEwallet(Integer ewallet) {
        this.ewallet = ewallet;
    }


}