package vn.iotstar.cosmeticshopapp.api;
import androidx.core.content.pm.PermissionInfoCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.iotstar.cosmeticshopapp.model.AddToCartResponse;
import vn.iotstar.cosmeticshopapp.model.Address;
import vn.iotstar.cosmeticshopapp.model.AddressResponse;
import vn.iotstar.cosmeticshopapp.model.CartItem;
import vn.iotstar.cosmeticshopapp.model.CartResponse;
import vn.iotstar.cosmeticshopapp.model.Category;
import vn.iotstar.cosmeticshopapp.model.CategoryAndStyleResponse;
import vn.iotstar.cosmeticshopapp.model.FollowProductResponse;
import vn.iotstar.cosmeticshopapp.model.ListAddressResponse;
import vn.iotstar.cosmeticshopapp.model.LoginSignupResponse;
import vn.iotstar.cosmeticshopapp.model.OrderResponse;
import vn.iotstar.cosmeticshopapp.model.ProductDetailResponse;
import vn.iotstar.cosmeticshopapp.model.ProductResponse;
import vn.iotstar.cosmeticshopapp.model.ReviewResponse;
import vn.iotstar.cosmeticshopapp.model.StyleByCategoryResponse;

public interface APIService {

    @POST("login")
    @FormUrlEncoded
    Call<LoginSignupResponse> login(@Field("email") String email, @Field("password") String password);

    @POST("signup")
    @FormUrlEncoded
    Call<LoginSignupResponse> signup(
            @Field("email") String email,
            @Field("password") String password,
            @Field("rePassword") String rePassword
    );
    @GET("category")
    Call<CategoryAndStyleResponse> getCategory();

    @POST("category/getCategoryByStyle")
    @FormUrlEncoded
    Call<CategoryAndStyleResponse> getCategoryByStyle(@Field("styleId") Integer styleId);

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

    @POST("style/getStyleSelling")
    @FormUrlEncoded
    Call<StyleByCategoryResponse> getStyleSelling(@Field("styleId") Integer styleId);

    @POST("product/getProductByStyle")
    @FormUrlEncoded
    Call<ProductResponse> getProductByStyle(@Field("styleId") Integer styleId, @Field("isSelling") Boolean isSelling);

    @PUT("cartItem/updateCartItem")
    Call<AddToCartResponse> updateCartItem(@Body CartItem cartItem, @Query("cartId") Integer cartId);

    @DELETE("cartItem/deleteCartItem")
    Call<AddToCartResponse> deleteCartItem(@Query("cartItemId") int cartItemId);

    @GET("address/user/{id}")
    Call<ListAddressResponse> getAddressByUserId(@Path("id") Integer id);

    @DELETE("address/{id}")
    Call<Void> deleteAddress(@Path("id") Integer id);

    @GET("address/{id}")
    Call<AddressResponse> getAddress(@Path("id") Integer id);

    @PUT("address/addOrUpdate")
    Call<AddressResponse> addOrUpdateAddress(@Body Address address, @Query("userId") Integer userId);

    @POST("order/getOrder")
    Call<OrderResponse> getOrder(@Query("userId") Integer userId, @Query("status") Integer status);

    @POST("user/updateAvatar")
    @Multipart
    Call<LoginSignupResponse> updateAvatar(@Part("userId") RequestBody userId, @Part MultipartBody.Part image);

    @POST("user/updatePhone")
    @FormUrlEncoded
    Call<LoginSignupResponse> updatePhone(@Field("userId") Integer userId, @Field("phone") String phone);

    @POST("user/updatePassword")
    @FormUrlEncoded
    Call<LoginSignupResponse> updatePassword(@Field("userId") Integer userId, @Field("password") String password);

    @POST("user/updateName")
    @FormUrlEncoded
    Call<LoginSignupResponse> updateName(@Field("userId") Integer userId, @Field("firstName") String firstName, @Field("lastName") String lastName);



}

