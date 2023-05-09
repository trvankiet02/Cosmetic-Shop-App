package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.adapter.FeedbackAdapter;
import vn.iotstar.cosmeticshopapp.adapter.ProductHomeAdapter;
import vn.iotstar.cosmeticshopapp.adapter.SliderAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Feedback;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.model.ProductDetailResponse;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.util.AnimationUtil;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    ImageView viewAnimation;
    Spinner sizeSpinner;
    TextView txtSize;
    RecyclerView rvProducGoiY;
    List<Product> products;
    ProductHomeAdapter productHomeAdapter;
    Product product;
    ImageView GioHang;
    APIService apiService;
    RecyclerView rvFeedback;
    List<Feedback> feedbacks;
    FeedbackAdapter feedbackAdapter;
    SliderView sliderView;
    SliderAdapter sliderAdapter;
    TextView addToCart;
    ImageView img_icon_bag;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);

        AnhXa();
        //SetSpinner();
        //setRvProductGoiY();
        //set2Feedback();
        getProductId();
        getProductDetail();
        addToCart();
        GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gioHang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(gioHang);
            }
        });
    }


    private void getProductId() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        product = (Product) bundle.getSerializable("product");
        Log.d("TAG", "getProductFromAdapter: " + product.getId());
    }


    private void AnhXa() {
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
        apiService.getProductDetail(product.getId()).enqueue(new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                sliderAdapter = new SliderAdapter(getApplicationContext(), response.body().getBody().getProductImages());
                sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                Glide.with(getApplicationContext()).load(response.body().getBody().getProductImages().get(0).getImage()).into(viewAnimation);
                sliderView.setSliderAdapter(sliderAdapter);
                sliderView.setScrollTimeInSec(3);
                sliderView.setAutoCycle(true);
                sliderView.startAutoCycle();
            }

            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {
                Toast.makeText(ChiTietSanPhamActivity.this, "0000000000", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void SetSpinner() {
        String[] sizes = {"S", "M", "L", "XL", "XXL"};

        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sizes);

        sizeSpinner.setAdapter(sizeAdapter);
        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = parent.getItemAtPosition(position).toString();
                txtSize.setText(selectedOption);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setRvProductGoiY(){
        productHomeAdapter = new ProductHomeAdapter(this, products);
        rvProducGoiY.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvProducGoiY.setLayoutManager(layoutManager);
        rvProducGoiY.setAdapter(productHomeAdapter);
        productHomeAdapter.notifyDataSetChanged();
    }
    private void set2Feedback(){
        List<Feedback> twoFeedbacks = new ArrayList<>(feedbacks.subList(0, 2));

        feedbackAdapter = new FeedbackAdapter(this, twoFeedbacks);
        rvFeedback.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvFeedback.setLayoutManager(layoutManager);
        rvFeedback.setAdapter(feedbackAdapter);
        feedbackAdapter.notifyDataSetChanged();
    }
}