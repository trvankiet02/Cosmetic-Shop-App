package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.User;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;

public class StoreActivity extends AppCompatActivity {
    ImageView ivStoreImage;
    TextView tvStoreName, tvEmail, tvStar, tvFollowCount;
    Button btnFollow;
    APIService apiService;
    SharedPrefManager sharedPrefManager;
    TabLayout tabLayout;
    ViewPager viewPager;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        anhXa();
    }

    private void anhXa() {
        ivStoreImage = findViewById(R.id.ivStoreImage);
        tvStoreName = findViewById(R.id.tvStoreName);
        tvEmail = findViewById(R.id.tvEmail);
        tvStar = findViewById(R.id.tvStar);
        tvFollowCount = findViewById(R.id.tvUserFollow);
        btnFollow = findViewById(R.id.btnFollow);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        APIService apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        sharedPrefManager = new SharedPrefManager(this);
        user = sharedPrefManager.getUser();
    }
}