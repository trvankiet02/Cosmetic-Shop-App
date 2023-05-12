package vn.iotstar.cosmeticshopapp.fragment_home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.Serializable;

import vn.iotstar.cosmeticshopapp.LoginSignupActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.SettingActivity;
import vn.iotstar.cosmeticshopapp.adapter.ViewPagerAdapter;
import vn.iotstar.cosmeticshopapp.fragment_product.FavouritedProductFragment;
import vn.iotstar.cosmeticshopapp.fragment_product.RecentProductFragment;
import vn.iotstar.cosmeticshopapp.XuLyDonHangActivity;
import vn.iotstar.cosmeticshopapp.fragment_xylydonhang.ChoXacNhanFragment;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;


public class ToiFragment extends Fragment {
    View view;
    ImageView btnProfile;
    TextView txtLogin;
    SharedPrefManager sharedPrefManager;
    TabLayout tabLayout;
    ViewPager vpProduct;
    ViewPagerAdapter productAdapter;
    LinearLayout lnChuaXacNhan, lnDaXacNhan, lnDangVanChuyen, lnDaNhan, lnDaHuy, lnAllOrder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_toi, container, false);

        anhXa();
        setBtnProfile();
        setBtnXyLyDonHang();
        if (sharedPrefManager.getUser().getId() == -1){
            txtLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), LoginSignupActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            if (sharedPrefManager.getUser().getFirstName() == null && sharedPrefManager.getUser().getLastName() == null){
                txtLogin.setText("Xin chào " + sharedPrefManager.getUser().getEmail());
            } else {
                txtLogin.setText("Xin chào " + sharedPrefManager.getUser().getFirstName() + " " + sharedPrefManager.getUser().getLastName());
            }
        }
        return view;
    }
    private void toFragment(Integer fragmentId){
        Intent intent = new Intent(getContext(), XuLyDonHangActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("fragment", fragmentId);
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
        lnDaHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toFragment(5);
            }
        });
    }


    private void anhXa(){
        txtLogin = view.findViewById(R.id.txtDangNhap);
        sharedPrefManager = new SharedPrefManager(getContext());
        btnProfile = view.findViewById(R.id.btnProfile);
        tabLayout = view.findViewById(R.id.tabLayout);
        vpProduct = view.findViewById(R.id.vpProduct);
        productAdapter = new ViewPagerAdapter(getChildFragmentManager(), ViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        productAdapter.addFragment(new FavouritedProductFragment(), "Danh sách yêu thích");
        productAdapter.addFragment(new RecentProductFragment(), "Đã xem gần đây");
        tabLayout.setupWithViewPager(vpProduct);
        vpProduct.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
        lnChuaXacNhan = view.findViewById(R.id.lnChuaXacNhan);
        lnDaXacNhan = view.findViewById(R.id.lnDaXacNhan);
        lnDangVanChuyen = view.findViewById(R.id.lnDangVanChuyen);
        lnDaNhan = view.findViewById(R.id.lnDaNhan);
        lnDaHuy = view.findViewById(R.id.lnDaHuy);
        lnAllOrder = view.findViewById(R.id.lnAllOrder);
    }
    private void setBtnProfile(){
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPrefManager.getUser().getId() == -1){
                    Toast.makeText(getContext(), "Vui lòng đăng nhập để thực hiện dịch vụ này", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), LoginSignupActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), SettingActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}