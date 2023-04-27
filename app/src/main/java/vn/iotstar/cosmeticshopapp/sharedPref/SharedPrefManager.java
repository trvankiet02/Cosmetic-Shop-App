package vn.iotstar.cosmeticshopapp.sharedPref;

import android.content.Context;
import android.content.SharedPreferences;

import vn.iotstar.cosmeticshopapp.model.User;

public class SharedPrefManager {
    private static String SHARED_PREF_NAME = "UserLoggedInDetail";
    private static String KEY_USER_ID = "userid";
    private static String KEY_USER_NAME = "username";
    private static String KEY_FNAME = "fname";
    private static String KEY_EMAIL = "email";
    private static String KEY_GENDER = "gender";
    private static String KEY_IMAGES = "images";
    Context context;
    public SharedPrefManager(Context ctx){
        this.context = ctx;
    }
    public void saveUserLoggedInDetail(User userSQLite){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt(KEY_USER_ID, userSQLite.getId());
//        editor.putString(KEY_USER_NAME, userSQLite.getUsername());
//        editor.putString(KEY_FNAME, userSQLite.getFname());
//        editor.putString(KEY_EMAIL, userSQLite.getEmail());
//        editor.putString(KEY_GENDER, userSQLite.getGender());
//        editor.putString(KEY_IMAGES, userSQLite.getImages());
        editor.commit();
    }
    public void clearUserLoggedInDetail(){
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();
    }

//    public User getUserLoggedInDetail(){
//
//        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        User userSQLite = new User(sharedPreferences.getInt(KEY_USER_ID, -1),
//                sharedPreferences.getString(KEY_USER_NAME, null),
//                sharedPreferences.getString(KEY_FNAME, null),
//                sharedPreferences.getString(KEY_EMAIL, null),
//                sharedPreferences.getString(KEY_GENDER, null),
//                sharedPreferences.getString(KEY_IMAGES, null));
//        return userSQLite;
//    }
    public void updateUserLoggedInDetail(String key, String data){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, data);
        editor.apply();
        editor.commit();
    }
}
