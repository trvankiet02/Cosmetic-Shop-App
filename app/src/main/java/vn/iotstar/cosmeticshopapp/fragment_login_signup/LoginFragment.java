package vn.iotstar.cosmeticshopapp.fragment_login_signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.MainActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.LoginSignupResponse;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;


public class LoginFragment extends Fragment {
    public static final String TAG = LoginFragment.class.getName();
    EditText edtEmail;
    EditText edtPassword;
    Button btnLogin;
    TextView tvForgotPassword;
    ProgressDialog progressDialog;
    View view;
    APIService apiService;
    SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        anhXa();
        setBtnLogin();
        return view;
    }


    private void setBtnLogin(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                String email = edtEmail.getText().toString().trim(); //trim() xoa khoang trang dau va cuoi
                String password = edtPassword.getText().toString().trim();

                apiService.login(email, password).enqueue(new Callback<LoginSignupResponse>() {
                    @Override
                    public void onResponse(Call<LoginSignupResponse> call, Response<LoginSignupResponse> response) {
                        if (response.isSuccessful()){
                            sharedPrefManager.setUser(response.body().getBody());
                            Intent mainActivity = new Intent(getContext(), MainActivity.class);
                            progressDialog.dismiss();
                            startActivity(mainActivity);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginSignupResponse> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }

                });

            }
        });
    }
    private void anhXa(){
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        tvForgotPassword = (TextView) view.findViewById(R.id.tvForgotPassword);
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        sharedPrefManager = new SharedPrefManager(getContext());
        progressDialog = new ProgressDialog(getContext());
        setProgressDialog();

    }
    private void setProgressDialog(){
        progressDialog.setTitle("Đang đăng nhập");
        progressDialog.setMessage("Làm ơn hãy đợi trong giây lát...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
}