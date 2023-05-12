package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Address;
import vn.iotstar.cosmeticshopapp.model.AddressResponse;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;

public class ThemSuaDiaChiActivity extends AppCompatActivity {
    Switch switchMacDinh;
    EditText edtHo, edtTen, edtSoDienThoai, edtDiaChi;
    boolean isMacDinh = false;
    TextView tvLuu;
    Address address;
    SharedPrefManager sharedPrefManager;
    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sua_dia_chi);
        anhXa();
        setClick();
        getAddressFromAdapter();
        setData();
    }

    private void setData() {
        apiService.getAddress(address.getId()).enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                if (response.isSuccessful()){
                    Address address = response.body().getAddress();
                    edtHo.setText(address.getFirstName());
                    edtTen.setText(address.getLastName());
                    edtSoDienThoai.setText(address.getPhone());
                    edtDiaChi.setText(address.getAddress());
                    if (!address.getIsDefault()) {
                        switchMacDinh.setBackgroundResource(R.drawable.switch_background);
                        switchMacDinh.setTrackDrawable(getResources().getDrawable(R.drawable.switch_background));
                        switchMacDinh.setChecked(false);
                        isMacDinh = false;
                    } else {
                        switchMacDinh.setBackgroundResource(R.drawable.backbackground_boder);
                        switchMacDinh.setTrackDrawable(getResources().getDrawable(R.drawable.backbackground_boder));
                        switchMacDinh.setChecked(true);
                        isMacDinh = true;
                    }
                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {

            }
        });
    }

    private void getAddressFromAdapter(){
        //get address from adapter
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            address = (Address) bundle.getSerializable("address");
            // Tiếp tục xử lý khi address đã được lấy từ bundle
        } else {
            // Xử lý khi bundle là null
            address = new Address();
            address.setId(0);
        }
    }

    private void setClick() {
        //đổi background khi bật tắt switch
        switchMacDinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMacDinh) {
                    switchMacDinh.setBackgroundResource(R.drawable.switch_background);
                    switchMacDinh.setTrackDrawable(getResources().getDrawable(R.drawable.switch_background));
                    isMacDinh = false;
                } else {
                    switchMacDinh.setBackgroundResource(R.drawable.backbackground_boder);
                    switchMacDinh.setTrackDrawable(getResources().getDrawable(R.drawable.backbackground_boder));
                    isMacDinh = true;
                }
            }
        });
        tvLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ho = edtHo.getText().toString();
                String ten = edtTen.getText().toString();
                String sdt = edtSoDienThoai.getText().toString();
                String diaChi = edtDiaChi.getText().toString();
                boolean isDefault = isMacDinh;
                Address newAddress = new Address(address.getId(), ho, ten, sdt, diaChi, isDefault, "", "");
                apiService.addOrUpdateAddress(newAddress, sharedPrefManager.getUser().getId()).enqueue(new Callback<AddressResponse>() {
                    @Override
                    public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                        if (response.isSuccessful()){
                            Intent intent = new Intent(ThemSuaDiaChiActivity.this, DiaChiCuaToiActivity.class);
                            startActivity(intent);
                        } else {
                            Log.e("TAG", "Loi response: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<AddressResponse> call, Throwable t) {
                        Log.e("TAG", "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }


    private void anhXa() {
        switchMacDinh = findViewById(R.id.switchMacDinh);
        edtHo = findViewById(R.id.edtHo);
        edtTen = findViewById(R.id.edtTen);
        edtSoDienThoai = findViewById(R.id.edtSDT);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        tvLuu = findViewById(R.id.tvLuu);
    }
}