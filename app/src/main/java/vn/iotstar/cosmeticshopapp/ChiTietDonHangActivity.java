package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.Locale;

import vn.iotstar.cosmeticshopapp.adapter.ChiTietDonHangProductAdapter;
import vn.iotstar.cosmeticshopapp.adapter.DonHangAdapter;
import vn.iotstar.cosmeticshopapp.adapter.OrderItemAdapter;
import vn.iotstar.cosmeticshopapp.model.Order;

public class ChiTietDonHangActivity extends AppCompatActivity {
    ImageView img_mautrangthai, storeImage, img_xoa;
    TextView tv_trangthai, tv_madonhang, tv_soluongsanpham, tv_giadonhang, storeName;
    TextView tv_danhgia, tv_huydon, tv_danhan, tv_donvivanchuyen, tv_diachinhanhang;

    ChiTietDonHangProductAdapter chiTietDonHangProductAdapter;
    RecyclerView rcProductOrder;
    NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);
        anhXa();
        // lấy dữ liệu từ adapter
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            order = (Order) bundle.getSerializable("order");
            setChiTietDonHang(order ,Integer.parseInt(order.getStatus().toString()));

        }
    }

    private void setChiTietDonHang(Order o, int trangthai) {
        setTrangThaiTongThe(trangthai);

        tv_madonhang.setText(o.getId().toString());
        storeName.setText(o.getStore().getName());
        Glide.with(getApplicationContext())
                .load(o.getStore().getStoreImage())
                .into(storeImage);
        tv_soluongsanpham.setText(String.valueOf(o.getOrderItems().size()));

        int price = o.getPrice();
        String formattedNumber = formatter.format(price);
        tv_giadonhang.setText(formattedNumber);
        tv_donvivanchuyen.setText(o.getDelivery().getDeliveryName());
        tv_diachinhanhang.setText(o.getAddress());
        tv_soluongsanpham.setText(String.valueOf(o.getOrderItems().size()));

        //truyền sản phẩm vào adapter
        chiTietDonHangProductAdapter = new ChiTietDonHangProductAdapter(getApplicationContext(), o.getOrderItems());
        rcProductOrder.setHasFixedSize(true);
        rcProductOrder.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        rcProductOrder.setAdapter(chiTietDonHangProductAdapter);
        chiTietDonHangProductAdapter.notifyDataSetChanged();
    }


    public void setTrangThaiTongThe(int trangThai){
        //đổi img màu, đổi txt trạng thái, ẩn nút xóa,
        //đơn chờ xác nhận thì có thể Hủy đơn
        //đơn đã xác nhận thì không được hủy
        //đơn đang giao thì không được hủy, nút đã nhận rõ  bấm được
        //đơn giao xong thì sẽ k hủy, mà có nút trả lại, có nút feedback, nút đã nhận biến mất
        //đơn hủy thì có nút xóa đơn
        switch (trangThai) {
            case 0:
                // Xử lý khi trạng thái là hủy
                img_mautrangthai.setBackgroundResource(R.drawable.background_tt_0);
                tv_trangthai.setText("ĐÃ THU HỒI");
                tv_danhan.setVisibility(View.GONE);
                tv_huydon.setVisibility(View.GONE);
                tv_danhgia.setVisibility(View.GONE);
                img_xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            case 1:
                // Xử lý khi trạng thái là chờ xác nhận
                img_mautrangthai.setBackgroundResource(R.drawable.background_tt_1);
                tv_trangthai.setText("CHƯA XÁC NHẬN");
                img_xoa.setVisibility(View.GONE);
                tv_danhan.setVisibility(View.GONE);
                tv_danhgia.setVisibility(View.GONE);
                tv_huydon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            case 2:
                // Xử lý khi trạng thái là đã xác nhận
                img_mautrangthai.setBackgroundResource(R.drawable.background_tt_2);
                tv_trangthai.setText("ĐÃ XÁC NHẬN");
                img_xoa.setVisibility(View.GONE);
                tv_danhan.setVisibility(View.GONE);
                tv_huydon.setVisibility(View.GONE);
                tv_danhgia.setVisibility(View.GONE);
                break;
            case 3:
                // Xử lý khi trạng thái là đang giao
                img_mautrangthai.setBackgroundResource(R.drawable.background_tt_3);
                tv_trangthai.setText("ĐANG GIAO HÀNG");
                tv_danhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                img_xoa.setVisibility(View.GONE);
                tv_danhgia.setVisibility(View.GONE);
                tv_huydon.setVisibility(View.GONE);
                break;
            case 4:
                // Xử lý khi trạng thái là đã nhận
                img_mautrangthai.setBackgroundResource(R.drawable.background_tt_4);
                tv_trangthai.setText("ĐÃ NHẬN HÀNG");
                tv_huydon.setVisibility(View.GONE);
                img_xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                tv_danhgia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ChiTietDonHangActivity.this, DanhGiaDonHangActivity.class);
                        intent.putExtra("order", order);
                        startActivity(intent);
                    }
                });
                tv_danhan.setBackgroundResource(R.drawable.background_xam);
                tv_danhan.setTextColor(ContextCompat.getColor(ChiTietDonHangActivity.this, R.color.grey_dark));
                if (order.getReview() != null){
                    tv_danhgia.setBackgroundResource(R.drawable.background_xam);
                    tv_danhgia.setTextColor(ContextCompat.getColor(ChiTietDonHangActivity.this, R.color.grey_dark));
                    tv_danhgia.setEnabled(false);
                }
                break;
            default:
                break;
        }


    }

    private void anhXa() {
        img_mautrangthai = findViewById(R.id.img_mautrangthai);
        storeImage = findViewById(R.id.storeImage4);
        storeName = findViewById(R.id.storeName4);
        img_xoa = findViewById(R.id.img_xoa);
        tv_trangthai = findViewById(R.id.tv_trangthai);
        tv_madonhang = findViewById(R.id.tv_madonhang);
        tv_soluongsanpham = findViewById(R.id.tv_soluongsanpham);
        tv_giadonhang = findViewById(R.id.tv_giadonhang);
        tv_danhgia = findViewById(R.id.tv_danhgia);
        tv_huydon = findViewById(R.id.tv_huydon);
        tv_danhan = findViewById(R.id.tv_danhan);
        rcProductOrder = findViewById(R.id.rcImageProductOder);
        tv_donvivanchuyen = findViewById(R.id.tv_donvivanchuyen);
        tv_diachinhanhang = findViewById(R.id.tv_diachinhanhang);
    }
}