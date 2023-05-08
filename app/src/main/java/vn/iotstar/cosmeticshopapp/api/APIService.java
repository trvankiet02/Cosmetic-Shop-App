package vn.iotstar.cosmeticshopapp.api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import vn.iotstar.cosmeticshopapp.model.CategoryAndStyleResponse;
import vn.iotstar.cosmeticshopapp.model.LoginSignupResponse;
import vn.iotstar.cosmeticshopapp.model.ProductResponse;

public interface APIService {

    @POST("login")
    @FormUrlEncoded
    Call<LoginSignupResponse> login(@Field("email") String email, @Field("password") String password);

    @GET("category")
    Call<CategoryAndStyleResponse> getCategory();

    @POST("product/getRandomProduct")
    @FormUrlEncoded
    Call<ProductResponse> getRandomProduct(@Field("quantity") Integer quantity);

    @GET("product")
    Call<ProductResponse> getAllProduct();
}

