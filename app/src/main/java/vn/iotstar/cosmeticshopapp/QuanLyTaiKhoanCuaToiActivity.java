package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import vn.iotstar.cosmeticshopapp.model.User;
import vn.iotstar.cosmeticshopapp.quanlytaikhoancuatoi.AddPhoneActivity;
import vn.iotstar.cosmeticshopapp.quanlytaikhoancuatoi.ChangeAvataActivity;
import vn.iotstar.cosmeticshopapp.quanlytaikhoancuatoi.ChangeEmailActivity;
import vn.iotstar.cosmeticshopapp.quanlytaikhoancuatoi.ChangePasswordActivity;
import vn.iotstar.cosmeticshopapp.quanlytaikhoancuatoi.ChangeUsernameActivity;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;

public class QuanLyTaiKhoanCuaToiActivity extends AppCompatActivity {
    ImageView avata, img_change_avata;
    LinearLayout lnEmail, lnSoDienThoai, lnDoiMatKhau, lnXoaTaiKhoan,lnTaiXuong;
    TextView tvSoDienThoai,tvEmail, tvNameUser;
    SharedPrefManager sharedPrefManager;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_tai_khoan_cua_toi);
        anhXa();
        loadData();
        setClick();
    }

    private void loadData() {
        user = sharedPrefManager.getUser();
        if (user.getFirstName() == null && user.getLastName() == null) {
            tvNameUser.setText(user.getEmail());
        } else {
            tvNameUser.setText(user.getFirstName() + " " + user.getLastName());
        }
        tvEmail.setText(user.getEmail());
        if (user.getPhone() != null){
            tvSoDienThoai.setText(user.getPhone());
        }
        Glide.with(this).load(user.getProfileImage()).into(avata);
    }

    private void setClick() {
        lnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyTaiKhoanCuaToiActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        img_change_avata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyTaiKhoanCuaToiActivity.this, ChangeAvataActivity.class);
                startActivity(intent);
            }
        });
        lnSoDienThoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyTaiKhoanCuaToiActivity.this, AddPhoneActivity.class);
                startActivity(intent);
            }
        });
        /*lnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyTaiKhoanCuaToiActivity.this, ChangeEmailActivity.class);
                startActivity(intent);
            }
        });*/
        tvNameUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLyTaiKhoanCuaToiActivity.this, ChangeUsernameActivity.class);
                startActivity(intent);
            }
        });
    }

    private void anhXa() {
        avata = findViewById(R.id.avata);
        img_change_avata = findViewById(R.id.img_change_avata);
        lnEmail = findViewById(R.id.lnEmail);
        lnSoDienThoai = findViewById(R.id.lnSoDienThoai);
        lnDoiMatKhau = findViewById(R.id.lnDoiMatKhau);
        lnXoaTaiKhoan = findViewById(R.id.lnXoaTaiKhoan);
        lnTaiXuong = findViewById(R.id.lnTaiXuong);
        tvSoDienThoai = findViewById(R.id.tvSoDienThoai);
        tvEmail = findViewById(R.id.tvEmail);
        tvNameUser = findViewById(R.id.tvNameUser);
        sharedPrefManager = new SharedPrefManager(this);
    }
}