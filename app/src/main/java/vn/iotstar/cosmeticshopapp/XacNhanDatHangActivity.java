package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class XacNhanDatHangActivity extends AppCompatActivity implements XacNhanShopAdapter.TotalAmountListener {
    LinearLayout ln_chondiachi,ln_themdiachi;
    TextView tvHoTen, tvSoDienThoai,tvDiaChi;
    TextView tvtongcong1, tvsophieugiamgia, tvtamtinh,tvchietkhau,tvtongcongtienthanhtoan, tvPhiVanChuyen, tvDamBaoVanChuyen, tvpgg;
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
    Spinner addressSpinner, voucher_spinner;
    Integer discount;
    Integer chietKhau;

    private int totalAmount;

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
            List<Cart> validCarts = new ArrayList<>();
            for (Cart cart : carts) {
                if (cart.getCartItems().size() > 0) {
                    validCarts.add(cart);
                }
            }
            carts = validCarts;
        }

        xacNhanShopAdapter = new XacNhanShopAdapter(XacNhanDatHangActivity.this, carts);
        xacNhanShopAdapter.setTotalAmountListener(this);
        rv_shop.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(XacNhanDatHangActivity.this, RecyclerView.VERTICAL, false);
        rv_shop.setLayoutManager(layoutManager);
        rv_shop.setAdapter(xacNhanShopAdapter);
        xacNhanShopAdapter.notifyDataSetChanged();

        tvtongcongtienthanhtoan.setText(String.valueOf(xacNhanShopAdapter.getTotal()));
        Integer tamTinh = xacNhanShopAdapter.getTotal() - xacNhanShopAdapter.getBaoHoTotal() - xacNhanShopAdapter.getGiaoHangTotal();
        Integer giaoHang = xacNhanShopAdapter.getGiaoHangTotal();
        Integer baoHo = xacNhanShopAdapter.getBaoHoTotal();
        discount = xacNhanShopAdapter.getTotal()*10/100;
        chietKhau = (tamTinh)*10/100;
        Integer tongCong = tamTinh + giaoHang + baoHo - discount - chietKhau;

        tvtamtinh.setText(String.valueOf(tamTinh));
        tvPhiVanChuyen.setText(String.valueOf(giaoHang));
        tvDamBaoVanChuyen.setText(String.valueOf(baoHo));
        tvpgg.setText(String.valueOf(-discount));
        tvchietkhau.setText(String.valueOf(-chietKhau));
        tvtongcongtienthanhtoan.setText(String.valueOf(tongCong));

    }
    private void selectVoucher(){

    }

    private void setSpinner() {

        apiService.getAddressByUserId(sharedPrefManager.getUser().getId()).enqueue(new Callback<ListAddressResponse>() {
            @Override
            public void onResponse(Call<ListAddressResponse> call, Response<ListAddressResponse> response) {
                if (response.isSuccessful()){
                    addressList = response.body().getBody();
                    List<String> addresses = new ArrayList<>();

                    for (Address address : addressList){
                        addresses.add("Tên người nhận: " + address.getFirstName() + " " + address.getLastName()
                                + "\nSố điện thoại: " + address.getPhone()
                                + "\nĐịa chỉ: " + address.getAddress());
                        if (address.getIsDefault()){
                            tvHoTen.setText(address.getFirstName() + " " + address.getLastName());
                            tvSoDienThoai.setText(address.getPhone());
                            tvDiaChi.setText(address.getAddress());
                        }
                    }
                    ArrayAdapter<String> addressArrayAdapter = new ArrayAdapter<>(XacNhanDatHangActivity.this, R.layout.my_custom_spinner_dropdown_item, addresses);
                    addressSpinner.setAdapter(addressArrayAdapter);



                    addressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedAddress = (String) parent.getItemAtPosition(position);
                            int pos = addresses.indexOf(selectedAddress);
                            tvHoTen.setText(addressList.get(pos).getFirstName() + " " + addressList.get(pos).getLastName());
                            tvSoDienThoai.setText(addressList.get(pos).getPhone());
                            tvDiaChi.setText(addressList.get(pos).getAddress());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ListAddressResponse> call, Throwable t) {

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
        tvPhiVanChuyen = findViewById(R.id.tvPhiVanChuyen);
        tvDamBaoVanChuyen = findViewById(R.id.tvDamBaoVanChuyen);
        tvpgg = findViewById(R.id.tvpgg);
        voucher_spinner = findViewById(R.id.voucher_spinner);
    }

    @Override
    public void onTotalAmountChanged(int totalAmount, int baoHoAmount, int giaoHangAmount) {
        this.totalAmount = totalAmount;
        tvtamtinh.setText(String.valueOf(totalAmount - baoHoAmount - giaoHangAmount));
        tvDamBaoVanChuyen.setText(String.valueOf(baoHoAmount));
        tvPhiVanChuyen.setText(String.valueOf(giaoHangAmount));
        tvtongcongtienthanhtoan.setText(String.valueOf(totalAmount - discount - chietKhau));
    }
}