package vn.iotstar.cosmeticshopapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.adapter.CategoryHomeAdapter;
import vn.iotstar.cosmeticshopapp.adapter.ProductHomeAdapter;
import vn.iotstar.cosmeticshopapp.adapter.StyleOfStyleAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Category;
import vn.iotstar.cosmeticshopapp.model.CategoryAndStyleResponse;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.model.ProductResponse;
import vn.iotstar.cosmeticshopapp.model.Style;
import vn.iotstar.cosmeticshopapp.model.StyleByCategoryResponse;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;

public class StyleActivity extends AppCompatActivity {
    RecyclerView rcStyle, rvProduct;
    APIService apiService;
    ProductHomeAdapter productHomeAdapter;
    List<Product> products;
    StyleOfStyleAdapter styleOfStyleAdapter;
    List<Style> styleList;
    Style style;
    Category cate;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style);
        anhXa();
        getCategoryFromAdapter();
        getStyleFromAdapter();
        //set 2 recycler view
        setStyleRecyclerView();
        setProductRecyclerView();
    }

    private void anhXa() {
        rcStyle = findViewById(R.id.rcStyle);
        rvProduct = findViewById(R.id.rvProduct);
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        styleList = new ArrayList<>();
        tvTitle = findViewById(R.id.tvTitle);
    }

    private void getCategoryFromAdapter() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        cate = (Category) bundle.getSerializable("category");
        //Log.d("TAG", "getCategoryFromMain: " + cate.getName());
    }

    private void getStyleFromAdapter() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        style = (Style) bundle.getSerializable("style");
        //Log.d("TAG", "getProductFromAdapter: " + style.getId());
    }

    private void setStyleRecyclerView() {
        if (cate != null){
            style = cate.getStyles().get(0);
            tvTitle.setText(cate.getName());
        } else {
            apiService.getCategoryByStyle(style.getId()).enqueue(new Callback<CategoryAndStyleResponse>() {
                @Override
                public void onResponse(Call<CategoryAndStyleResponse> call, Response<CategoryAndStyleResponse> response) {
                    if (response.isSuccessful()) {
                        CategoryAndStyleResponse categoryAndStyleResponse = response.body();
                        cate = categoryAndStyleResponse.getBody().get(0);
                        tvTitle.setText(cate.getName());
                    } else {
                        Log.e("TAG", "onResponse: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<CategoryAndStyleResponse> call, Throwable t) {

                }
            });
        }
        apiService.getStyleSelling(style.getId()).enqueue(new Callback<StyleByCategoryResponse>() {
            @Override
            public void onResponse(Call<StyleByCategoryResponse> call, Response<StyleByCategoryResponse> response) {
                if (response.isSuccessful()) {
                    Style allStyle = new Style();
                    styleList = response.body().getBody();
                    styleOfStyleAdapter = new StyleOfStyleAdapter(StyleActivity.this, styleList);
                    rcStyle.setHasFixedSize(true);
                    GridLayoutManager layoutManager = new GridLayoutManager(StyleActivity.this, 1, RecyclerView.HORIZONTAL, false);
                    rcStyle.setLayoutManager(layoutManager);
                    rcStyle.setAdapter(styleOfStyleAdapter);
                    styleOfStyleAdapter.setOnItemClickListener(new StyleOfStyleAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Style style) {
                            apiService.getProductByStyle(style.getId(), true).enqueue(new Callback<ProductResponse>() {
                                @Override
                                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                                    if (response.isSuccessful()) {
                                        products = response.body().getBody();
                                        if (products == null) {
                                            Log.e("TAG", "onResponse: " + "NULL");
                                        }
                                        productHomeAdapter = new ProductHomeAdapter(StyleActivity.this, products);
                                        rvProduct.setHasFixedSize(true);
                                        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);

                                        rvProduct.setLayoutManager(layoutManager);
                                        rvProduct.setAdapter(productHomeAdapter);
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
                    });
                    styleOfStyleAdapter.notifyDataSetChanged();
                } else {
                    Log.e("TAG", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<StyleByCategoryResponse> call, Throwable t) {

            }
        });
    }

    private void setProductRecyclerView() {
        apiService.getProductByStyle(style.getId(), true).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    products = response.body().getBody();
                    if (products == null) {
                        Log.e("TAG", "onResponse: " + "NULL");
                    }
                    productHomeAdapter = new ProductHomeAdapter(StyleActivity.this, products);
                    rvProduct.setHasFixedSize(true);
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);

                    rvProduct.setLayoutManager(layoutManager);
                    rvProduct.setAdapter(productHomeAdapter);
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


}