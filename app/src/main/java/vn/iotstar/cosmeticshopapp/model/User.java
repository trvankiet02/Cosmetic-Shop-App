package vn.iotstar.cosmeticshopapp.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.iotstar.cosmeticshopapp.model.Address;

public class User implements Serializable
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("profileImage")
    @Expose
    private String profileImage;
    @SerializedName("role")
    @Expose
    private Integer role;
    @SerializedName("createAt")
    @Expose
    private String createAt;
    @SerializedName("updateAt")
    @Expose
    private String updateAt;
    @SerializedName("lastLogin")
    @Expose
    private String lastLogin;
    @SerializedName("addresses")
    @Expose
    private List<Address> addresses;
    @SerializedName("ewallet")
    @Expose
    private Integer ewallet;
    private final static long serialVersionUID = -5080609665973113173L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
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

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Integer getEwallet() {
        return ewallet;
    }

    public void setEwallet(Integer ewallet) {
        this.ewallet = ewallet;
    }

    public User() {
    }

    public User(Integer id, String email, String firstName,
                String lastName, String phone, String profileImage,
                Integer role, String createAt, String updateAt,
                String lastLogin, List<Address> addresses, Integer ewallet) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.profileImage = profileImage;
        this.role = role;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.lastLogin = lastLogin;
        this.addresses = addresses;
        this.ewallet = ewallet;
    }
}