package vn.iotstar.cosmeticshopapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import vn.iotstar.cosmeticshopapp.R;


public class LoginFragment extends Fragment {

    EditText edtEmail;
    EditText edtPassword;
    Button btnLogin;
    TextView tvForgotPassword;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        anhXa();

        return view;
    }

    private void anhXa(){
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        tvForgotPassword = (TextView) view.findViewById(R.id.tvForgotPassword);
    }

}