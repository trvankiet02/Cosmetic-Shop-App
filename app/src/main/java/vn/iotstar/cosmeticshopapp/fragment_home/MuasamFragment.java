package vn.iotstar.cosmeticshopapp.fragment_home;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.iotstar.cosmeticshopapp.GioHangActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.adapter.CategoryHomeAdapter;
import vn.iotstar.cosmeticshopapp.adapter.CategorySideBarAdapter;
import vn.iotstar.cosmeticshopapp.adapter.ProductHomeAdapter;
import vn.iotstar.cosmeticshopapp.adapter.StyleSideBarAdapter;
import vn.iotstar.cosmeticshopapp.model.Category;
import vn.iotstar.cosmeticshopapp.model.Product;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MuasamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MuasamFragment extends Fragment {

    private TextView txtTimer;

    CountDownTimer Timer;
    View view;
    RecyclerView rvProductDeNghi;
    List<Product> products;
    ProductHomeAdapter productHomeAdapter;
    RecyclerView rvCategoryHome;
    List<Category> categoryList;
    CategoryHomeAdapter categoryHomeAdapter;
    ImageView GioHang;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_muasam, container, false);
        AnhXa();
        demoData();
        setRvProductDeNghi();
        setRvCategoryHome();
        GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gioHang = new Intent(getActivity(), GioHangActivity.class);
                startActivity(gioHang);
            }
        });
        return view;

    }
    @Override
    public void onStart() {
        super.onStart();
        txtTimer = (TextView) view.findViewById(R.id.txtGio);
        Date date = new Date(System.currentTimeMillis());
        Timer = new CountDownTimer(date.getTime()+60*60*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int gio = (int) (millisUntilFinished / 720000) % 24;
                int phut = (int) (millisUntilFinished / 60000) % 60;
                int giay = (int) (millisUntilFinished / 1000) % 60;
                txtTimer.setText(String.format("%02d:%02d:%02d", gio, phut, giay));
            }
            @Override
            public void onFinish() {
                txtTimer.setText("00:00:00");
            }
        }.start();
    }
    private void AnhXa(){
        rvProductDeNghi = (RecyclerView) view.findViewById(R.id.rvProduct2);
        rvCategoryHome = (RecyclerView) view.findViewById(R.id.rcCategory);
        GioHang = (ImageView) view.findViewById(R.id.icon_bag);
    }
    private void demoData(){
        products = new ArrayList<>();
        products.add(new Product(1,"DAZY Áo cổ lọ màu trơn", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp" ));
        products.add(new Product(1,"DAZY Áo cổ lọ màu trơn", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp" ));
        products.add(new Product(1,"DAZY Áo cổ lọ màu trơn", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp" ));
        products.add(new Product(1,"DAZY Áo cổ lọ màu trơn", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp" ));
        products.add(new Product(1,"DAZY Áo cổ lọ màu trơn", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp" ));
        products.add(new Product(1,"DAZY Áo cổ lọ màu trơn", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp" ));
        products.add(new Product(1,"DAZY Áo cổ lọ màu trơn", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp" ));
        products.add(new Product(1,"DAZY Áo cổ lọ màu trơn", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp" ));
        products.add(new Product(1,"DAZY Áo cổ lọ màu trơn", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp" ));
        products.add(new Product(1,"DAZY Áo cổ lọ màu trơn", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp" ));
        products.add(new Product(1,"DAZY Áo cổ lọ màu trơn", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp" ));
        products.add(new Product(1,"DAZY Áo cổ lọ màu trơn", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp" ));
        products.add(new Product(1,"DAZY Áo cổ lọ màu trơn", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp" ));
        products.add(new Product(1,"DAZY Áo cổ lọ màu trơn", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp" ));
        products.add(new Product(1,"DAZY Áo cổ lọ màu trơn", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp" ));
        products.add(new Product(1,"DAZY Áo cổ lọ màu trơn", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp" ));
        products.add(new Product(1,"DAZY Áo cổ lọ màu trơn", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp" ));
        products.add(new Product(1,"DAZY Áo cổ lọ màu trơn", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp" ));
        products.add(new Product(1,"DAZY Áo cổ lọ màu trơn", "148000", "https://img.ltwebstatic.com/images3_pi/2023/02/01/167523143976e639d6203da40f200da8bba360ed66.webp" ));

        categoryList = new ArrayList<>();
        categoryList.add(new Category(1, "Áo", "https://res.cloudinary.com/dl0vbutga/image/upload/v1681638010/ytyhnvdwp0xnel15knif.png", null, null));
        categoryList.add(new Category(2, "Quần", "https://res.cloudinary.com/dl0vbutga/image/upload/v1681638081/fxzbcjdm3xcgpvxctrjw.png", null, null));
        categoryList.add(new Category(3, "Đồ biển", "https://res.cloudinary.com/dl0vbutga/image/upload/v1681638109/mtu58mltdobdesvtfjm2.png", null, null));
        categoryList.add(new Category(4, "Giày", "https://res.cloudinary.com/dl0vbutga/image/upload/v1681638134/jfzn0x9of0c5ospubdoo.png", null, null));
        categoryList.add(new Category(5, "Phụ kiện", "https://res.cloudinary.com/dl0vbutga/image/upload/v1681638162/bkunmpgcyevfcvn6um9g.png", null, null));
        categoryList.add(new Category(6, "TÚI", "https://res.cloudinary.com/dl0vbutga/image/upload/v1681638428/zgxfuzcyct51bpnqnm8h.png", null, null));
    }
    private void setRvProductDeNghi(){
        productHomeAdapter = new ProductHomeAdapter(getContext(), products);
        rvProductDeNghi.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvProductDeNghi.setLayoutManager(layoutManager);
        rvProductDeNghi.setAdapter(productHomeAdapter);
        productHomeAdapter.notifyDataSetChanged();
    }
    private void setRvCategoryHome(){
        categoryHomeAdapter = new CategoryHomeAdapter(getContext(), categoryList);
        rvCategoryHome.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rvCategoryHome.setLayoutManager(layoutManager);
        rvCategoryHome.setAdapter(categoryHomeAdapter);
        categoryHomeAdapter.notifyDataSetChanged();
    }
}