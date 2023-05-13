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

public class ChangeUsernameActivity extends AppCompatActivity {
    APIService apiService;
    SharedPrefManager sharedPrefManager;
    User user;
    Button btnSave;
    EditText edtFirstName, edtLastName;
    TextInputLayout layoutFirstName, layoutLastName;
    ProgressDialog progressDialog;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);
        anhXa();
        setBtnSave();
        setBtnBack();
    }

    private void setBtnBack() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setBtnSave(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String firstName = edtFirstName.getText().toString().trim();
                String lastName = edtLastName.getText().toString().trim();
                checkFirstNameAndLastName(firstName, lastName);
                if (layoutFirstName.getError() == null && layoutLastName.getError() == null){
                    apiService.updateName(user.getId(), firstName, lastName).enqueue(new Callback<LoginSignupResponse>() {
                        @Override
                        public void onResponse(Call<LoginSignupResponse> call, Response<LoginSignupResponse> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()){
                                sharedPrefManager.deleteUser();
                                sharedPrefManager.setUser(response.body().getBody());
                                Intent intent = new Intent(ChangeUsernameActivity.this, QuanLyTaiKhoanCuaToiActivity.class);
                                startActivity(intent);
                                Toast.makeText(ChangeUsernameActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ChangeUsernameActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginSignupResponse> call, Throwable t) {
                            Log.e("TAG", "onFailure: " + t.getMessage() );
                        }
                    });
                } else {
                    progressDialog.dismiss();
                }
            }
        });
    }
    private void checkFirstNameAndLastName(String firstName, String lastName){
        if (firstName.isEmpty()){
            layoutFirstName.setError("Vui lòng nhập họ");
            return;
        } else {
            layoutFirstName.setError(null);
        }
        if (lastName.isEmpty()){
            layoutLastName.setError("Vui lòng nhập tên");
            return;
        } else {
            layoutLastName.setError(null);
        }
        if (firstName.matches(".*\\d.*")){
            layoutFirstName.setError("Họ không được chứa số");
            return;
        } else {
            layoutFirstName.setError(null);
        }
        if (lastName.matches(".*\\d.*")){
            layoutLastName.setError("Tên không được chứa số");
            return;
        } else {
            layoutLastName.setError(null);
        }
    }
    private void anhXa(){
        btnSave = findViewById(R.id.btnSave);
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        layoutFirstName = findViewById(R.id.layoutFirstName);
        layoutLastName = findViewById(R.id.layoutLastName);
        sharedPrefManager = new SharedPrefManager(this);
        user = sharedPrefManager.getUser();
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);

        if (user.getFirstName() != null){
            edtFirstName.setText(user.getFirstName());
        }
        if (user.getLastName() != null){
            edtLastName.setText(user.getLastName());
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang cập nhật...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        btnBack = findViewById(R.id.btnBack);
    }
}