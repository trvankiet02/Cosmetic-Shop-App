package vn.iotstar.cosmeticshopapp.model;

import java.util.Date;

public class CartItem {
    private Integer id;
    private Cart cart;
    private Product product;
    private String size;
    private Integer quantity;
    private Boolean isPayed;
    private Date createAt;
    private Date updateAt;
    private OrderItem orderItem;
}
