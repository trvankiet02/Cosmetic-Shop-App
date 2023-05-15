package vn.iotstar.cosmeticshopapp.sharedPreferentManager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import vn.iotstar.cosmeticshopapp.model.Address;
import vn.iotstar.cosmeticshopapp.model.Store;
import vn.iotstar.cosmeticshopapp.model.User;

public class SharedPrefManager {
    private static String SHARED_PREF_NAME = "user";
    private static String KEY_USER_ID = "id";
    private static String KEY_USER_EMAIL = "email";
    private static String KEY_USER_PASSWORD = "password";
    private static String KEY_USER_FIRST_NAME = "firstName";
    private static String KEY_USER_LAST_NAME = "lastName";
    private static String KEY_USER_PHONE = "phone";
    private static String KEY_USER_PROFILE_IMAGE = "profileImage";
    private static String KEY_USER_ROLE = "role";
    private static String KEY_USER_CREATE_AT = "createAt";
    private static String KEY_USER_UPDATE_AT = "updateAt";
    private static String KEY_USER_LAST_LOGIN = "lastLogin";
    private static String KEY_USER_ADDRESSES = "addresses";
    private static String KEY_USER_EWALLET = "ewallet";

    //store
    private static String KEY_STORE_ID = "storeId";
    Context context;

    public SharedPrefManager(Context ctx) {
        this.context = ctx;
    }
    public void setPassword(String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_PASSWORD, password);
        editor.commit();
    }
    public void updateEwallet(Integer ewallet) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_EWALLET, ewallet);
        editor.commit();
    }
    public void setStoreId(Integer id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_STORE_ID, id);
        editor.commit();
    }

    public void setUser(User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putString(KEY_USER_FIRST_NAME, user.getFirstName());
        editor.putString(KEY_USER_LAST_NAME, user.getLastName());
        editor.putString(KEY_USER_PHONE, user.getPhone());
        editor.putString(KEY_USER_PROFILE_IMAGE, user.getProfileImage());
        editor.putInt(KEY_USER_ROLE, user.getRole());
        editor.putString(KEY_USER_CREATE_AT, user.getCreateAt());
        editor.putString(KEY_USER_UPDATE_AT, user.getUpdateAt());
        editor.putString(KEY_USER_LAST_LOGIN, user.getLastLogin());
        editor.putString(KEY_USER_ADDRESSES, toAddressesJson(user.getAddresses()));
        editor.putInt(KEY_USER_EWALLET, user.getEwallet());
        editor.commit();
    }


    public void deletePassword() {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(KEY_USER_PASSWORD);
        editor.apply();
    }
    public void deleteStoreId() {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(KEY_STORE_ID);
        editor.apply();
    }


    public void deleteUser() {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();
    }
    public void deleteAddress(Address address) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        User user = getUser();
        for (Address add: user.getAddresses()) {
            if (add.getId() == address.getId()) {
                user.getAddresses().remove(add);
                break;
            }
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ADDRESSES, toAddressesJson(user.getAddresses()));
        editor.commit();
    }
    public void addAddress(Address address){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        User user = getUser();
        user.getAddresses().add(address);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ADDRESSES, toAddressesJson(user.getAddresses()));
        editor.commit();
    }

    public User getUser() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        User user = new User(sharedPreferences.getInt(KEY_USER_ID, -1),
                sharedPreferences.getString(KEY_USER_EMAIL, null),
                sharedPreferences.getString(KEY_USER_FIRST_NAME, null),
                sharedPreferences.getString(KEY_USER_LAST_NAME, null),
                sharedPreferences.getString(KEY_USER_PHONE, null),
                sharedPreferences.getString(KEY_USER_PROFILE_IMAGE, null),
                sharedPreferences.getInt(KEY_USER_ROLE, -1),
                sharedPreferences.getString(KEY_USER_CREATE_AT, null),
                sharedPreferences.getString(KEY_USER_UPDATE_AT, null),
                sharedPreferences.getString(KEY_USER_LAST_LOGIN, null),
                toAddressesList(sharedPreferences.getString(KEY_USER_ADDRESSES, null)),
                sharedPreferences.getInt(KEY_USER_EWALLET, -1));
        return user;
    }
    public String getPassword() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String password = sharedPreferences.getString(KEY_USER_PASSWORD, null);
        return password;
    }
    public Integer getStoreId(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Integer storeId = sharedPreferences.getInt(KEY_STORE_ID, -1);
        return storeId;
    }


    public void updateUser(String key, String data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, data);
        editor.apply();
        editor.commit();
    }

    private String toAddressesJson(List<Address> addresses) {
        Gson gson = new Gson();
        String json = gson.toJson(addresses);
        return json;
    }

    private List<Address> toAddressesList(String json) {
        Gson gson = new Gson();
        Type addressListType = new TypeToken<List<Address>>() {
        }.getType();
        List<Address> addresses = gson.fromJson(json, addressListType);
        return addresses;
    }
}
