package vn.iotstar.cosmeticshopapp.quanlytaikhoancuatoi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class AddPhoneActivity extends AppCompatActivity {
    APIService apiService;
    SharedPrefManager sharedPrefManager;
    TextInputLayout layoutPhone;
    EditText edtPhone;
    Button btnSave;
    ImageView btnBack;
    User user;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);
        anhXa();
        setBtnSave();
        setBtnBack();
    }

    private void checkTextInput(String input) {
        if (input.isEmpty()) {
            layoutPhone.setError("Không được để trống");
            return;
        } else if (!input.startsWith("0")) {
            layoutPhone.setError("Số điện thoại phải bắt đầu bằng số 0");
            return;
        } else if (input.length() < 10 || input.length() > 11) {
            layoutPhone.setError("Số điện thoại phải có 10 hoặc 11 số");
            return;
        } else if (!input.matches("^[0-9]*$")) {
            layoutPhone.setError("Số điện thoại không được chứa ký tự");
            return;
        } else {
            layoutPhone.setError(null);
            return;
        }
    }
    private void setBtnBack() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setBtnSave() {
        edtPhone.setText(user.getPhone());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String phone = edtPhone.getText().toString().trim();
                checkTextInput(phone);
                if (layoutPhone.getError() == null){
                    apiService.updatePhone(user.getId(), phone).enqueue(new Callback<LoginSignupResponse>() {
                        @Override
                        public void onResponse(Call<LoginSignupResponse> call, Response<LoginSignupResponse> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()){
                                sharedPrefManager.deleteUser();
                                sharedPrefManager.setUser(response.body().getBody());
                                Toast.makeText(AddPhoneActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent profilePage = new Intent(AddPhoneActivity.this, QuanLyTaiKhoanCuaToiActivity.class);
                                startActivity(profilePage);
                            } else {
                                Log.e("TAG", "onResponse: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginSignupResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Log.e("TAG", "onFailure: " + t.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void anhXa() {
        layoutPhone = findViewById(R.id.layoutPhone);
        edtPhone = findViewById(R.id.edtPhone);
        btnSave = findViewById(R.id.btnSave);
        sharedPrefManager = new SharedPrefManager(this);
        user = sharedPrefManager.getUser();
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang cập nhật...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        btnBack = findViewById(R.id.btnBack);
    }
}