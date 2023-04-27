package vn.iotstar.cosmeticshopapp.model;

import java.util.Date;

public class OrderItem {
    private Integer id;
    private Order order;
    private CartItem cartItem;
    private Integer unitPrice;
    private Date createAt;
    private Date updateAt;
}
