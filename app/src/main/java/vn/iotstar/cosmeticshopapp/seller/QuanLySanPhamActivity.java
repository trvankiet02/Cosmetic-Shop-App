package vn.iotstar.cosmeticshopapp.seller;

import androidx.appcompat.app.AppCompatActivity;
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
import vn.iotstar.cosmeticshopapp.adapter.ProductEditAdapter;
import vn.iotstar.cosmeticshopapp.adapter.ProductHomeAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.model.ProductResponse;
import vn.iotstar.cosmeticshopapp.model.Store;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;

public class QuanLySanPhamActivity extends AppCompatActivity {

    ProductEditAdapter productEditAdapter;
    List<Product> products = new ArrayList<>();
    RecyclerView rvProduct;
    LinearLayout linearLayout2;
    APIService apiService;
    Store store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_san_pham);
        anhXa();
        //get bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            store = (Store) bundle.getSerializable("store");
            setListProduct();
        }

        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chuyển qua activity thêm sản phẩm
                Intent intent = new Intent(QuanLySanPhamActivity.this, ThemSuaSanPhamActivity.class);
                startActivity(intent);
            }
        });
    }

    private void anhXa() {
        rvProduct = findViewById(R.id.rvProduct);
        linearLayout2 = findViewById(R.id.linearLayout2);
    }

    private void setListProduct() {

        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        apiService.getProductByStore(store.getId()).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                products = response.body().getBody();
                productEditAdapter = new ProductEditAdapter(QuanLySanPhamActivity.this, products);
                rvProduct.setHasFixedSize(true);
                GridLayoutManager layoutManager = new GridLayoutManager(QuanLySanPhamActivity.this, 2, GridLayoutManager.VERTICAL, false);

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