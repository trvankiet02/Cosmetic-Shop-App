package vn.iotstar.cosmeticshopapp.fragment_login_signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.LoginSignupActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.LoginSignupResponse;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;

public class SignupFragment extends Fragment {
    EditText edtEmail, edtPassword, edtRePassword;
    Button btnSignup;
    View view;
    APIService apiService;
    ProgressDialog progressDialog;
    TextInputLayout passwordLayout, emailLayout, rePasswordLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup, container, false);

        anhXa();
        setBtnSignup();

        return view;
    }

    private void anhXa() {
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPassword = view.findViewById(R.id.edtPassword);
        edtRePassword = view.findViewById(R.id.edtRePassword);
        btnSignup = view.findViewById(R.id.btnSignup);
        passwordLayout = view.findViewById(R.id.passwordLayout);
        emailLayout = view.findViewById(R.id.emailLayout);
        rePasswordLayout = view.findViewById(R.id.rePasswordLayout);
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        progressDialog = new ProgressDialog(getContext());
        setProgressDialog();
    }

    private void setProgressDialog() {
        progressDialog.setTitle("Đang đăng ký");
        progressDialog.setMessage("Làm ơn hãy đợi trong giây lát...");
        //progressDialog.setCancelable(false);
        //progressDialog.setCanceledOnTouchOutside(false);
    }

    private void setBtnSignup() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String email = edtEmail.getText().toString().trim();
                if (email.isEmpty()) {
                    emailLayout.setError("Email không được để trống");
                    progressDialog.dismiss();
                    return;
                } else if (Patterns.EMAIL_ADDRESS.matcher(email).matches() == false) {
                    emailLayout.setError("Địa chỉ Email không hợp lệ");
                    progressDialog.dismiss();
                    return;
                } else {
                    emailLayout.setError(null);
                }
                String password = edtPassword.getText().toString().trim();
                String rePassword = edtRePassword.getText().toString().trim();
                if (password.isEmpty()) {
                    passwordLayout.setError("Mật khẩu không được để trống");
                    progressDialog.dismiss();
                    return;
                } else if (password.length() < 6) {
                    passwordLayout.setError("Mật khẩu phải có ít nhất 6 ký tự");
                    progressDialog.dismiss();
                    return;
                } else {
                    passwordLayout.setError(null);
                }
                if (rePassword.isEmpty()) {
                    rePasswordLayout.setError("Mật khẩu không được để trống");
                    progressDialog.dismiss();
                    return;
                } else if (rePassword.length() < 6) {
                    rePasswordLayout.setError("Mật khẩu phải có ít nhất 6 ký tự");
                    progressDialog.dismiss();
                    return;
                } else if (rePassword.equals(password) == false) {
                    rePasswordLayout.setError("Mật khẩu không khớp");
                    progressDialog.dismiss();
                    return;
                } else {
                    rePasswordLayout.setError(null);
                }
                apiService.signup(email, password, rePassword).enqueue(new Callback<LoginSignupResponse>() {
                    @Override
                    public void onResponse(Call<LoginSignupResponse> call, Response<LoginSignupResponse> response) {
                        if (response.isSuccessful()){
                            progressDialog.dismiss();
                            Intent intent = new Intent(getContext(), LoginSignupActivity.class);
                            startActivity(intent);

                        } else {
                            Log.e("TAG", "Error on response: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginSignupResponse> call, Throwable t) {
                        Log.e("TAG", "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }
}