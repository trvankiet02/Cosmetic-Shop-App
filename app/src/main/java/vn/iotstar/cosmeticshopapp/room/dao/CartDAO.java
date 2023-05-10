package vn.iotstar.cosmeticshopapp.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import vn.iotstar.cosmeticshopapp.room.entity.CartEntity;
import vn.iotstar.cosmeticshopapp.room.entity.CartItemEntity;

@Dao
public interface CartDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCart(CartEntity cartEntity);

    @Query("SELECT * FROM cart")
    List<CartEntity> getAllCart();


}
