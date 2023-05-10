package vn.iotstar.cosmeticshopapp.room.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cart",
indices = {@Index(value = "storeId", unique = true)})
public class CartEntity implements Serializable {
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Integer userId;
    private Integer storeId;
    private String createAt;
    private String updateAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
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

    public CartEntity(Integer userId, Integer storeId, String createAt, String updateAt) {
        this.userId = userId;
        this.storeId = storeId;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
