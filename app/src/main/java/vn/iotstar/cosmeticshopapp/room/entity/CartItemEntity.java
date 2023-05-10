package vn.iotstar.cosmeticshopapp.room.entity;

import android.annotation.SuppressLint;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.material.timepicker.TimeFormat;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "cartItem")
public class CartItemEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Integer cartId;
    private Integer productId;
    private String size;
    private Integer quantity;
    private String createAt;
    private String updateAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public CartItemEntity(Integer cartId, Integer productId, String size, Integer quantity, String createAt, String updateAt) {
        this.cartId = cartId;
        this.productId = productId;
        this.size = size;
        this.quantity = quantity;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
