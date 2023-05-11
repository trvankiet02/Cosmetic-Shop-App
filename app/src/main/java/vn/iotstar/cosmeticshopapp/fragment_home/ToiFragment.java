package vn.iotstar.cosmeticshopapp.fragment_home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import vn.iotstar.cosmeticshopapp.LoginSignupActivity;
import vn.iotstar.cosmeticshopapp.R;

public class ToiFragment extends Fragment {
    View view;
    TextView txtTenUser;
    LinearLayout lnChuaLogin, lnDaLogin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_toi, container, false);
        anhXa();
        setOneClick();
        checkLogin();
        return view;
    }

    private void checkLogin() {
        if (true){
            lnChuaLogin.setVisibility(View.GONE);
            lnDaLogin.setVisibility(View.VISIBLE);
            txtTenUser.setText("Nguyễn Văn A");
        }else {
            lnChuaLogin.setVisibility(View.VISIBLE);
            lnDaLogin.setVisibility(View.GONE);
        }
    }

    private void setOneClick() {
        lnChuaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginSignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void anhXa(){
        lnChuaLogin = view.findViewById(R.id.lnChualogin);
        lnDaLogin = view.findViewById(R.id.lnDalogin);
        txtTenUser = view.findViewById(R.id.txtTenUser);
    }
}