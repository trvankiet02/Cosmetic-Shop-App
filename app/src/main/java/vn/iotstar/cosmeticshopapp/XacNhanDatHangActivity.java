package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.adapter.CartAdapter;
import vn.iotstar.cosmeticshopapp.adapter.DiaChiAdapter;
import vn.iotstar.cosmeticshopapp.adapter.XacNhanShopAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Address;
import vn.iotstar.cosmeticshopapp.model.Cart;
import vn.iotstar.cosmeticshopapp.model.ListAddressResponse;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.model.User;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;

public class XacNhanDatHangActivity extends AppCompatActivity {
    LinearLayout ln_chondiachi,ln_themdiachi;
    TextView tvHoTen, tvSoDienThoai,tvDiaChi;
    TextView tvtongcong1, tvsophieugiamgia, tvtamtinh,tvchietkhau,tvtongcongtienthanhtoan;
    TextView tvtieptucthanhtoan;
    RadioButton rb_giaohangtieuchuan;
    Switch switchdambaovanchuyen;
    XacNhanShopAdapter xacNhanShopAdapter;
    RecyclerView rv_shop;
    List<Cart> carts;
    APIService apiService;
    SharedPrefManager sharedPrefManager;
    User user;
    List <Address> addressList;
    Spinner addressSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_dat_hang);
        anhXa();
        setSpinner();
        setRVXacNhan_Shop();
    }

    private void setRVXacNhan_Shop() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            carts = (List<Cart>) bundle.getSerializable("cartList");
            if (carts != null) {
                // Sử dụng danh sách cartList ở đây
                Log.d("TAG", "Received cartList with size: " + carts.size());
            }
        }

        xacNhanShopAdapter = new XacNhanShopAdapter(XacNhanDatHangActivity.this, carts);
        rv_shop.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(XacNhanDatHangActivity.this, RecyclerView.VERTICAL, false);
        rv_shop.setLayoutManager(layoutManager);
        rv_shop.setAdapter(xacNhanShopAdapter);
        xacNhanShopAdapter.notifyDataSetChanged();

//        productGioHangAdapter = new CartAdapter(GioHangActivity.this, carts);
//        rvProductGioHang.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(GioHangActivity.this, RecyclerView.VERTICAL, false);
//        rvProductGioHang.setLayoutManager(layoutManager);
//        rvProductGioHang.setAdapter(productGioHangAdapter);
//        productGioHangAdapter.notifyDataSetChanged();
    }

    private void setSpinner() {
        List<String> addresses = new ArrayList<>();
        apiService.getAddressByUserId(sharedPrefManager.getUser().getId()).enqueue(new Callback<ListAddressResponse>() {
            @Override
            public void onResponse(Call<ListAddressResponse> call, Response<ListAddressResponse> response) {
                if (response.isSuccessful()){
                    addressList = response.body().getBody();
                    Log.e("TAG", "setSpinner: 0 " + addressList.size() );
                    for (Address address : addressList){
                        addresses.add("Tên người nhận: " + address.getFirstName() + " " + address.getLastName() + "." +
                                "\nSố điện thoại: " + address.getPhone() + "" +
                                "\nĐịa chỉ: " + address.getAddress() + ".");
                    }
                } else {
                    Toast.makeText(XacNhanDatHangActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListAddressResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage() );
            }
        });
        // Tạo một ArrayAdapter để cung cấp dữ liệu cho Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.my_custom_spinner_dropdown_item, addresses);

        // Thiết lập Adapter cho Spinner
        addressSpinner.setAdapter(adapter);

        int selectedIndex = addresses.indexOf(addresses.get(0));
        addressSpinner.setSelection(selectedIndex);
        addressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý sự kiện khi người dùng chọn một mục trong Spinner
                String selectedAddress = (String) parent.getItemAtPosition(position);
                tvDiaChi.setText(addressSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void anhXa() {
        addressSpinner = findViewById(R.id.address_spinner);
        ln_chondiachi = findViewById(R.id.ln_chondiachi);
        ln_themdiachi = findViewById(R.id.ln_themdiachi);
        tvHoTen = findViewById(R.id.tvHoTen);
        tvSoDienThoai = findViewById(R.id.tvSoDienThoai);
        tvDiaChi = findViewById(R.id.tvDiaChi);
        tvtongcong1 = findViewById(R.id.tvtongcong1);
        tvsophieugiamgia = findViewById(R.id.tvsophieugiamgia);
        tvtamtinh = findViewById(R.id.tvtamtinh);
        tvchietkhau = findViewById(R.id.tvchietkhau);
        tvtongcongtienthanhtoan = findViewById(R.id.tvtongcongtienthanhtoan);
        tvtieptucthanhtoan = findViewById(R.id.tvtieptucthanhtoan);
        rb_giaohangtieuchuan = findViewById(R.id.rb_giaohangtieuchuan);
        switchdambaovanchuyen = findViewById(R.id.switchdambaovanchuyen);
        rv_shop = findViewById(R.id.rv_shop);
        carts = new ArrayList<>();
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        sharedPrefManager = new SharedPrefManager(XacNhanDatHangActivity.this);
        user = sharedPrefManager.getUser();
        addressList = new ArrayList<>();
    }
}