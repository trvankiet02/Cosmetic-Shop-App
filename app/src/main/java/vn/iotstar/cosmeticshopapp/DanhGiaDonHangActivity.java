package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import vn.iotstar.cosmeticshopapp.model.Order;

public class DanhGiaDonHangActivity extends AppCompatActivity {
    RatingBar rate_danhgia;
    FrameLayout flthemHinhAnh;
    TextView edtCommentDanhGia, tv_soluonghinhanh, tvSubmitDanhGia, tvMaDonHang;
    ImageView imgDanhGia;
    Order order;
    int count;

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

                //Chuyển đến trang Review của sản phẩm đầu tiên trong đơn hàng vừa được đánh giá
                Intent intent = new Intent(DanhGiaDonHangActivity.this, ReviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", order.getOrderItems().get(0).getProduct());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

                //nếu không đủ thông tin thì thông báo

            }
        });
        flthemHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chọn hình ảnh từ thư viện điện thoại, chọn xong nhớ cập nhật số lượng hình ảnh,
                // hiển thị hình ảnh imgDanhGia.setVisibility(View.VISIBLE);
                // set cho hình ảnh imgDanhGia bằng hình ảnh vừa chọn

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
    }
}