package vn.iotstar.cosmeticshopapp.quanlytaikhoancuatoi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.QuanLyTaiKhoanCuaToiActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.LoginSignupResponse;
import vn.iotstar.cosmeticshopapp.model.User;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;

public class ChangePasswordActivity extends AppCompatActivity {
    APIService apiService;
    SharedPrefManager sharedPrefManager;
    EditText edtCurrentPassword, edtNewPassword, edtConfirmPassword;
    TextInputLayout layoutCurrentPassword, layoutNewPassword, layoutConfirmPassword;
    ProgressDialog progressDialog;
    User user;
    Button btnSave;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        anhXa();
        setBtnSave();
        setBtnBack();
    }

    private void setBtnBack() {
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void setBtnSave() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String currentPassword = edtCurrentPassword.getText().toString().trim();
                String newPassword = edtNewPassword.getText().toString().trim();
                String confirmPassword = edtConfirmPassword.getText().toString().trim();
                checkCurrentPassword(currentPassword);
                checkNewPasswordAndConfirmPassword(newPassword, confirmPassword);
                if (layoutCurrentPassword.getError() == null && layoutNewPassword.getError() == null && layoutConfirmPassword.getError() == null) {
                    apiService.updatePassword(user.getId(), newPassword).enqueue(new Callback<LoginSignupResponse>() {
                        @Override
                        public void onResponse(Call<LoginSignupResponse> call, Response<LoginSignupResponse> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()) {
                                sharedPrefManager.deletePassword();
                                sharedPrefManager.setPassword(newPassword);
                                Intent intent = new Intent(ChangePasswordActivity.this, QuanLyTaiKhoanCuaToiActivity.class);
                                Toast.makeText(ChangePasswordActivity.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginSignupResponse> call, Throwable t) {

                        }
                    });
                } else {
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void checkCurrentPassword(String currentPassword) {
        if (currentPassword.equals("")) {
            layoutCurrentPassword.setError("Vui lòng nhập mật khẩu hiện tại");
            return;
        }
        if (currentPassword.length() < 6) {
            layoutCurrentPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            return;
        }
        if (!currentPassword.equals(sharedPrefManager.getPassword())) {
            layoutCurrentPassword.setError("Mật khẩu hiện tại không đúng");
            return;
        }
        layoutCurrentPassword.setError(null);
    }
    private void checkNewPasswordAndConfirmPassword(String newPassword, String confirmPassword) {
        if (newPassword.equals("")) {
            layoutNewPassword.setError("Vui lòng nhập mật khẩu mới");
            return;
        }
        if (newPassword.length() < 6) {
            layoutNewPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            return;
        }
        if (confirmPassword.equals("")) {
            layoutConfirmPassword.setError("Vui lòng nhập lại mật khẩu mới");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            layoutConfirmPassword.setError("Mật khẩu không trùng khớp");
            return;
        }
        layoutNewPassword.setError(null);
        layoutConfirmPassword.setError(null);
    }
    private void anhXa() {
        edtCurrentPassword = findViewById(R.id.edtCurentPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtRePassword);
        sharedPrefManager = new SharedPrefManager(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang cập nhật ...");
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        layoutCurrentPassword = findViewById(R.id.layoutCurrentPassword);
        layoutNewPassword = findViewById(R.id.layoutNewPassword);
        layoutConfirmPassword = findViewById(R.id.layoutRePassword);
        user = sharedPrefManager.getUser();
        btnSave = findViewById(R.id.btnSave);
    }
}