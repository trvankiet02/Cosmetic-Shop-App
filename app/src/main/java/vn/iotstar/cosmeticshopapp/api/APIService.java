package vn.iotstar.cosmeticshopapp.api;
import androidx.core.content.pm.PermissionInfoCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import vn.iotstar.cosmeticshopapp.model.AddToCartResponse;
import vn.iotstar.cosmeticshopapp.model.CartResponse;
import vn.iotstar.cosmeticshopapp.model.CategoryAndStyleResponse;
import vn.iotstar.cosmeticshopapp.model.FollowProductResponse;
import vn.iotstar.cosmeticshopapp.model.LoginSignupResponse;
import vn.iotstar.cosmeticshopapp.model.OrderResponse;
import vn.iotstar.cosmeticshopapp.model.ProductDetailResponse;
import vn.iotstar.cosmeticshopapp.model.ProductResponse;
import vn.iotstar.cosmeticshopapp.model.ReviewResponse;

public interface APIService {

    @POST("login")
    @FormUrlEncoded
    Call<LoginSignupResponse> login(@Field("email") String email, @Field("password") String password);

    @GET("category")
    Call<CategoryAndStyleResponse> getCategory();
    @GET("style")
    Call<CategoryAndStyleResponse> getStyle();

    @POST("product/getRandomProduct")
    @FormUrlEncoded
    Call<ProductResponse> getRandomProduct(@Field("quantity") Integer quantity);

    @GET("product")
    Call<ProductResponse> getAllProduct();

    @POST("product/getProduct")
    @FormUrlEncoded
    Call<ProductDetailResponse> getProductDetail(@Field("id") Integer id);

    @POST("review/getReview")
    @FormUrlEncoded
    Call<ReviewResponse> getReview(@Field("productId") Integer productId);

    @GET("order")
    Call<OrderResponse> getAllOrder();

    @POST("userFollowProduct/getFollowProduct")
    @FormUrlEncoded
    Call<FollowProductResponse> getFollowProduct(@Field("userId") Integer userId);

    @POST("userFollowProduct/followOrUnfollow")
    @FormUrlEncoded
    Call<FollowProductResponse> followOrUnfollow(@Field("userId") Integer userId, @Field("productId") Integer productId);

    @POST("cartItem/addCartItem")
    @FormUrlEncoded
    Call<AddToCartResponse> addToCart(@Field("userId") Integer userId, @Field("productId") Integer productId,
                                      @Field("size") String size, @Field("quantity") Integer quantity);

    @POST("cart/getCart")
    @FormUrlEncoded
    Call<CartResponse> getCart(@Field("userId") Integer userId);
}

