package vn.iotstar.cosmeticshopapp.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.ChiTietSanPhamActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.SettingActivity;
import vn.iotstar.cosmeticshopapp.adapter.ProductEditAdapter;
import vn.iotstar.cosmeticshopapp.adapter.ProductHomeAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.model.ProductResponse;
import vn.iotstar.cosmeticshopapp.model.Store;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;

public class QuanLySanPhamActivity extends AppCompatActivity {

    ProductEditAdapter productEditAdapter;
    SearchView searchView;
    List<Product> products = new ArrayList<>();
    RecyclerView rvProduct;
    LinearLayout linearLayout2;
    APIService apiService;
    SharedPrefManager sharedPrefManager;
    Integer storeId;
    List<Product> defaultProductList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_san_pham);
        anhXa();
        sharedPrefManager = new SharedPrefManager(this);
        storeId = sharedPrefManager.getStoreId();
        //get bundle
        setListProduct();
        setSearchView();

        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chuyển qua activity thêm sản phẩm
                Intent intent = new Intent(QuanLySanPhamActivity.this, ThemSuaSanPhamActivity.class);
                startActivity(intent);
                //finish();
            }
        });
    }

    private void anhXa() {
        rvProduct = findViewById(R.id.rvProduct);
        linearLayout2 = findViewById(R.id.linearLayout2);
        searchView = findViewById(R.id.search_view);
    }
    private void setSearchView() {
        searchView.requestFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productEditAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
    private void filterListener(String text) {
        List<Product> productList = new ArrayList<>();
        for (Product product: products){
            if (product.getName().toLowerCase().contains(text.toLowerCase())){
                productList.add(product);
            }
            if (productList.size() == 0) {
                rvProduct.setVisibility(View.GONE);
            } else {
                productEditAdapter.updateProduct(productList);
            }
        }
    }

    private void setListProduct() {

        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        apiService.getProductByStore(storeId).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                products = response.body().getBody();
                defaultProductList = products;
                productEditAdapter = new ProductEditAdapter(QuanLySanPhamActivity.this, products);
                rvProduct.setHasFixedSize(true);
                GridLayoutManager layoutManager = new GridLayoutManager(QuanLySanPhamActivity.this, 2, GridLayoutManager.VERTICAL, true);

                rvProduct.setLayoutManager(layoutManager);
                rvProduct.setAdapter(productEditAdapter);
                productEditAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });


    }


}