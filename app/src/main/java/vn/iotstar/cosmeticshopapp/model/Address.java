package vn.iotstar.cosmeticshopapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Address implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("address")
    @Expose
    private String address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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