package vn.iotstar.cosmeticshopapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Order;
import vn.iotstar.cosmeticshopapp.model.SingleOrderResponse;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;

public class DanhGiaDonHangActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_IMAGE_PICKER = 100;
    private static final String EXTRA_RESULT_IMAGES = "extra_result_images";

    RatingBar rate_danhgia;
    FrameLayout flthemHinhAnh;
    TextView edtCommentDanhGia, tv_soluonghinhanh, tvSubmitDanhGia, tvMaDonHang;
    ImageView imgDanhGia;
    Order order;
    int count;
    List<File> fileList = new ArrayList<>();
    SharedPrefManager sharedPrefManager;
    APIService apiService;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia_don_hang);
        //nhận dữ liệu từ intent
        Bundle bundle = getIntent().getExtras();
        order = (Order) bundle.getSerializable("order");
        anhXa();
        //nếu có đơn hàng thì hiển thị mã đơn hàng
        if (order != null) {
            tvMaDonHang.setText(String.valueOf(order.getId()));

            setClick();
        }
        //nếu không có đơn hàng thì ẩn mã đơn hàng
        else {
            Toast.makeText(this, "Không có đơn hàng", Toast.LENGTH_SHORT).show();
            tvSubmitDanhGia.setText("Quay lại");
            tvSubmitDanhGia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

//
//        count = Integer.parseInt(tv_soluonghinhanh.getText().toString());
//        if (count == 0) {
//            imgDanhGia.setVisibility(View.GONE);
//        }else{
//            imgDanhGia.setVisibility(View.VISIBLE);
//        }

    }

    private void setClick() {
        tvSubmitDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nếu đủ thông tin thì cho phép đánh giá
                String content = edtCommentDanhGia.getText().toString();
                float rate = rate_danhgia.getRating();
                if (rate != 0 && !content.isEmpty()) {
                    progressDialog.show();
                    //đánh giá đơn hàng
                    //chuyển đến trang review
                    //finish
                    List<MultipartBody.Part> imageParts = new ArrayList<>();
                    for (File file : fileList) {
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("reviewImages", file.getName(), requestFile);
                        imageParts.add(body);
                    }
                    RequestBody orderId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(order.getId()));
                    RequestBody rating = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(rate));
                    RequestBody comment = RequestBody.create(MediaType.parse("multipart/form-data"), content);
                    apiService.addReview(orderId, rating, comment, imageParts).enqueue(new Callback<SingleOrderResponse>() {
                        @Override
                        public void onResponse(Call<SingleOrderResponse> call, Response<SingleOrderResponse> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()) {
                                Toast.makeText(DanhGiaDonHangActivity.this, "Đánh giá thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(DanhGiaDonHangActivity.this, "Đánh giá thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SingleOrderResponse> call, Throwable t) {
                            Log.e("TAG", "onFailure: " + t.getMessage());
                        }
                    });
                }


                /*//Chuyển đến trang Review của sản phẩm đầu tiên trong đơn hàng vừa được đánh giá
                Intent intent = new Intent(DanhGiaDonHangActivity.this, ReviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", order.getOrderItems().get(0).getProduct());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();*/

                //nếu không đủ thông tin thì thông báo

            }
        });
        flthemHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    ImagePicker.create(DanhGiaDonHangActivity.this)
                            .limit(5) // Giới hạn số lượng ảnh có thể chọn (ở đây là 5 ảnh)
                            .folderMode(true)
                            .start(REQUEST_CODE_IMAGE_PICKER);
                } else {
                    ActivityCompat.requestPermissions(DanhGiaDonHangActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }
        });
    }

    private void anhXa() {
        rate_danhgia = findViewById(R.id.rate_danhgia);
        edtCommentDanhGia = findViewById(R.id.edtCommentDanhGia);
        imgDanhGia = findViewById(R.id.imgDanhGia);
        tv_soluonghinhanh = findViewById(R.id.tv_soluonghinhanh);
        tvSubmitDanhGia = findViewById(R.id.tvSubmitDanhGia);
        flthemHinhAnh = findViewById(R.id.flthemHinhAnh);
        tvMaDonHang = findViewById(R.id.tvMaDonHang);
        sharedPrefManager = new SharedPrefManager(this);
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang tải...");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE_PICKER && resultCode == RESULT_OK && data != null) {
            List<Image> images = ImagePicker.getImages(data);
            for (Image image : images) {
                Log.d("TAG", "onActivityResult: " + image.getId() + " " + image.getPath());
                File file = new File(image.getPath());
                fileList.add(file);
            }

            // Your code for handling the selected images
        }
    }

}