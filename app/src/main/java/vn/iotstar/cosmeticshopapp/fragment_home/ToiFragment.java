package vn.iotstar.cosmeticshopapp.fragment_home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import vn.iotstar.cosmeticshopapp.LoginSignupActivity;
import vn.iotstar.cosmeticshopapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToiFragment extends Fragment {
    View view;
    TextView txtLogin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_toi, container, false);

        anhXa();
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), LoginSignupActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void anhXa(){
        txtLogin = view.findViewById(R.id.txtDangNhap);
    }
}