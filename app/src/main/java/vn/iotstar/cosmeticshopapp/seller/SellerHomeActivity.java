package vn.iotstar.cosmeticshopapp.seller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.model.Store;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;

public class SellerHomeActivity extends AppCompatActivity {
    LinearLayout lnChuaXacNhan, lnDaXacNhan, lnDangVanChuyen, lnDaNhan, lnAllOrder;
    ImageView ivStoreImage;
    TextView tvStoreName, tvStar,tvUserFollow, tvEmail, tv_QuanLySanPham;
    Store store;
    SharedPrefManager sharedPrefManager = new SharedPrefManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        anhXa();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            store = (Store) bundle.getSerializable("store");
            sharedPrefManager.setStoreId(store.getId());
            Log.e("TAG", "onCreate: " + sharedPrefManager.getStoreId());
            getStoreInfo();
        }
        setBtnXyLyDonHang();
        tv_QuanLySanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QuanLySanPhamActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("store", store);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    private void toFragment(Integer fragmentId){
        Intent intent = new Intent(getApplicationContext(), XuLyDonHangSellerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("fragment", fragmentId);
        bundle.putSerializable("store", store);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void setBtnXyLyDonHang() {
        lnAllOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toFragment(0);
            }
        });
        lnChuaXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toFragment(1);
            }
        });
        lnDaXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toFragment(2);
            }
        });
        lnDangVanChuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toFragment(3);
            }
        });
        lnDaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toFragment(4);
            }
        });
    }


    private void getStoreInfo() {
        tvStoreName.setText(store.getName());
        tvEmail.setText(store.getEmail());
        tvStar.setText(String.valueOf(Math.round(store.getRating()*10.0) /10.0));
        tvUserFollow.setText(String.valueOf(store.getUserFollowStores().size() + " Người theo dõi"));
        Glide.with(this).load(store.getStoreImage()).into(ivStoreImage);
    }

    private void anhXa() {
        ivStoreImage = findViewById(R.id.ivStoreImage);
        tvStoreName = findViewById(R.id.tvStoreName);
        tvStar = findViewById(R.id.tvStar);
        tvUserFollow = findViewById(R.id.tvUserFollow);
        tvEmail = findViewById(R.id.tvEmail);
        tv_QuanLySanPham = findViewById(R.id.tv_QuanLySanPham);
        lnChuaXacNhan = findViewById(R.id.lnChuaXacNhan);
        lnDaXacNhan = findViewById(R.id.lnDaXacNhan);
        lnDangVanChuyen = findViewById(R.id.lnDangVanChuyen);
        lnDaNhan = findViewById(R.id.lnDaNhan);
        lnAllOrder = findViewById(R.id.lnAllOrder);
    }
}