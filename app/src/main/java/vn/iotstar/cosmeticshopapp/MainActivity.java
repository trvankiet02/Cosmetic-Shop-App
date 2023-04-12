package vn.iotstar.cosmeticshopapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

import vn.iotstar.cosmeticshopapp.adapter.ViewPagerAdapter;
import vn.iotstar.cosmeticshopapp.fragment_home.DanhmucFragment;
import vn.iotstar.cosmeticshopapp.fragment_home.GalsFragment;
import vn.iotstar.cosmeticshopapp.fragment_home.MoiFragment;
import vn.iotstar.cosmeticshopapp.fragment_home.MuasamFragment;
import vn.iotstar.cosmeticshopapp.fragment_home.ToiFragment;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    ViewPagerAdapter viewPagerAdapter;
    Map<Integer, Integer> menuItemMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        viewPager.setAdapter(viewPagerAdapter);
        setBottomNavigationView();
    }
    private void AnhXa(){
        //component
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //adapter
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.addFragment(new MuasamFragment(), "Mua sắm");
        viewPagerAdapter.addFragment(new DanhmucFragment(), "Danh mục");
        viewPagerAdapter.addFragment(new MoiFragment(), "Mới");
        viewPagerAdapter.addFragment(new GalsFragment(), "Bộ sưu tập");
        viewPagerAdapter.addFragment(new ToiFragment(), "Tôi");
        //map menu item
        menuItemMap = new HashMap<>();
        menuItemMap.put(R.id.muasam, 0);
        menuItemMap.put(R.id.danhmuc, 1);
        menuItemMap.put(R.id.moi, 2);
        menuItemMap.put(R.id.gals, 3);
        menuItemMap.put(R.id.toi, 4);
    }

    private void setBottomNavigationView(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                viewPager.setCurrentItem(menuItemMap.get(item.getItemId()));
                return true;
            }
        });
    }


}