package vn.iotstar.cosmeticshopapp.fragment_home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.adapter.ProductHomeAdapter;
import vn.iotstar.cosmeticshopapp.model.Category;
import vn.iotstar.cosmeticshopapp.model.Product;


public class MoiFragment extends Fragment {

    View view;
    RecyclerView rvProduct;
    ProductHomeAdapter productHomeAdapter;
    List<Product> products;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_moi, container, false);
        AnhXa();
        demoData();
        setRvProduct();
        return view;
    }

    private void AnhXa() {
        rvProduct = (RecyclerView) view.findViewById(R.id.rvProduct);
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
    }
    private void setRvProduct(){
        productHomeAdapter = new ProductHomeAdapter(getContext(), products);
        rvProduct.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvProduct.setLayoutManager(layoutManager);
        rvProduct.setAdapter(productHomeAdapter);
        productHomeAdapter.notifyDataSetChanged();
    }
}