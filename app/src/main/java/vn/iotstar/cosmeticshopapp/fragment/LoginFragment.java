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
    private void configEdtEmail(){
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtEmail.getText().toString().equals("")){
                    return ;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}