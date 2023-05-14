package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.adapter.ProductHomeAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.fragment_home.MoiFragment;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.model.ProductResponse;
import vn.iotstar.cosmeticshopapp.model.Store;
import vn.iotstar.cosmeticshopapp.model.User;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;

public class StoreActivity extends AppCompatActivity {
    ImageView ivStoreImage, ivFavouriteProduct, ivCart;
    SearchView search_view;
    TextView tvStoreName, tvEmail, tvStar, tvFollowCount;
    Button btnFollow;
    APIService apiService;
    SharedPrefManager sharedPrefManager;
    User user;
    RecyclerView rvProduct;
    List<Product> soldProductList;
    List<Product> newProductList;
    ProductHomeAdapter productHomeAdapter;
    TextView tv_SoldProduct, tv_NewProduct, tv_AllProduct;
    Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        anhXa();
        //nhận dữ liệu
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            store = (Store) bundle.get("store");
            getStoreInfo();
            getProductList();
            setClick();
        }

    }

    private void setClick() {
        tv_AllProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllProductList();
            }
        });
        tv_NewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNewProductList();
            }
        });
        tv_SoldProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProductList();
            }
        });
    }

    private void getAllProductList() {
        apiService.getProductByStore(store.getId(), true).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()){
                    soldProductList = response.body().getBody();
                    productHomeAdapter = new ProductHomeAdapter(StoreActivity.this, soldProductList);
                    rvProduct.setAdapter(productHomeAdapter);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(StoreActivity.this, 2);
                    rvProduct.setLayoutManager(gridLayoutManager);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });
    }

    private void getNewProductList() {
        apiService.getNewProduct(store.getId()).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()){
                    newProductList = response.body().getBody();
                    productHomeAdapter = new ProductHomeAdapter(StoreActivity.this, newProductList);
                    rvProduct.setAdapter(productHomeAdapter);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(StoreActivity.this, 2);
                    rvProduct.setLayoutManager(gridLayoutManager);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });
    }

    private void getStoreInfo() {
        tvStoreName.setText(store.getName());
        tvEmail.setText(store.getEmail());
        tvStar.setText(String.valueOf(Math.round(store.getRating()*10.0) /10.0));
        tvFollowCount.setText(String.valueOf(store.getUserFollowStores().size() + " Người theo dõi"));
        Glide.with(this).load(store.getStoreImage()).into(ivStoreImage);
    }

    private void getProductList() {
        apiService.getSoldProduct(store.getId()).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()){
                    soldProductList = response.body().getBody();
                    productHomeAdapter = new ProductHomeAdapter(StoreActivity.this, soldProductList);
                    rvProduct.setAdapter(productHomeAdapter);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(StoreActivity.this, 2);
                    rvProduct.setLayoutManager(gridLayoutManager);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });

    }

    private void anhXa() {
        ivStoreImage = findViewById(R.id.ivStoreImage);
        tvStoreName = findViewById(R.id.tvStoreName);
        tvEmail = findViewById(R.id.tvEmail);
        tvStar = findViewById(R.id.tvStar);
        tvFollowCount = findViewById(R.id.tvUserFollow);
        btnFollow = findViewById(R.id.btnFollow);
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        sharedPrefManager = new SharedPrefManager(this);
        user = sharedPrefManager.getUser();
        rvProduct = findViewById(R.id.rvProduct);
        soldProductList = new ArrayList<>();
        newProductList = new ArrayList<>();
        tv_SoldProduct = findViewById(R.id.tv_SoldProduct);
        tv_NewProduct = findViewById(R.id.tv_NewProduct);
        tv_AllProduct = findViewById(R.id.tv_AllProduct);
//        search_view = findViewById(R.id.search_view);
        ivFavouriteProduct = findViewById(R.id.ivFavouriteProduct);
        ivCart = findViewById(R.id.ivCart);
    }
}