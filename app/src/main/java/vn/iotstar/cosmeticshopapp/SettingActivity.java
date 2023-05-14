package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.fragment_home.DanhmucFragment;
import vn.iotstar.cosmeticshopapp.model.Store;
import vn.iotstar.cosmeticshopapp.model.StoreResponse;
import vn.iotstar.cosmeticshopapp.model.User;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;

public class SettingActivity extends AppCompatActivity {
    LinearLayout lnTenUser, lnSoDiaChi, lnQuanLyTaiKhoan, lnViTri, lnNgonNgu, lnTuyChonLienHe;
    LinearLayout lnDanhSachLienHeBiChan, lnXoaBoNhoCache, lnDanhGiaPhanHoi, lnTheoDoiChungToi, lnGioiThieuSHEIN;
    LinearLayout lnDangXuat;
    TextView tvPhienBan, tvTenUser;
    SharedPrefManager sharedPrefManager;
    ImageView btnBack;
    User user;
    APIService apiService;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        anhXa();
        getUserFromShared();
        setBtnDangXuat();
        setBtnBack();
    }

    private void setBtnBack() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }

    private void setBtnDangXuat() {
        lnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefManager.deleteUser();
                sharedPrefManager.deletePassword();
                Intent homeIntent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(homeIntent);
            }
        });
    }

    private void getUserFromShared() {
        user = sharedPrefManager.getUser();
        if (user.getFirstName() == null && user.getLastName() == null) {
            tvTenUser.setText(user.getEmail());
        } else {
            tvTenUser.setText(user.getFirstName() + " " + user.getLastName());
        }
        setClick();
    }

    private void setClick() {
        lnSoDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, DiaChiCuaToiActivity.class);
                startActivity(intent);
            }
        });
        lnQuanLyTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, QuanLyTaiKhoanCuaToiActivity.class);
                startActivity(intent);
            }
        });
        lnTuyChonLienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Đang kiểm tra...");
                progressDialog.show();
                apiService.getStoreByUserId(sharedPrefManager.getUser().getId()).enqueue(new Callback<StoreResponse>() {
                    @Override
                    public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(SettingActivity.this, SellerHomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("store", response.body().getBody());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            dialog.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StoreResponse> call, Throwable t) {
                        Log.e("TAG", "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }

    private void anhXa() {
        tvPhienBan = findViewById(R.id.tvPhienBan);
        tvTenUser = findViewById(R.id.tvTenUser);
        lnTenUser = findViewById(R.id.lnTenUser);

        lnSoDiaChi = findViewById(R.id.lnSoDiaChi);
        lnQuanLyTaiKhoan = findViewById(R.id.lnQuanLyTaiKhoan);
        lnViTri = findViewById(R.id.lnViTri);
        lnNgonNgu = findViewById(R.id.lnNgonNgu);
        lnTuyChonLienHe = findViewById(R.id.lnTuyChonLienHe);
        lnDanhSachLienHeBiChan = findViewById(R.id.lnDanhSachLienHeBiChan);
        lnXoaBoNhoCache = findViewById(R.id.lnXoaBoNhoCache);
        lnDanhGiaPhanHoi = findViewById(R.id.lnDanhGiaPhanHoi);
        lnTheoDoiChungToi = findViewById(R.id.lnTheoDoiChungToi);
        lnGioiThieuSHEIN = findViewById(R.id.lnGioiThieuSHEIN);
        lnDangXuat = findViewById(R.id.lnDangXuat);
        sharedPrefManager = new SharedPrefManager(this);
        user = new User();
        btnBack = findViewById(R.id.btnBack);
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        progressDialog = new ProgressDialog(this);
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Bạn không có cửa hàng");
        builder.setMessage("Bạn có muốn tạo cửa hàng không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(SettingActivity.this, SellerSignUpActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog = builder.create();
    }
}