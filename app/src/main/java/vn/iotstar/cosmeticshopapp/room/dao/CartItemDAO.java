package vn.iotstar.cosmeticshopapp.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import vn.iotstar.cosmeticshopapp.room.entity.CartEntity;
import vn.iotstar.cosmeticshopapp.room.entity.CartItemEntity;

@Dao
public interface CartItemDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCartItem(CartItemEntity cartItem);

    @Query("SELECT * FROM cartItem WHERE productId = :productId AND size = :size LIMIT 1")
    CartItemEntity getCartItemByProductIdAndSize(int productId, String size);

    @Update
    void updateCartItem(CartItemEntity cartItem);

    @Query("SELECT * FROM cartItem")
    List<CartItemEntity> getAllCartItem();

    @Query("UPDATE cartItem SET quantity = :newQuantity WHERE id = :id")
    void updateQuantityByCartItemId(int id, int newQuantity);
}
