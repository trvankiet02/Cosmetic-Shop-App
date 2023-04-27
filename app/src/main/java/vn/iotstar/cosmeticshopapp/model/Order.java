package vn.iotstar.cosmeticshopapp.model;

import java.util.Date;

public class Order {
    private Integer id;
    private User user;
    private Delivery delivery;
//    private Store store;
    private String address;
    private Integer price;
    private String phone;
    private Integer status;
    private Date createAt;
    private Date updateAt;
//    private List<OrderItem> orderItems;
//    private Review review;
}
