package vn.iotstar.cosmeticshopapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.adapter.CategoryHomeAdapter;
import vn.iotstar.cosmeticshopapp.adapter.ProductHomeAdapter;
import vn.iotstar.cosmeticshopapp.adapter.StyleOfStyleAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.CategoryAndStyleResponse;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.model.ProductResponse;
import vn.iotstar.cosmeticshopapp.model.Style;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;

public class StyleActivity extends AppCompatActivity {
    RecyclerView rcStyle, rvProduct;
    APIService apiService;
    ProductHomeAdapter productHomeAdapter;
    List<Product> products;
    StyleOfStyleAdapter styleOfStyleAdapter;
    List<Style> styleList;
    Style style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style);
        anhXa();
        getStyleFromAdapter();
        //set 2 recycler view
        setStyleRecyclerView();
        setProductRecyclerView();
    }
    private void anhXa() {
        rcStyle = findViewById(R.id.rcStyle);
        rvProduct = findViewById(R.id.rvProduct);
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
    }
    private void getStyleFromAdapter(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        style = (Style) bundle.getSerializable("style");
        Log.d("TAG", "getProductFromAdapter: " + style.getId());
    }

    private void setStyleRecyclerView() {
//        apiService.getStyle().enqueue(new Callback<CategoryAndStyleResponse>() {
//            @Override
//            public void onResponse(Call<CategoryAndStyleResponse> call, Response<CategoryAndStyleResponse> response) {
//                if  (response.isSuccessful()){
//                    styles = response.body().getBody();
//                    styleOfStyleAdapter = new CategoryHomeAdapter(StyleActivity.this, styles);
//                    rvCategoryHome.setHasFixedSize(true);
//                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false);
//                    rvCategoryHome.setLayoutManager(layoutManager);
//                    rvCategoryHome.setAdapter(categoryHomeAdapter);
//                    categoryHomeAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CategoryAndStyleResponse> call, Throwable t) {
//                Log.e(TAG, "onFailure: " + t.getMessage());
//            }
//        });
    }

    private void setProductRecyclerView() {
        apiService.getRandomProduct(5).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()){
                    products = response.body().getBody();
                    if (products == null){
                        Log.e("TAG", "onResponse: " + "NULL" );
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