package vn.iotstar.cosmeticshopapp.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import vn.iotstar.cosmeticshopapp.room.dao.CartDAO;
import vn.iotstar.cosmeticshopapp.room.dao.CartItemDAO;
import vn.iotstar.cosmeticshopapp.room.entity.CartItemEntity;

@Database(entities = {CartItemEntity.class}, version = 1)
public abstract class CartItemDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "cartItem.db.Test4";
    public static CartItemDatabase instance;
    public static synchronized CartItemDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), CartItemDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract CartItemDAO cartItemDao();
}
