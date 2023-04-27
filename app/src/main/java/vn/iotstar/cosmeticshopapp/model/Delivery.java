package vn.iotstar.cosmeticshopapp.model;

import java.util.Date;
import java.util.List;

public class Delivery {
    private Integer id;
    private String deliveryName;
    private String deliveryImage;
    private String description;
    private Date createAt;
    private Date updateAt;
    private List<Order> orders;
}
