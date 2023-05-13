package vn.iotstar.cosmeticshopapp.quanlytaikhoancuatoi;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.QuanLyTaiKhoanCuaToiActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.LoginSignupResponse;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;
import vn.iotstar.cosmeticshopapp.util.RealPathUtil;

public class ChangeAvataActivity extends AppCompatActivity {
    Button btnSelectImage, btnSave;
    RoundedImageView ivAvatar;
    APIService apiService;
    SharedPrefManager sharedPrefManager;
    ProgressDialog mProgressDialog;
    Uri uri;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_change_avata);


        anhXa();
        loadAvatar();
        clickListeners();
    }
    private void clickListeners() {
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
                    ActivityCompat.requestPermissions(ChangeAvataActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateImages(sharedPrefManager.getUser().getId());
            }
        });

    }
    private void updateImages(int userId) {
        mProgressDialog.show();
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("images", file.getName(), requestFile);

        RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(userId));
        Log.d("TAG", "updateImages: " + userId);
        apiService.updateAvatar(userId, body).enqueue(new Callback<LoginSignupResponse>() {
            @Override
            public void onResponse(Call<LoginSignupResponse> call, Response<LoginSignupResponse> response) {
                mProgressDialog.dismiss();
                if (response.isSuccessful()) {
                    sharedPrefManager.deleteUser();
                    sharedPrefManager.setUser(response.body().getBody());
                    Toast.makeText(ChangeAvataActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent profilePage = new Intent(ChangeAvataActivity.this, QuanLyTaiKhoanCuaToiActivity.class);
                    startActivity(profilePage);
                } else {
                    Log.e("TAG", "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginSignupResponse> call, Throwable t) {
                mProgressDialog.dismiss();
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    private void loadAvatar() {
        Log.e("TAG", "loadAvatar: " + sharedPrefManager.getUser().getProfileImage());
        Glide.with(this).load(sharedPrefManager.getUser().getProfileImage()).into(ivAvatar);
    }

    private void anhXa(){
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSelectImage = (Button) findViewById(R.id.btnSelectImage);
        ivAvatar = findViewById(R.id.ivAvatar);
        sharedPrefManager = new SharedPrefManager(this);
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        mProgressDialog = new ProgressDialog(ChangeAvataActivity.this);
        mProgressDialog.setMessage("Please waiting upload....");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            Context context = ChangeAvataActivity.this;
            path = RealPathUtil.getRealPath(context, uri);
            //bitmap = BitmapFactory.decodeFile(path);
            ivAvatar.setImageURI(uri);

        }
    }
}