package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import vn.iotstar.cosmeticshopapp.adapter.XacNhanShopAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Address;
import vn.iotstar.cosmeticshopapp.model.Cart;
import vn.iotstar.cosmeticshopapp.model.CartItem;
import vn.iotstar.cosmeticshopapp.model.Delivery;
import vn.iotstar.cosmeticshopapp.model.DeliveryListResponse;
import vn.iotstar.cosmeticshopapp.model.ListAddressResponse;
import vn.iotstar.cosmeticshopapp.model.OrderResponse;
import vn.iotstar.cosmeticshopapp.model.SingleOrderResponse;
import vn.iotstar.cosmeticshopapp.model.User;
import vn.iotstar.cosmeticshopapp.model.Voucher;
import vn.iotstar.cosmeticshopapp.model.VoucherResponse;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;

public class XacNhanDatHangActivity extends AppCompatActivity implements XacNhanShopAdapter.TotalAmountListener {
    LinearLayout ln_chondiachi, ln_themdiachi;
    TextView tvHoTen, tvSoDienThoai, tvDiaChi;
    TextView tvsophieugiamgia, tvtamtinh, tvchietkhau, tvtongcongtienthanhtoan, tvPhiVanChuyen, tvDamBaoVanChuyen, tvpgg;
    TextView tvtieptucthanhtoan, tvDeliveryName, tvPayMethodName;
    RadioButton rb_giaohangtieuchuan;
    Switch switchdambaovanchuyen;
    XacNhanShopAdapter xacNhanShopAdapter;
    RecyclerView rv_shop;
    List<Cart> carts;
    APIService apiService;
    SharedPrefManager sharedPrefManager;
    User user;
    List<Address> addressList;
    Spinner addressSpinner, voucher_spinner, delivery_spinner, paymethodSpinner;
    Integer discount;
    Integer chietKhau;
    List<Voucher> voucherList;
    List<Delivery> deliveryList;
    int voucherId;
    int deliveryId;
    int payMethodId;
    int addressId;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    ProgressDialog progressDialog;

    private int totalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_dat_hang);
        anhXa();
        setSpinner();
        selectVoucher();
        setRVXacNhan_Shop();
        setDeliverySpinner();
        setPayMethodSpinner();
        setBtnThanhToan();
    }

    private void setBtnThanhToan() {
        tvtieptucthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }

    private void setPayMethodSpinner() {
        List<String> payMethodList = new ArrayList<>();
        payMethodList.add("Thanh toán khi nhận hàng");
        payMethodList.add("Thanh toán bằng eWallet");
        ArrayAdapter<String> payMethodAdapter = new ArrayAdapter<>(XacNhanDatHangActivity.this, android.R.layout.simple_spinner_item, payMethodList);
        payMethodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymethodSpinner.setAdapter(payMethodAdapter);
        paymethodSpinner.setSelection(0);
        paymethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String payMethod = parent.getItemAtPosition(position).toString();
                payMethodId = payMethodList.indexOf(payMethod);
                if (payMethodId == 1) {
                    tvPayMethodName.setText(String.valueOf(sharedPrefManager.getUser().getEwallet()));
                    tvPayMethodName.setTextColor(getResources().getColor(R.color.black));
                    tvPayMethodName.setTextSize(18);
                } else {
                    tvPayMethodName.setText("Thanh toán khi nhận hàng");
                    tvPayMethodName.setTextColor(getResources().getColor(R.color.black));
                    tvPayMethodName.setTextSize(14);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        discount = xacNhanShopAdapter.getTotal() * 10 / 100;
        chietKhau = (tamTinh) * 10 / 100;
        Integer tongCong = tamTinh + giaoHang + baoHo - discount + chietKhau;

        tvtamtinh.setText(String.valueOf(tamTinh));
        tvPhiVanChuyen.setText(String.valueOf(giaoHang));
        tvDamBaoVanChuyen.setText(String.valueOf(baoHo));

        tvchietkhau.setText(String.valueOf(chietKhau));
        tvtongcongtienthanhtoan.setText(String.valueOf(tongCong));

    }

    private void selectVoucher() {
//set voucher spinner
        apiService.getAllVoucher().enqueue(new Callback<VoucherResponse>() {
            @Override
            public void onResponse(Call<VoucherResponse> call, Response<VoucherResponse> response) {
                if (response.isSuccessful()) {
                    voucherList = response.body().getBody();
                    List<String> stringVoucher = new ArrayList<>();
                    for (Voucher voucher : voucherList) {
                        stringVoucher.add("Mã giảm giá: " + voucher.getCode()
                                + "\nGiảm giá: " + voucher.getDiscount() * 100 + "%"
                                + "\nSố lượng: " + voucher.getQuantity()
                                + "\n Giảm tối đa: " + voucher.getMaxDiscount() + "đ"
                                + "\nNgày hết hạn: " + voucher.getExpireAt());
                    }
                    tvsophieugiamgia.setText(String.valueOf(stringVoucher.size()));
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(XacNhanDatHangActivity.this, R.layout.my_custom_spinner_dropdown_item, stringVoucher);
                    voucher_spinner.setAdapter(adapter);
                    voucher_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String discount = (String) parent.getItemAtPosition(position);
                            voucherId = stringVoucher.indexOf(discount);
                            int chietKhau = (int) (xacNhanShopAdapter.getTotal() * voucherList.get(voucherId).getDiscount());
                            if (chietKhau > voucherList.get(voucherId).getMaxDiscount()) {
                                chietKhau = voucherList.get(voucherId).getMaxDiscount();
                            }
                            tvpgg.setText(String.valueOf(chietKhau));
                            tvtongcongtienthanhtoan.setText(String.valueOf(xacNhanShopAdapter.getTotal() + Integer.parseInt(tvDamBaoVanChuyen.getText().toString())
                                    - chietKhau + Integer.parseInt(tvPhiVanChuyen.getText().toString()) + Integer.parseInt(tvchietkhau.getText().toString())));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<VoucherResponse> call, Throwable t) {

            }
        });
/*=======
                    tvsophieugiamgia.setText(String.valueOf(response.body().getBody().size()));
                    List<Voucher> vouchers = response.body().getBody();
                    List<String> voucherNames = new ArrayList<>();
                    for (Voucher voucher : vouchers){
                        voucherNames.add(String.valueOf(voucher.getDescription()));
                    }
                    ArrayAdapter<String> voucherArrayAdapter = new ArrayAdapter<>(XacNhanDatHangActivity.this, R.layout.my_custom_spinner_dropdown_item, voucherNames);
                    voucher_spinner.setAdapter(voucherArrayAdapter);
                    voucher_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedVoucher = (String) parent.getItemAtPosition(position);
                            int pos = voucherNames.indexOf(selectedVoucher);
                            tvpgg.setText(String.valueOf(vouchers.get(pos).getDiscount()*Double.parseDouble(tvtamtinh.getText().toString())));
>>>>>>> 0d175ec414a9f6c875f0b7b7a76a92cb47d8c3d9*/

    }
/*
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<VoucherResponse> call, Throwable t) {

            }
        });*/

    private void setSpinner() {

        apiService.getAddressByUserId(sharedPrefManager.getUser().getId()).enqueue(new Callback<ListAddressResponse>() {

            @Override
            public void onResponse(Call<ListAddressResponse> call, Response<ListAddressResponse> response) {
                if (response.isSuccessful()) {
                    addressList = response.body().getBody();
                    List<String> addresses = new ArrayList<>();

                    for (Address address : addressList) {
                        addresses.add("Tên người nhận: " + address.getFirstName() + " " + address.getLastName()
                                + "\nSố điện thoại: " + address.getPhone()
                                + "\nĐịa chỉ: " + address.getAddress());
                        if (address.getIsDefault()) {
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
                            addressId = addresses.indexOf(selectedAddress);
                            tvHoTen.setText(addressList.get(voucherId).getFirstName() + " " + addressList.get(voucherId).getLastName());
                            tvSoDienThoai.setText(addressList.get(voucherId).getPhone());
                            tvDiaChi.setText(addressList.get(voucherId).getAddress());
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
    private void setDeliverySpinner(){
        apiService.getAllDelivery().enqueue(new Callback<DeliveryListResponse>() {
            @Override
            public void onResponse(Call<DeliveryListResponse> call, Response<DeliveryListResponse> response) {
                if (response.isSuccessful()){
                    deliveryList = response.body().getBody();
                    List<String> deliveryNames = new ArrayList<>();
                    for (Delivery delivery : deliveryList){
                        deliveryNames.add(delivery.getDeliveryName());
                    }
                    ArrayAdapter<String> deliveryArrayAdapter = new ArrayAdapter<>(XacNhanDatHangActivity.this, R.layout.my_custom_spinner_dropdown_item, deliveryNames);
                    delivery_spinner.setAdapter(deliveryArrayAdapter);
                    tvDeliveryName.setText(deliveryList.get(0).getDeliveryName());
                    delivery_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedDelivery = (String) parent.getItemAtPosition(position);
                            deliveryId = deliveryNames.indexOf(selectedDelivery);
                            tvDeliveryName.setText(deliveryList.get(deliveryId).getDeliveryName());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    Toast.makeText(XacNhanDatHangActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeliveryListResponse> call, Throwable t) {
                Log.e("Error", t.getMessage());
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
        voucherList = new ArrayList<>();
        delivery_spinner = findViewById(R.id.delivery_spinner);
        deliveryList = new ArrayList<>();
        tvDeliveryName = findViewById(R.id.tvDeliveryName);
        tvPayMethodName = findViewById(R.id.tvPayMethodName);
        paymethodSpinner = findViewById(R.id.payMethodSpinner);
        progressDialog = new ProgressDialog(XacNhanDatHangActivity.this);
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        builder = new AlertDialog.Builder(XacNhanDatHangActivity.this);
        builder.setTitle("Xác nhận đặt hàng");
        builder.setMessage("Bạn có chắc chắn muốn đặt hàng?");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                List<Integer> cartItemIdList = new ArrayList<>();
                for (Cart cart : carts) {
                    for (CartItem cartItem : cart.getCartItems()) {
                        cartItemIdList.add(cartItem.getId());
                        Log.d("TAG", "cartItem Id: " + cartItem.getId());
                    }
                }
                Integer totalPrice = Integer.parseInt(tvtongcongtienthanhtoan.getText().toString());
                Log.d("TAG", sharedPrefManager.getUser().getId()
                        + "\n" + addressList.get(addressId).getFirstName() + " " + addressList.get(addressId).getLastName()
                + "\n" + deliveryId
                + "\n" + addressList.get(addressId).getAddress()
                + "\n" + addressList.get(addressId).getPhone()
                + "\n" + voucherId
                + "\n" + tvtongcongtienthanhtoan.getText().toString()
                + "\n" + payMethodId);
                if (deliveryId == 0) { deliveryId = deliveryList.get(0).getId(); }
                Log.d("TAG", "onClick: "  + voucherId);
                if (voucherId == 0) { voucherId = voucherList.get(0).getId();}
                Log.d("TAG", "onClick: "  + voucherId);
                if (payMethodId == 1){
                    if (sharedPrefManager.getUser().getEwallet() < totalPrice){
                        Toast.makeText(XacNhanDatHangActivity.this, "Số dư ví không đủ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                progressDialog.show();
                apiService.addOrder(cartItemIdList,
                        sharedPrefManager.getUser().getId(),
                        addressList.get(addressId).getFirstName() + " " + addressList.get(addressId).getLastName(),
                        deliveryId,
                        addressList.get(addressId).getAddress(),
                        addressList.get(addressId).getPhone(),
                        voucherId,
                        totalPrice,
                        payMethodId).enqueue(new Callback<OrderResponse>() {
                    @Override
                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()){
                            Toast.makeText(XacNhanDatHangActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), XuLyDonHangActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("fragment", 1);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(XacNhanDatHangActivity.this, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
                            Log.e("TAG", "onResponse: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(XacNhanDatHangActivity.this, "Đặt hàng lỗi", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "onFailure: " + t.getMessage() + "\n" + t.getCause());
                    }
                });
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog = builder.create();
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