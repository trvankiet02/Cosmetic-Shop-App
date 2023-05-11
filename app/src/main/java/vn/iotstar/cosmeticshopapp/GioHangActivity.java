package vn.iotstar.cosmeticshopapp;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.helper.SwipeHelper;
import vn.iotstar.cosmeticshopapp.adapter.Cart_itemAdapter;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.room.dao.CartDAO;
import vn.iotstar.cosmeticshopapp.room.dao.CartItemDAO;
import vn.iotstar.cosmeticshopapp.room.database.CartDatabase;
import vn.iotstar.cosmeticshopapp.room.database.CartItemDatabase;

public class GioHangActivity extends AppCompatActivity {
    List<Product> products;
    Cart_itemAdapter productGioHangAdapter;
    RecyclerView rvProductGioHang;
    CartDAO cartDao;
    CartItemDAO cartItemDao;
    APIService apiService;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        AnhXa();
        setRvProductGioHang();
        btnXoa();
    }

    private void setRvProductGioHang() {
        //productGioHangAdapter = new ProductGioHangAdapter(GioHangActivity.this, products);
        rvProductGioHang.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(GioHangActivity.this, RecyclerView.VERTICAL, false);
        rvProductGioHang.setLayoutManager(layoutManager);
        rvProductGioHang.setAdapter(productGioHangAdapter);
        productGioHangAdapter.notifyDataSetChanged();
    }

    private void AnhXa(){
        rvProductGioHang = (RecyclerView) findViewById(R.id.rvProduct_gioHang);
        products = new ArrayList<>();
        cartDao = CartDatabase.getInstance(this).cartDao();
        cartItemDao = CartItemDatabase.getInstance(this).cartItemDao();
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        progressDialog = new ProgressDialog(this);
        setProgressDialog();
    }
    private void setProgressDialog(){
        progressDialog.setTitle("Đang tải...");
        progressDialog.setMessage("Làm ơn hãy đợi trong giây lát...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    private void btnXoa(){
        SwipeHelper swipeHelper = new SwipeHelper(this, rvProductGioHang, false) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                Typeface typeface = Typeface.create("sans-serif", Typeface.SANS_SERIF.getStyle());
                // Button Xóa
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Xóa",
                        null, // icon set to null to remove image
                        Color.parseColor("#FF0000"),
                        Color.parseColor("#FFFFFF"),
                        typeface,
                        new UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Toast.makeText(
                                        GioHangActivity.this,
                                        "Đã xóa" + products.get(pos).getName(),
                                        Toast.LENGTH_SHORT
                                ).show();
                                productGioHangAdapter.getModelList().remove(pos);
                                productGioHangAdapter.notifyItemRemoved(pos);
                            }
                        }
                ));

            }
        };
    }

}
