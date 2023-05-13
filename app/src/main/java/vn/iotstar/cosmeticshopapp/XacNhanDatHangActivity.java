package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.iotstar.cosmeticshopapp.adapter.CartAdapter;
import vn.iotstar.cosmeticshopapp.adapter.XacNhanShopAdapter;
import vn.iotstar.cosmeticshopapp.model.Cart;

public class XacNhanDatHangActivity extends AppCompatActivity {
    LinearLayout ln_chondiachi,ln_themdiachi;
    TextView tvHoTen, tvSoDienThoai,tvDiaChi;
    TextView tvtongcong1, tvsophieugiamgia, tvtamtinh,tvchietkhau,tvtongcongtienthanhtoan;
    TextView tvtieptucthanhtoan;
    RadioButton rb_giaohangtieuchuan;
    Switch switchdambaovanchuyen;
    Spinner address_spinner;
    XacNhanShopAdapter xacNhanShopAdapter;
    RecyclerView rv_shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_dat_hang);
        anhXa();
        setSpinner();
        setRVXacNhan_Shop();
    }

    private void setRVXacNhan_Shop() {
        List<Cart> carts = new ArrayList<>();
        carts.add(new Cart());
        carts.add(new Cart());
        carts.add(new Cart());

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
        String[] addresses = {"được sử dụng để xác định chiều cao ưu tiên của các mục trong danh sách Spinner1",
                "AHGLAKDNS ALKSFNASFKAJN ANK ,ANFD, NÀN ÀN JAKBFKJSAFLAB JAJ KJAB LAKJ AJNFAKLF ANFALK NFAKFN ÁN,F MÀ NBLKFMAFNAKL FMA,F NALKF NA,F NÀ LANFJASNDF ALKSFJLKAFJ LAKNFASLK FALK ALK AKL JAFLKJAN",
                "được sử dụng đểg danh sách Spinner3",
                "được sử dụng để xác định chiều cao ưu tiên của các mục trong danh sách Spinner4",
                "được sử dụng để xác  sách Spinner5"};

        // Tạo một ArrayAdapter để cung cấp dữ liệu cho Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.my_custom_spinner_dropdown_item, addresses);

        // Thiết lập Adapter cho Spinner

        address_spinner.setAdapter(adapter);
        address_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý sự kiện khi người dùng chọn một mục trong Spinner
                String selectedAddress = (String) parent.getItemAtPosition(position);
                tvDiaChi.setText(selectedAddress);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý sự kiện khi không có mục nào được chọn trong Spinner

            }
        });
    }


    private void anhXa() {
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
        address_spinner = findViewById(R.id.address_spinner);
        rv_shop = findViewById(R.id.rv_shop);
    }
}