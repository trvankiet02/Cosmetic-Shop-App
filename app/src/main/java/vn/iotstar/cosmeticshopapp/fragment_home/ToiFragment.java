package vn.iotstar.cosmeticshopapp.fragment_home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import vn.iotstar.cosmeticshopapp.LoginSignupActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.SettingActivity;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;


public class ToiFragment extends Fragment {
    View view;
    ImageView btnProfile;
    TextView txtLogin;
    SharedPrefManager sharedPrefManager;;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_toi, container, false);

        anhXa();
        setBtnProfile();
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
    private void anhXa(){
        txtLogin = view.findViewById(R.id.txtDangNhap);
        sharedPrefManager = new SharedPrefManager(getContext());
        btnProfile = view.findViewById(R.id.btnProfile);
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