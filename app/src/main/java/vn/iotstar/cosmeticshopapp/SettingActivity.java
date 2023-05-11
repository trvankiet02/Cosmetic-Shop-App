package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.iotstar.cosmeticshopapp.fragment_home.DanhmucFragment;
import vn.iotstar.cosmeticshopapp.model.User;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;

public class SettingActivity extends AppCompatActivity {
    LinearLayout lnTenUser, lnSoDiaChi, lnQuanLyTaiKhoan, lnViTri, lnNgonNgu, lnTuyChonLienHe;
    LinearLayout lnDanhSachLienHeBiChan, lnXoaBoNhoCache, lnDanhGiaPhanHoi, lnTheoDoiChungToi, lnGioiThieuSHEIN;
    LinearLayout lnDangXuat;
    TextView tvPhienBan, tvTenUser;
    SharedPrefManager sharedPrefManager;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        anhXa();
        getUserFromShared();
        setBtnDangXuat();
    }

    private void setBtnDangXuat() {
        lnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefManager.deleteUser();
                Intent homeIntent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(homeIntent);
            }
        });
    }

    private void getUserFromShared() {
        user = sharedPrefManager.getUser();
        tvTenUser.setText(user.getFirstName() + " " + user.getLastName());
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
    }
}