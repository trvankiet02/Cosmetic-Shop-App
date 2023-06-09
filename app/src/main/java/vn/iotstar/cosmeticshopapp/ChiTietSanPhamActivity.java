package vn.iotstar.cosmeticshopapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderView;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.adapter.FeedbackAdapter;
import vn.iotstar.cosmeticshopapp.adapter.ProductHomeAdapter;
import vn.iotstar.cosmeticshopapp.adapter.SliderProductImageAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.fragment_login_signup.LoginFragment;
import vn.iotstar.cosmeticshopapp.model.AddToCartResponse;
import vn.iotstar.cosmeticshopapp.model.FollowProductResponse;
import vn.iotstar.cosmeticshopapp.model.Order;
import vn.iotstar.cosmeticshopapp.model.OrderItem;
import vn.iotstar.cosmeticshopapp.model.OrderResponse;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.model.ProductDetailResponse;
import vn.iotstar.cosmeticshopapp.model.ProductQuantity;
import vn.iotstar.cosmeticshopapp.model.ProductResponse;
import vn.iotstar.cosmeticshopapp.model.Review;
import vn.iotstar.cosmeticshopapp.model.ReviewResponse;
import vn.iotstar.cosmeticshopapp.model.Store;
import vn.iotstar.cosmeticshopapp.model.UserFollowProduct;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.room.dao.CartDAO;
import vn.iotstar.cosmeticshopapp.room.dao.CartItemDAO;
import vn.iotstar.cosmeticshopapp.room.database.CartDatabase;
import vn.iotstar.cosmeticshopapp.room.database.CartItemDatabase;
import vn.iotstar.cosmeticshopapp.room.entity.CartEntity;
import vn.iotstar.cosmeticshopapp.room.entity.CartItemEntity;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;
import vn.iotstar.cosmeticshopapp.util.AnimationUtil;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 123;
    public static final String TAG = "ChiTietSanPhamActivity";

    ImageView viewAnimation, img_shopimage;
    TextView tv_name_of_shop, tv_start_of_shop;
    ImageButton imageButtonTru, imageButtonCong;
    SearchView searchView;
    ImageView GioHang, imgFavorite;
    LinearLayout lnReview, lnXemThem, lnXemTatCa, ln_shop;
    TextView txtSize, tv_name_product, tvPriceSale, tvPrice, tvPrice_d, txtsoluong, tv_rate_sanpham_tren, tvAddress, tv_rate_sanpham_duoi;
    TextView count_product, tv_rate_num;
    RatingBar rate_sanpham_tren, rate_sanpham_duoi;
    ProgressBar progressBar_nho, progressBar_vua, progressBar_lon;
    TextView tv_nho, tv_vua, tv_lon;

    RecyclerView rvFeedback;
    Spinner sizeSpinner;
    RecyclerView rvProducGoiY;
    List<Product> products;
    ProductHomeAdapter productHomeAdapter;
    Product product;
    APIService apiService;
    List<Review> feedbacks;
    FeedbackAdapter feedbackAdapter;
    SliderView sliderView;
    SliderProductImageAdapter sliderProductImageAdapter;
    TextView addToCart;
    ImageView img_icon_bag, imgLike, img_back;
    SharedPrefManager sharedPrefManager;
    ProgressDialog progressDialog;
    boolean isLiked;
    DecimalFormat df = new DecimalFormat("#.#");
    int quantityMaxOfSize;

    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);
        AnhXa();
        setRvProductGoiY();
        getProductId();
        getProductDetail();
        setSoldProduct();
        setFollowProduct();
        setShopInfo();
        addToCart();
        set2Feedback();
        setOnClick();
        setBtnBack();
        GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gioHang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(gioHang);
            }
        });
    }

    private void setShopInfo() {
        tv_name_of_shop.setText(String.valueOf(product.getStore().getName()));
        tv_start_of_shop.setText(String.valueOf(Math.round(product.getStore().getRating()*10.0) /10.0));
        Glide.with(getApplicationContext()).load(product.getStore().getStoreImage()).into(img_shopimage);
        ln_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietSanPhamActivity.this, StoreActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("store", product.getStore());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void setOnClick() {
        lnXemThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feedback = new Intent(ChiTietSanPhamActivity.this, ReviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", product);
                feedback.putExtras(bundle);
                startActivity(feedback);
            }
        });
        lnXemTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feedback = new Intent(ChiTietSanPhamActivity.this, ReviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", product);
                feedback.putExtras(bundle);
                startActivity(feedback);
            }
        });
        imageButtonTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(count_product.getText().toString());
                if (count > 1) {
                    count--;
                    count_product.setText(String.valueOf(count));
                }else{
                    Toast.makeText(ChiTietSanPhamActivity.this, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageButtonCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(count_product.getText().toString());
                if (count < quantityMaxOfSize) {
                    count++;
                    count_product.setText(String.valueOf(count));
                }else{
                    Toast.makeText(ChiTietSanPhamActivity.this, "Số lượng phải nhỏ hơn " + quantityMaxOfSize, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void AnhXa() {
        //linearlayout shop của sản phẩm
        img_back = findViewById(R.id.img_back);
        ln_shop = findViewById(R.id.ln_shop);
        img_shopimage = findViewById(R.id.img_shopimage);
        tv_name_of_shop = findViewById(R.id.tv_name_of_shop);
        tv_start_of_shop = findViewById(R.id.tv_start_of_shop);
        //       searchView = (SearchView) findViewById(R.id.search_view);
        sizeSpinner = (Spinner) findViewById(R.id.size_spinner);
        txtSize = findViewById(R.id.txtsize);
        count_product = findViewById(R.id.count_product);
        imageButtonTru = findViewById(R.id.imageButtonTru);
        imageButtonCong = findViewById(R.id.imageButtonCong);
        rvProducGoiY = (RecyclerView) findViewById(R.id.rvProduct);
        rvFeedback = (RecyclerView) findViewById(R.id.rvFeedback);
        GioHang = (ImageView) findViewById(R.id.img_icon_bag);
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        sliderView = findViewById(R.id.slider);
        viewAnimation = findViewById(R.id.viewAnimation);
        addToCart = findViewById(R.id.addToCart);
        img_icon_bag = findViewById(R.id.img_icon_bag);

        tvPriceSale = (TextView) findViewById(R.id.tv_price_sale);
        tv_name_product = (TextView) findViewById(R.id.tv_name_product);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvPrice_d = (TextView) findViewById(R.id.tv_d);
        txtsoluong = (TextView) findViewById(R.id.txtsoluong);
        tv_rate_sanpham_tren = (TextView) findViewById(R.id.tv_rating);
//        tvAddress = (TextView) findViewById(R.id.tv_address);
        tv_rate_sanpham_duoi = (TextView) findViewById(R.id.tv_rate1);
        tv_rate_num = (TextView) findViewById(R.id.tv_rate_num);
        lnXemThem = (LinearLayout) findViewById(R.id.ln_xemthem);
        lnXemTatCa = (LinearLayout) findViewById(R.id.lnXemTatCa);
        lnReview = (LinearLayout) findViewById(R.id.lnReview);
//        tv_themVaoGio = (TextView) findViewById(R.id.tv_them_vao_gio);
//
        rate_sanpham_tren = (RatingBar) findViewById(R.id.rate_sanpham_tren);
        rate_sanpham_duoi = (RatingBar) findViewById(R.id.rate_sanpham_duoi);
//
//
        tv_nho = (TextView) findViewById(R.id.tv_progress_bar_nho);
        tv_vua = (TextView) findViewById(R.id.tv_progress_bar_vua);
        tv_lon = (TextView) findViewById(R.id.tv_progress_bar_lon);
        progressBar_nho = (ProgressBar) findViewById(R.id.progress_bar_nho);
        progressBar_vua = (ProgressBar) findViewById(R.id.progress_bar_vua);
        progressBar_lon = (ProgressBar) findViewById(R.id.progress_bar_lon);

        imgLike = findViewById(R.id.img_icon_favorite);

        sharedPrefManager = new SharedPrefManager(this);

        progressDialog = new ProgressDialog(this);
        setProgressDialog();


    }
    private void setBtnBack(){
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setProgressDialog() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Vui lòng đợi");
        progressDialog.setMessage("Đang xử lý yêu cầu...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
    }

    private void setFollowProduct(){
        apiService.getFollowProduct(sharedPrefManager.getUser().getId()).enqueue(new Callback<FollowProductResponse>() {
            @Override
            public void onResponse(Call<FollowProductResponse> call, Response<FollowProductResponse> response) {
                imgLike.setBackgroundResource(R.drawable.icon_favourite);
                isLiked = false;
                if (response.isSuccessful()){
                    for (UserFollowProduct userFollowProduct : response.body().getBody()){
                        if (userFollowProduct.getProduct().getId().equals(product.getId())){
                            imgLike.setBackgroundResource(R.drawable.icon_favourited);
                            isLiked = true;
                        } else {
                            imgLike.setBackgroundResource(R.drawable.icon_favourite);
                            isLiked = false;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<FollowProductResponse> call, Throwable t) {

            }
        });
        imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPrefManager.getUser().getId() == -1){
                    Intent intent = new Intent(ChiTietSanPhamActivity.this, LoginSignupActivity.class);
                    startActivity(intent);
                    Toast.makeText(ChiTietSanPhamActivity.this, "Vui lòng đăng nhập để thực hiện chức năng này", Toast.LENGTH_SHORT).show();
                }
                progressDialog.show();
                if (isLiked) {
                    apiService.followOrUnfollow(sharedPrefManager.getUser().getId(), product.getId()).enqueue(new Callback<FollowProductResponse>() {
                        @Override
                        public void onResponse(Call<FollowProductResponse> call, Response<FollowProductResponse> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()) {
                                imgLike.setBackgroundResource(R.drawable.icon_favourite);
                                isLiked = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<FollowProductResponse> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });
                } else {
                    apiService.followOrUnfollow(sharedPrefManager.getUser().getId(), product.getId()).enqueue(new Callback<FollowProductResponse>() {
                        @Override
                        public void onResponse(Call<FollowProductResponse> call, Response<FollowProductResponse> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()) {
                                imgLike.setBackgroundResource(R.drawable.icon_favourited);
                                isLiked = true;
                            }
                        }
                        @Override
                        public void onFailure(Call<FollowProductResponse> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void getProductId() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        product = (Product) bundle.getSerializable("product");
        Log.d("TAG", "getProductFromAdapter: " + product.getId());
    }

    private void addToCart(){
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                if (sharedPrefManager.getUser().getId() == -1){
                    //addToCartDatabase(sharedPrefManager.getUser().getId(), product);
                    Toast.makeText(ChiTietSanPhamActivity.this, "Vui lòng đăng nhập để thực hiện hành động này", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(ChiTietSanPhamActivity.this, LoginSignupActivity.class);
                    progressDialog.dismiss();
                    startActivityForResult(loginIntent, REQUEST_CODE);
                } else {
                    apiService.addToCart(sharedPrefManager.getUser().getId(), product.getId(), sizeSpinner.getSelectedItem().toString(), Integer.parseInt(count_product.getText().toString())).enqueue(new Callback<AddToCartResponse>() {
                        @Override
                        public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(ChiTietSanPhamActivity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else {
                                if (response.code() == 400){
                                    Toast.makeText(ChiTietSanPhamActivity.this, "Số lượng thêm đã vượt quá số lượng sản phẩm hiện có", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }

                            }
                        }
                        @Override
                        public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                            Log.e(TAG, "onFailure: " + t.getMessage() );
                        }
                    });
                }
            }
        });
    }

    private void getProductDetail(){
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        apiService.getProductDetail(product.getId()).enqueue(new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                sliderProductImageAdapter = new SliderProductImageAdapter(getApplicationContext(), response.body().getBody().getProductImages(), REQUEST_CODE);
                sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                Glide.with(getApplicationContext()).load(response.body().getBody().getProductImages().get(0).getImage()).into(viewAnimation);
                sliderView.setSliderAdapter(sliderProductImageAdapter);
                tv_rate_sanpham_tren.setText(String.valueOf(response.body().getBody().getSold()));

                int promotionalPrice = response.body().getBody().getPromotionalPrice();
                int price = response.body().getBody().getPrice();
                if(price <= promotionalPrice){
                    //k km
                    tvPrice.setVisibility(View.GONE);
                    tvPrice_d.setVisibility(View.GONE);
                    TextView tv_d0 = (TextView) findViewById(R.id.tv_d0);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                    tv_d0.setLayoutParams(params);
                }
                else {
                    //có km
                    String text1 = formatter.format(response.body().getBody().getPrice());
                    String text2 = tvPrice_d.getText().toString();

                    SpannableString spannableString1 = new SpannableString(text1);
                    spannableString1.setSpan(new StrikethroughSpan(), 0, text1.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE );

                    SpannableString spannableString2 = new SpannableString(text2);
                    spannableString2.setSpan(new StrikethroughSpan(), 0, text2.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

                    tvPrice.setText(spannableString1);
                    tvPrice_d.setText(spannableString2);
                }
                String formattedNumber = formatter.format(promotionalPrice);
                tvPriceSale.setText(formattedNumber);
                tv_name_product.setText(response.body().getBody().getName());

                SetSpinner(response.body().getBody());

            }

            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {
                Toast.makeText(ChiTietSanPhamActivity.this, "lỗi không load được product", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void SetSpinner(Product product) {
        List<String> sizes = new ArrayList<>();
        List<Integer> soluong = new ArrayList<>();
        for (ProductQuantity productQuantity: product.getProductQuantities()) {
            sizes.add(productQuantity.getSize().toString());
            soluong.add(productQuantity.getQuantity());
        }

        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(ChiTietSanPhamActivity.this, android.R.layout.simple_spinner_dropdown_item, sizes);

        sizeSpinner.setAdapter(sizeAdapter);
        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = parent.getItemAtPosition(position).toString();
                txtSize.setText(selectedOption);
                txtsoluong.setText(soluong.get(position).toString());
                quantityMaxOfSize = soluong.get(position);
                count_product.setText(String.valueOf(1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setRvProductGoiY(){
        apiService.getRandomProduct(6).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()){
                    products = response.body().getBody();
                    if (products == null){
                        Log.e("TAG", "onResponse: " + "NULL" );
                    }
                    productHomeAdapter = new ProductHomeAdapter(ChiTietSanPhamActivity.this, products);
                    rvProducGoiY.setHasFixedSize(true);
                    GridLayoutManager layoutManager = new GridLayoutManager(ChiTietSanPhamActivity.this, 2, GridLayoutManager.VERTICAL, false);

                    rvProducGoiY.setLayoutManager(layoutManager);
                    rvProducGoiY.setAdapter(productHomeAdapter);
                    productHomeAdapter.notifyDataSetChanged();
                } else {
                    Log.e("TAG", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });
    }
    private void setSoldProduct(){
        apiService.getAllOrder().enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()){
                    int soldProduct = 0;
                    List<Order> orders = response.body().getBody();
                    for (Order order : orders){
                        List<OrderItem> orderItems = order.getOrderItems();

                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {

            }
        });
    }
    private float avgRating(List<Review> reviews){
        float avg = 0;
        if (reviews.size() == 0){
            return 0.0f;
        }
        for (Review review : reviews){
            avg += review.getRating();
        }
        return avg/reviews.size();
    }
    private void setRatingTable(List<Review> reviewList, ProgressBar nho, ProgressBar vua, ProgressBar lon,
                                TextView tvNho, TextView tvVua, TextView tvLon){
        int nhoCount = 0;
        int vuaCount = 0;
        int lonCount = 0;
        for (Review review : reviewList){
            if (review.getRating() <= 2){
                nhoCount++;
            }
            else if (review.getRating() == 5){
                lonCount++;
            }
            else {
                vuaCount++;
            }
        }
        nho.setProgress(nhoCount*100/reviewList.size());
        tvNho.setText(String.valueOf(nhoCount*100/reviewList.size()));
        vua.setProgress(vuaCount*100/reviewList.size());
        tvVua.setText(String.valueOf(vuaCount*100/reviewList.size()));
        lon.setProgress(lonCount*100/reviewList.size());
        tvLon.setText(String.valueOf(lonCount*100/reviewList.size()));
    }
    private void set2Feedback(){
        apiService.getReview(product.getId()).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful()){
                    feedbacks = response.body().getBody();
                    Log.e(TAG, "onResponse: " + feedbacks.size());
                    if(feedbacks.size() == 0){
                        lnReview.setVisibility(View.GONE);
                    }
                    else {
                        setRatingTable(feedbacks, progressBar_nho, progressBar_vua, progressBar_lon,
                                tv_nho, tv_vua, tv_lon);
                        tv_rate_num.setText(df.format(avgRating(feedbacks)));
                        tv_rate_sanpham_duoi.setText(String.valueOf(feedbacks.size()));
                        rate_sanpham_duoi.setRating(avgRating(feedbacks));
                        rate_sanpham_tren.setRating(avgRating(feedbacks));

                        List<Review> twoFeedbacks = new ArrayList<>();
                        if (feedbacks.size() >= 2) {
                            twoFeedbacks = new ArrayList<>(feedbacks.subList(0, 2));
                        } else {
                            twoFeedbacks.addAll(feedbacks);
                        }

                        feedbackAdapter = new FeedbackAdapter(getApplicationContext(), twoFeedbacks);
                        rvFeedback.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                        rvFeedback.setLayoutManager(layoutManager);
                        rvFeedback.setAdapter(feedbackAdapter);
                        feedbackAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {

            }
        });

    }
}