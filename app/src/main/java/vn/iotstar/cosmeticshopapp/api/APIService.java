package vn.iotstar.cosmeticshopapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.iotstar.cosmeticshopapp.model.User;

public interface APIService {
    Gson gson = new GsonBuilder().setDateFormat("yyyy MM dd HH:mm:ss").create();
    @POST("registrationapi.php?apicall=login")
    @FormUrlEncoded
    Call<User> login(@Field("username") String username, @Field("password") String password);




}

//    @POST("registrationapi.php?apicall=login")
//    @FormUrlEncoded
//    Call<LoginResponse> login(@Field("username") String username, @Field("password") String password);
//
//    @GET("categories.php")
//    Call<List<Category>> getAllCaterory();
//
//    @GET("lastproduct.php")
//    Call<List<Product>> getAllLastProduct();
//
//    @POST("updatefullname.php")
//    @FormUrlEncoded
//    Call<UpdateResponse> updateFName(@Field("id") Integer id,
//                                     @Field("fname") String fname);
//    @Multipart
//    @POST("updateimages.php")
//    Call<UpdateResponse> uploadAvatar(@Part MultipartBody.Part images,
//                                      @Part("id") RequestBody id);
//    @FormUrlEncoded
//    @POST("newmealdetail.php")
//    Call<Mess> getProductDetail(@Field("id") String id);
//    @POST("getcategory.php")
//    @FormUrlEncoded
//    Call<List<Product>> getProductByCategoryId(@Field("idcategory") int idcategory);
