package vn.iotstar.cosmeticshopapp.model;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private String price;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private Integer promotionalPrice;
    private String description;
    private String madeOf;
    private String color;
    private String madeIn;
    private Style style;
    private Category category;
    //private Store store;
    private Boolean isSelling;

    public Product(int id, String name, String price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
