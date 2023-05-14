package vn.iotstar.cosmeticshopapp.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.adapter.ViewPagerAdapter;
import vn.iotstar.cosmeticshopapp.fragment_xulydonhang_seller.ChuyenChoShipperFragment;
import vn.iotstar.cosmeticshopapp.fragment_xulydonhang_seller.DonDaHuyFragment;
import vn.iotstar.cosmeticshopapp.fragment_xulydonhang_seller.DonHangDaBanFragment;
import vn.iotstar.cosmeticshopapp.fragment_xulydonhang_seller.TatCaDonHangSellerFragment;
import vn.iotstar.cosmeticshopapp.fragment_xulydonhang_seller.XacNhanDonHangFragment;
import vn.iotstar.cosmeticshopapp.fragment_xulydonhang_seller.XacNhanHoanTatFragment;
import vn.iotstar.cosmeticshopapp.model.Store;

public class XuLyDonHangSellerActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter xulydonhangAdapter;
    Store store;
    Integer fragmentId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xu_ly_don_hang_seller);
        AnhXa();
        setTabLayout();
        getFragment();
    }
    private void getFragment() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        fragmentId = (Integer) bundle.getSerializable("fragment");
        store = (Store) bundle.getSerializable("store");

        bundle.putSerializable("store", store);

        if (fragmentId != -1){

            viewPager.setCurrentItem(fragmentId);
        } else {
            viewPager.setCurrentItem(0);
        }
    }

    private void AnhXa() {
        tabLayout = (TabLayout) findViewById(R.id.tlXuLyDonHang);
        viewPager = (ViewPager) findViewById(R.id.vpXuLyDonHang);
        xulydonhangAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        xulydonhangAdapter.addFragment(new TatCaDonHangSellerFragment(), "Tất cả đơn hàng");
        xulydonhangAdapter.addFragment(new XacNhanDonHangFragment(), "Chờ xác nhận");
        xulydonhangAdapter.addFragment(new ChuyenChoShipperFragment(), "Đã xác nhận");
        xulydonhangAdapter.addFragment(new XacNhanHoanTatFragment(), "Đang giao");
        xulydonhangAdapter.addFragment(new DonHangDaBanFragment(), "Đã bán");
        xulydonhangAdapter.addFragment(new DonDaHuyFragment(), "Đã huỷ");

    }
    private void setTabLayout() {
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(xulydonhangAdapter);
        xulydonhangAdapter.notifyDataSetChanged();
    }
}