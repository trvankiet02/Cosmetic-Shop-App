package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.View;
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

import com.smarteist.autoimageslider.SliderView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
import vn.iotstar.cosmeticshopapp.model.ProductQuantity;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    SearchView searchView;
    ImageView GioHang, imgFavorite;
    LinearLayout lnXemThem;
    TextView txtSize, tv_name_product, tvPriceSale, tvPrice, tvPrice_d, txtsoluong, tv_rate_sanpham_tren, tvAddress, tv_rate_sanpham_duoi, tvXemTatCa;
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
    List<Feedback> feedbacks;
    FeedbackAdapter feedbackAdapter;
    SliderView sliderView;
    SliderAdapter sliderAdapter;
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
        txtSize = (TextView) findViewById(R.id.txtsize);
        rvProducGoiY = (RecyclerView) findViewById(R.id.rvProduct);
        rvFeedback = (RecyclerView) findViewById(R.id.rvFeedback);
        GioHang = (ImageView) findViewById(R.id.img_icon_bag);
        imgFavorite = (ImageView) findViewById(R.id.img_icon_favorite);
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        sliderView = findViewById(R.id.slider);

        tvPriceSale = (TextView) findViewById(R.id.tv_price_sale);
        tv_name_product = (TextView) findViewById(R.id.tv_name_product);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvPrice_d = (TextView) findViewById(R.id.tv_d);
        txtsoluong = (TextView) findViewById(R.id.txtsoluong);
//        tv_rate_sanpham_tren = (TextView) findViewById(R.id.tv_rating);
//        tvAddress = (TextView) findViewById(R.id.tv_address);
//        tv_rate_sanpham_duoi = (TextView) findViewById(R.id.tv_rate1);
//        tvXemTatCa = (TextView) findViewById(R.id.tv_xemtatca);
//        tv_rate_num = (TextView) findViewById(R.id.tv_rate_num);
//        lnXemThem = (LinearLayout) findViewById(R.id.ln_xemthem);
//        tv_themVaoGio = (TextView) findViewById(R.id.tv_them_vao_gio);
//
//        rate_sanpham_tren = (RatingBar) findViewById(R.id.rate_sanpham_tren);
//        rate_sanpham_duoi = (RatingBar) findViewById(R.id.rate_sanpham_duoi);
//
//
//        tv_nho = (TextView) findViewById(R.id.tv_progress_bar_nho);
//        tv_vua = (TextView) findViewById(R.id.tv_progress_bar_vua);
//        tv_lon = (TextView) findViewById(R.id.tv_progress_bar_lon);
//        progressBar_nho = (ProgressBar) findViewById(R.id.progress_bar_nho);
//        progressBar_vua = (ProgressBar) findViewById(R.id.progress_bar_vua);
//        progressBar_lon = (ProgressBar) findViewById(R.id.progress_bar_lon);
    }

    private void getProductId() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        product = (Product) bundle.getSerializable("product");
        Log.d("TAG", "getProductFromAdapter: " + product.getId());
    }

    private void getProductDetail(){
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        apiService.getProductDetail(product.getId()).enqueue(new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                sliderAdapter = new SliderAdapter(getApplicationContext(), response.body().getBody().getProductImages());
                sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
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
        /*String[] sizes = new String[0];
        String[] soluong = new String[10];
        for(int i = 0; i < product.getProductQuantities().size(); i++){
            sizes = Arrays.copyOf(sizes, sizes.length + 1);
            sizes[sizes.length - 1] = product.getProductQuantities().get(i).getSize().toString();

            soluong = Arrays.copyOf(soluong, soluong.length + 1);
            soluong[soluong.length - 1] = product.getProductQuantities().get(i).getQuantity().toString();
        }*/
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