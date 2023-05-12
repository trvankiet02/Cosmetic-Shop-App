package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

public class XacNhanDatHangActivity extends AppCompatActivity {
    LinearLayout ln_chondiachi,ln_themdiachi;
    TextView tvHoTen, tvSoDienThoai,tvDiaChi;
    TextView tvtongcong1, tvsophieugiamgia, tvtamtinh,tvchietkhau,tvtongcongtienthanhtoan;
    TextView tvtieptucthanhtoan;
    RadioButton rb_giaohangtieuchuan;
    Switch switchdambaovanchuyen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_dat_hang);
        anhXa();
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
    }
}