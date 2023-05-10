package vn.iotstar.cosmeticshopapp.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import vn.iotstar.cosmeticshopapp.room.dao.CartDAO;
import vn.iotstar.cosmeticshopapp.room.entity.CartEntity;

@Database(entities = {CartEntity.class}, version = 1)
public abstract class CartDatabase extends RoomDatabase{
    private static final String DATABASE_NAME = "cart.db.Test4";
    public static CartDatabase instance;
    public static synchronized CartDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), CartDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract CartDAO cartDao();
}
