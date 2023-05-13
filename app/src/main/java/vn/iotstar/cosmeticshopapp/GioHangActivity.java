package vn.iotstar.cosmeticshopapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.adapter.CartAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.helper.SwipeHelper;
import vn.iotstar.cosmeticshopapp.adapter.CartItemAdapter;
import vn.iotstar.cosmeticshopapp.model.Cart;
import vn.iotstar.cosmeticshopapp.model.CartItem;
import vn.iotstar.cosmeticshopapp.model.CartResponse;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.room.dao.CartDAO;
import vn.iotstar.cosmeticshopapp.room.dao.CartItemDAO;
import vn.iotstar.cosmeticshopapp.room.database.CartDatabase;
import vn.iotstar.cosmeticshopapp.room.database.CartItemDatabase;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;

public class GioHangActivity extends AppCompatActivity {
    List<Cart> carts;
    CartAdapter productGioHangAdapter;
    RecyclerView rvProductGioHang;
    CartDAO cartDao;
    CartItemDAO cartItemDao;
    APIService apiService;
    ProgressDialog progressDialog;
    SharedPrefManager sharedPrefManager;
    ImageView ivFavouriteProduct;
    TextView tvTotalPrice, btnThanhToan;
    CheckBox cbSelectAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        AnhXa();
        setRvProductGioHang();
        setBtnThanhToan();
        //btnXoa();
    }

    private void setBtnThanhToan() {
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer total = 0;

                /*for (CartItem cI : productGioHangAdapter.getSelectedCartItem()) {
                    total += cI.getQuantity() * cI.getProduct().getPromotionalPrice();
                    Log.d("TAG", "onClick: " + cI.getProduct().getName() + " " + cI.getQuantity() + " " + cI.getProduct().getPromotionalPrice());
                }*/

//                for (Cart c: productGioHangAdapter.getSelectedCart()){
//                    for (CartItem cI: c.getCartItems()){
//                        total += cI.getQuantity() * cI.getProduct().getPromotionalPrice();
//                        Log.d("TAG", "onClick: " + cI.getProduct().getName() + " " + cI.getQuantity() + " " + cI.getProduct().getPromotionalPrice());
//                    }
//                }

                //chuyển qua XacNhanDatHangActivity
                Intent intent = new Intent(GioHangActivity.this, XacNhanDatHangActivity.class);
                startActivity(intent);
                tvTotalPrice.setText(total.toString() + "đ");
            }
        });
    }

    private void setRvProductGioHang() {
        apiService.getCart(sharedPrefManager.getUser().getId()).enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful()) {
                    carts = response.body().getBody();
                    productGioHangAdapter = new CartAdapter(GioHangActivity.this, carts);
                    rvProductGioHang.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(GioHangActivity.this, RecyclerView.VERTICAL, false);
                    rvProductGioHang.setLayoutManager(layoutManager);
                    rvProductGioHang.setAdapter(productGioHangAdapter);
                    productGioHangAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {

            }
        });
    }

    private void AnhXa() {
        rvProductGioHang = (RecyclerView) findViewById(R.id.rvProduct_gioHang);
        cartDao = CartDatabase.getInstance(this).cartDao();
        cartItemDao = CartItemDatabase.getInstance(this).cartItemDao();
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        progressDialog = new ProgressDialog(this);
        setProgressDialog();
        sharedPrefManager = new SharedPrefManager(this);
        carts = new ArrayList<>();
        ivFavouriteProduct = (ImageView) findViewById(R.id.ivFavouriteProduct);
        tvTotalPrice = (TextView) findViewById(R.id.tvTongTien);
        cbSelectAll = (CheckBox) findViewById(R.id.cbAll);
        btnThanhToan = (TextView) findViewById(R.id.btnThanhToan);
    }

    private void setProgressDialog() {
        progressDialog.setTitle("Đang tải...");
        progressDialog.setMessage("Làm ơn hãy đợi trong giây lát...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    private void btnXoa() {
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
                                        "Đã xóa",
                                        Toast.LENGTH_SHORT
                                ).show();
                                //productGioHangAdapter.getModelList().remove(pos);
                                //productGioHangAdapter.notifyItemRemoved(pos);
                            }
                        }
                ));

            }
        };
    }
}
