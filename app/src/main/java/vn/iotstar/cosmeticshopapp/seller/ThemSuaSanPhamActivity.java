package vn.iotstar.cosmeticshopapp.seller;

import static vn.iotstar.cosmeticshopapp.ChiTietSanPhamActivity.REQUEST_CODE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderView;

import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.adapter.SliderProductImageAdapter;
import vn.iotstar.cosmeticshopapp.model.Product;

public class ThemSuaSanPhamActivity extends AppCompatActivity {
    EditText NameProduct_edt, Price_edt,promoprice_edt,Description_edt,Makeof_edt,Color_edt,Makein_edt;
    EditText SoLuong_edt, KichThuoc_edt;
    SliderView sliderProduct;
    FrameLayout flthemHinhAnh;
    TextView tv_soluonghinhanh,Category_textview, Style_textview, tvSubmit;
    Spinner Category_spinner,Stype_spinner;
    SliderProductImageAdapter sliderProductImageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sua_san_pham);
        anhXa();
        //lấy dữ liệu:
        //nếu có dữ liệu thì là sửa, không có dữ liệu thì là thêm
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            Product product = (Product) bundle.getSerializable("product");
            suaSanPham(product);
        }else{
            themSanPham();
        }
    }

    private void themSanPham() {
        tvSubmit.setText("Thêm sản phẩm");
        //xử lý thêm sản phẩm ở đây

        //set Spiner
    }

    private void suaSanPham(Product p) {

        tvSubmit.setText("Cập nhật sản phẩm");
        //xử lý sửa sản phẩm ở đây
        NameProduct_edt.setText(p.getName());
        Price_edt.setText(String.valueOf(p.getPrice()));
        promoprice_edt.setText(String.valueOf(p.getPromotionalPrice()));
        Description_edt.setText(p.getDescription());
        Makeof_edt.setText(p.getMadeOf());
        Color_edt.setText(p.getColor());
        Makein_edt.setText(p.getMadeIn());

        //set Spiner


        //nếu có hình ảnh thì set hình ảnh
        sliderProduct.setVisibility(View.VISIBLE);
        tv_soluonghinhanh.setText(String.valueOf(p.getProductImages().size()));//set số lượng ảnh

        sliderProductImageAdapter = new SliderProductImageAdapter(getApplicationContext(), p.getProductImages(), REQUEST_CODE);
        sliderProduct.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderProduct.setSliderAdapter(sliderProductImageAdapter);

        //tạo list ảnh mới để chọn, nếu có ảnh mới thì thay thế nó


    }


    private void anhXa() {
        NameProduct_edt = findViewById(R.id.NameProduct_edt);
        Price_edt = findViewById(R.id.Price_edt);
        promoprice_edt = findViewById(R.id.promoprice_edt);
        Description_edt = findViewById(R.id.Description_edt);
        Makeof_edt = findViewById(R.id.Makeof_edt);
        Color_edt = findViewById(R.id.Color_edt);
        Makein_edt = findViewById(R.id.Makein_edt);
        sliderProduct = findViewById(R.id.sliderProduct);
        flthemHinhAnh = findViewById(R.id.flthemHinhAnh);
        tv_soluonghinhanh = findViewById(R.id.tv_soluonghinhanh);
        Category_textview = findViewById(R.id.Category_textview);
        Style_textview = findViewById(R.id.Style_textview);
        tvSubmit = findViewById(R.id.tvSubmit);
        Category_spinner = findViewById(R.id.Category_spinner);
        Stype_spinner = findViewById(R.id.Stype_spinner);
        KichThuoc_edt = findViewById(R.id.KichThuoc_edt);
        SoLuong_edt = findViewById(R.id.SoLuong_edt);
    }
}