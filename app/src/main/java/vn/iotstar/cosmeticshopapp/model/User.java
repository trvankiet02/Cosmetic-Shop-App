package vn.iotstar.cosmeticshopapp.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private int id;
//    private String styleName;
//    private String styleImage;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String profileImage;
    private Boolean role;
    private Integer eWallet;
    private Date createAt;
    private Date updateAt;
    private Date lastLogin;
//    private List<Address> addresses;
//    private Store store;
//    private List<UserFollowStore> userFollowStores;
//    private List<UserFollowProduct> userFollowProducts;
//    private List<Cart> carts;
//    private List<Order> orders;

}
