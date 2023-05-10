package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.adapter.FeedbackAdapter;
import vn.iotstar.cosmeticshopapp.adapter.ProductHomeAdapter;
import vn.iotstar.cosmeticshopapp.adapter.SliderAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
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
import vn.iotstar.cosmeticshopapp.model.UserFollowProduct;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPref.SharedPrefManager;
import vn.iotstar.cosmeticshopapp.util.AnimationUtil;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 123;
    ImageView viewAnimation;
    SearchView searchView;
    ImageView GioHang, imgFavorite;
    LinearLayout lnReview, lnXemThem, lnXemTatCa;
    TextView txtSize, tv_name_product, tvPriceSale, tvPrice, tvPrice_d, txtsoluong, tv_rate_sanpham_tren, tvAddress, tv_rate_sanpham_duoi;
    TextView tv_rate_num, tv_themVaoGio;
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
    SliderAdapter sliderAdapter;
    TextView addToCart;
    ImageView img_icon_bag, imgLike;
    SharedPrefManager sharedPrefManager;
    ProgressDialog progressDialog;
    boolean isLiked;
    DecimalFormat df = new DecimalFormat("#.#");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);
        AnhXa();
        //SetSpinner();
        setRvProductGoiY();
        getProductId();
        getProductDetail();
        setSoldProduct();
        setFollowProduct();
        addToCart();
        set2Feedback();
        GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gioHang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(gioHang);
            }
        });
    }

    private void AnhXa() {
        //       searchView = (SearchView) findViewById(R.id.search_view);
        sizeSpinner = (Spinner) findViewById(R.id.size_spinner);
        txtSize = findViewById(R.id.txtsize);
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

    private void setProgressDialog() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    private void setFollowProduct(){
        Log.d("TAG", "userFromShared: " + sharedPrefManager.getUser().getId());
        apiService.getFollowProduct(sharedPrefManager.getUser().getId()).enqueue(new Callback<FollowProductResponse>() {
            @Override
            public void onResponse(Call<FollowProductResponse> call, Response<FollowProductResponse> response) {
                imgLike.setBackgroundResource(R.drawable.icon_favourite);
                isLiked = false;
                if (response.isSuccessful()){
                    for (UserFollowProduct userFollowProduct : response.body().getBody()){
                        Log.d("TAG", "followProduct: productFollow: " + userFollowProduct.getProduct().getId());
                        if (userFollowProduct.getProduct().getId().equals(product.getId())){
                            Log.d("TAG", "onResponse: " + "true");
                            imgLike.setBackgroundResource(R.drawable.icon_favourited);
                            isLiked = true;
                        } else {
                            Log.d("TAG", "onResponse: " + "false");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<FollowProductResponse> call, Throwable t) {

            }
        });
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
        imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                AnimationUtil.translateAnimation(viewAnimation, addToCart, img_icon_bag, new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
    }
    private void getProductDetail(){
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        apiService.getProductDetail(product.getId()).enqueue(new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                sliderAdapter = new SliderAdapter(getApplicationContext(), response.body().getBody().getProductImages(), REQUEST_CODE);
                sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                Glide.with(getApplicationContext()).load(response.body().getBody().getProductImages().get(0).getImage()).into(viewAnimation);
                sliderView.setSliderAdapter(sliderAdapter);
                sliderView.setScrollTimeInSec(3);
                sliderView.setAutoCycle(true);
                sliderView.startAutoCycle();

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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setRvProductGoiY(){
        apiService.getRandomProduct(5).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()){
                    products = response.body().getBody();
                    if (products == null){
                        Log.e("TAG", "onResponse: " + "NULL" );
                    }
                    productHomeAdapter = new ProductHomeAdapter(ChiTietSanPhamActivity.this, products);
                    rvProducGoiY.setHasFixedSize(true);
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, RecyclerView.HORIZONTAL);

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
                    if(feedbacks.size() == 0){
                        lnReview.setVisibility(View.GONE);
                    }
                    else {
                        setRatingTable(feedbacks, progressBar_nho, progressBar_vua, progressBar_lon,
                                tv_nho, tv_vua, tv_lon);
                        tv_rate_num.setText(df.format(avgRating(feedbacks)));
                        tv_rate_sanpham_tren.setText(String.valueOf(feedbacks.size()));
                        tv_rate_sanpham_duoi.setText(String.valueOf(feedbacks.size()));
                        rate_sanpham_duoi.setRating(avgRating(feedbacks));
                        rate_sanpham_tren.setRating(avgRating(feedbacks));

                        List<Review> twoFeedbacks = new ArrayList<>();
                        if (feedbacks.size() >= 2) {
                            twoFeedbacks = new ArrayList<>(feedbacks.subList(0, 2));
                        } else {
                            twoFeedbacks = feedbacks;
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