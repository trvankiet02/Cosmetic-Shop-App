package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import vn.iotstar.cosmeticshopapp.adapter.ViewPagerAdapter;
import vn.iotstar.cosmeticshopapp.fragment_xylydonhang.ChoXacNhanFragment;
import vn.iotstar.cosmeticshopapp.fragment_xylydonhang.DaHuyFragment;
import vn.iotstar.cosmeticshopapp.fragment_xylydonhang.DaNhanFragment;
import vn.iotstar.cosmeticshopapp.fragment_xylydonhang.DaXacNhanFragment;
import vn.iotstar.cosmeticshopapp.fragment_xylydonhang.DangGiaoFragment;
import vn.iotstar.cosmeticshopapp.fragment_xylydonhang.TatCaDonHangFragment;

public class XuLyDonHangActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter xulydonhangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xu_ly_don_hang);
        AnhXa();
        setTabLayout();
    }
    private void AnhXa() {
        tabLayout = (TabLayout) findViewById(R.id.tlXuLyDonHang);
        viewPager = (ViewPager) findViewById(R.id.vpXuLyDonHang);
        xulydonhangAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        xulydonhangAdapter.addFragment(new TatCaDonHangFragment(), "Tất cả đơn hàng");
        xulydonhangAdapter.addFragment(new ChoXacNhanFragment(), "Chờ xác nhận");
        xulydonhangAdapter.addFragment(new DaXacNhanFragment(), "Đã xác nhận");
        xulydonhangAdapter.addFragment(new DangGiaoFragment(), "Đang giao");
        xulydonhangAdapter.addFragment(new DaNhanFragment(), "Đã nhận");
        xulydonhangAdapter.addFragment(new DaHuyFragment(), "Đã hủy");
    }
    private void setTabLayout() {
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(xulydonhangAdapter);
        xulydonhangAdapter.notifyDataSetChanged();
    }
}