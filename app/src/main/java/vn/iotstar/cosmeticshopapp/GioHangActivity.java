package vn.iotstar.cosmeticshopapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.iotstar.cosmeticshopapp.Helper.SwipeHelper;
import vn.iotstar.cosmeticshopapp.adapter.ProductGioHangAdapter;
import vn.iotstar.cosmeticshopapp.model.Product;

public class GioHangActivity extends AppCompatActivity {
    List<Product> products;
    ProductGioHangAdapter productGioHangAdapter;
    RecyclerView rvProductGioHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        AnhXa();
        demoData();
        setRvProductGioHang();
        btnXoa();
    }

    private void setRvProductGioHang() {
        productGioHangAdapter = new ProductGioHangAdapter(this, products);
        rvProductGioHang.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvProductGioHang.setLayoutManager(layoutManager);
        rvProductGioHang.setAdapter(productGioHangAdapter);
        productGioHangAdapter.notifyDataSetChanged();
    }

    private void AnhXa(){
        rvProductGioHang = (RecyclerView) findViewById(R.id.rvProduct_gioHang);

    }
    private void demoData() {
        products = new ArrayList<>();
        products.add(new Product(1, "DAZY Áo cổ lọ màu trơn1", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp"));
        products.add(new Product(2, "DAZY Áo cổ lọ màu trơn2", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp"));
        products.add(new Product(3, "DAZY Áo cổ lọ màu trơn3", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp"));
        products.add(new Product(4, "DAZY Áo cổ lọ màu trơn4", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp"));
        products.add(new Product(5, "DAZY Áo cổ lọ màu trơn5", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp"));
        products.add(new Product(6, "DAZY Áo cổ lọ màu trơn6", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp"));
        products.add(new Product(7, "DAZY Áo cổ lọ màu trơn7", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp"));
        products.add(new Product(8, "DAZY Áo cổ lọ màu trơn8", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp"));
        products.add(new Product(9, "DAZY Áo cổ lọ màu trơn9", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp"));
    }
    private void btnXoa(){
        SwipeHelper swipeHelper = new SwipeHelper(this, rvProductGioHang, false) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                Typeface typeface = Typeface.create("sans-serif-condensed", Typeface.BOLD);
                // Button Xóa
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Xóa",
                        null, // icon set to null to remove image
                        Color.parseColor("#FF0000"), Color.parseColor("#FFFFFF"),
                        typeface,
                        24, // font size in sp
                        0, // left padding in pixels
                        32, // top padding in pixels
                        0, // right padding in pixels
                        0, // bottom padding in pixels
                        new UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Toast.makeText(
                                        GioHangActivity.this,
                                        "Đã xóa" + products.get(pos).getName(),
                                        Toast.LENGTH_SHORT
                                ).show();
                                productGioHangAdapter.getModelList().remove(pos);
                                productGioHangAdapter.notifyItemRemoved(pos);
                            }
                        }
                ));

            }
        };
    }

}
