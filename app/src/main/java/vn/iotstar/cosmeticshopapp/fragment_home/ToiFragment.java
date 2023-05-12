package vn.iotstar.cosmeticshopapp.fragment_home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import vn.iotstar.cosmeticshopapp.LoginSignupActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.SettingActivity;
import vn.iotstar.cosmeticshopapp.XuLyDonHangActivity;
import vn.iotstar.cosmeticshopapp.fragment_xylydonhang.ChoXacNhanFragment;
import vn.iotstar.cosmeticshopapp.fragment_xylydonhang.DaHuyFragment;
import vn.iotstar.cosmeticshopapp.fragment_xylydonhang.DaNhanFragment;
import vn.iotstar.cosmeticshopapp.fragment_xylydonhang.DaXacNhanFragment;
import vn.iotstar.cosmeticshopapp.fragment_xylydonhang.DangGiaoFragment;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;


public class ToiFragment extends Fragment {
    View view;
    ImageView btnProfile;
    TextView txtLogin;
    SharedPrefManager sharedPrefManager;
    LinearLayout lnChuaXacNhan, lnDaXacNhan, lnDangVanChuyen, lnDaNhan, lnDaHuy;
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
                    Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), LoginSignupActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            txtLogin.setText("Xin chào " + sharedPrefManager.getUser().getFirstName() + " " + sharedPrefManager.getUser().getLastName());
        }
        return view;
    }

    private void setBtnXyLyDonHang() {
        lnChuaXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), XuLyDonHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void anhXa(){
        txtLogin = view.findViewById(R.id.txtDangNhap);
        sharedPrefManager = new SharedPrefManager(getContext());
        btnProfile = view.findViewById(R.id.btnProfile);
        lnChuaXacNhan = view.findViewById(R.id.lnChuaXacNhan);
        lnDaXacNhan = view.findViewById(R.id.lnDaXacNhan);
        lnDangVanChuyen = view.findViewById(R.id.lnDangVanChuyen);
        lnDaNhan = view.findViewById(R.id.lnDaNhan);
        lnDaHuy = view.findViewById(R.id.lnDaHuy);
    }
    private void setBtnProfile(){
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }

}