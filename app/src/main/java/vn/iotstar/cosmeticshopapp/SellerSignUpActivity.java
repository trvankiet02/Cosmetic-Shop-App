package vn.iotstar.cosmeticshopapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Store;
import vn.iotstar.cosmeticshopapp.model.StoreResponse;
import vn.iotstar.cosmeticshopapp.quanlytaikhoancuatoi.ChangeAvataActivity;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;
import vn.iotstar.cosmeticshopapp.util.RealPathUtil;

public class SellerSignUpActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_IMAGE_PICKER = 100;
    EditText edtStoreName, edtStoreEmail, edtStorePhone;
    ImageView btnBack, ivStoreImage;
    Button btnSelectImage, btnSignUp;
    APIService apiService;
    SharedPrefManager sharedPrefManager;
    Image image;
    ProgressDialog progressDialog;
    TextInputLayout tilStoreName, tilStoreEmail, tilStorePhone;
    Uri uri;
    String path;
    RequestOptions requestOptions;
    Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_sign_up);

        anhXa();
        setBtnSignUp();
        setBtnBack();
        setBtnSelectImage();
    }
    private void checkInput(String storeName, String storeEmail, String storePhone){
        if (storeName.isEmpty()){
            tilStoreName.setError("Tên cửa hàng không được để trống");
            tilStoreName.requestFocus();
            return;
        } else {
            tilStoreName.setError(null);
        }
        if (storeEmail.isEmpty()){
            tilStoreEmail.setError("Email không được để trống");
            tilStoreEmail.requestFocus();
            return;
        } else {
            tilStoreEmail.setError(null);
        }

        if (storePhone.isEmpty()){
            tilStorePhone.setError("Số điện thoại không được để trống");
            tilStorePhone.requestFocus();
            return;
        } else {
            tilStorePhone.setError(null);
        }
    }

    private void setBtnSignUp(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String storeName = edtStoreName.getText().toString().trim();
                String storeEmail = edtStoreEmail.getText().toString().trim();
                String storePhone = edtStorePhone.getText().toString().trim();
                checkInput(storeName, storeEmail, storePhone);
                if (tilStoreEmail.getError() == null && tilStoreName.getError() == null && tilStorePhone.getError() == null){
                    progressDialog.setMessage("Đang đăng ký...");
                    progressDialog.show();
                    File file = new File(path);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
                    RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), storeName);
                    RequestBody email = RequestBody.create(MediaType.parse("multipart/form-data"), storeEmail);
                    RequestBody phone = RequestBody.create(MediaType.parse("multipart/form-data"), storePhone);
                    RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(sharedPrefManager.getUser().getId()));

                    apiService.storeSignup(id, name, email, phone, body).enqueue(new Callback<StoreResponse>() {
                        @Override
                        public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()){
                                store = response.body().getBody();
                                Toast.makeText(SellerSignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SellerSignUpActivity.this, SellerHomeActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SellerSignUpActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<StoreResponse> call, Throwable t) {
                            Log.e("TAG", "onFailure: " + t.getMessage());
                        }
                    });
                }
            }
        });
    }
    private void setBtnSelectImage(){
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 10);
                } else {
                    ActivityCompat.requestPermissions(SellerSignUpActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
            }
        });
    }

    private void anhXa(){
        edtStoreName = findViewById(R.id.edtStoreName);
        edtStoreEmail = findViewById(R.id.edtStoreEmail);
        edtStorePhone = findViewById(R.id.edtStorePhone);
        btnBack = findViewById(R.id.btnBack);
        ivStoreImage = findViewById(R.id.ivStoreImage);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnSignUp = findViewById(R.id.btnSignUp);
        tilStoreName = findViewById(R.id.tilStoreName);
        tilStoreEmail = findViewById(R.id.tilStoreEmail);
        tilStorePhone = findViewById(R.id.tilStorePhone);
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        sharedPrefManager = new SharedPrefManager(this);
        progressDialog = new ProgressDialog(this);
        requestOptions = RequestOptions.circleCropTransform();
    }

    private void setBtnBack(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            Context context = SellerSignUpActivity.this;
            path = RealPathUtil.getRealPath(context, uri);
            //bitmap = BitmapFactory.decodeFile(path);
            //ivAvatar.setImageURI(uri);
            Glide.with(context)
                    .load(uri)
                    .apply(requestOptions)
                    .into(ivStoreImage);
        }
    }
}