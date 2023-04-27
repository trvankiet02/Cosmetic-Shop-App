package vn.iotstar.cosmeticshopapp.fragment_home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.iotstar.cosmeticshopapp.MainActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.adapter.CategorySideBarAdapter;
import vn.iotstar.cosmeticshopapp.adapter.StyleSideBarAdapter;
import vn.iotstar.cosmeticshopapp.model.Category;
import vn.iotstar.cosmeticshopapp.model.Style;


public class DanhmucFragment extends Fragment {
    View view;
    RecyclerView rvCategory;
    RecyclerView rvStyle;
    List<Category> categoryList;
    List<Style> styleList;
    CategorySideBarAdapter categorySideBarAdapter;
    StyleSideBarAdapter styleSideBarAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_danhmuc, container, false);

        AnhXa();
        demoData();
        setRvCategory();
        setRvStyle();

        return view;

    }
    private void setRvCategory(){
        //nhan mang

        //khoi tao adapter
        categorySideBarAdapter = new CategorySideBarAdapter(getContext()
        , categoryList);
        rvCategory.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        rvCategory.setLayoutManager(layoutManager);
        rvCategory.setAdapter(categorySideBarAdapter);
        categorySideBarAdapter.notifyDataSetChanged();
    }
    private void setRvStyle(){
        //nhan mang

        //khoi tao adapter
        styleSideBarAdapter = new StyleSideBarAdapter(getContext()
                , styleList);
        rvStyle.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        rvStyle.setLayoutManager(layoutManager);
        rvStyle.setAdapter(styleSideBarAdapter);
        styleSideBarAdapter.notifyDataSetChanged();
    }
    private void AnhXa(){
        rvCategory = (RecyclerView) view.findViewById(R.id.rvCategory);
        rvStyle = (RecyclerView) view.findViewById(R.id.rvStyle);

    }
    private void demoData(){
        categoryList = new ArrayList<>();
        categoryList.add(new Category(1, "ĐỒ ĐI BIỂN", "image 1", null, null));
        categoryList.add(new Category(2, "GIẢM GIÁ", "image 2", null, null));
        categoryList.add(new Category(3, "TRANG PHỤC", "image 3", null, null));
        categoryList.add(new Category(4, "ĐỒ LÓT VÀ ĐỒ MẶC NHÀ", "image 4", null, null));
        categoryList.add(new Category(5, "GIÀY", "image 1", null, null));
        categoryList.add(new Category(6, "TÚI", "image 2", null, null));
        categoryList.add(new Category(7, "LÀM ĐẸP", "image 3", null, null));
        categoryList.add(new Category(8, "NHÀ VÀ VẬT NUÔI", "image 4", null, null));

        styleList = new ArrayList<>();
        styleList.add(new Style(1, "Bộ bikini 3 mảnh", "https://cf.shopee.vn/file/6f8628c37eafc691a218703500f57fef", 1, null, null));
        styleList.add(new Style(2, "Trang phục đi biển mới", "https://vn-test-11.slatic.net/p/a6b8bbca9dc6afd5637913a4bb8bd3cc.jpg", 1, null, null));
        styleList.add(new Style(3, "một mảnh", "https://vn-test-11.slatic.net/p/f073de69dbf435d63f27251ca00382b8.jpg", 1, null, null));
        styleList.add(new Style(4, "áo bikini", "https://cf.shopee.vn/file/sg-11134201-23020-1ulk4qxxz0mvc5", 1, null, null));
        styleList.add(new Style(5, "kimono", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRYJjxvPT1CsycrzIG-NjPsNBNg2cZgPrrq86s4lgqiAqzhufLLLHJfwfOpyIH18RRyLcQ&usqp=CAU", 1, null, null));
        styleList.add(new Style(6, "áo che", "https://product.hstatic.net/200000417501/product/z3340086475280_0b302f8e62b99ec0b6a2dc747d89e5d2_30099707f1b04939b57be74a597d372b_master.jpg", 1, null, null));
        styleList.add(new Style(7, "đồ bơi", "https://cf.shopee.vn/file/6f8628c37eafc691a218703500f57fef", 1, null, null));
        styleList.add(new Style(8, "đồ bơi một vai", "https://product.hstatic.net/200000417501/product/z3340086475280_0b302f8e62b99ec0b6a2dc747d89e5d2_30099707f1b04939b57be74a597d372b_master.jpg", 1, null, null));
        styleList.add(new Style(9, "Đồ bơi high cut", "https://filebroker-cdn.lazada.vn/kf/Saee4e577e8914f66974778fc8068d4d5X.jpg", 1, null, null));
        styleList.add(new Style(10, "Đồ bơi nhiệt đới", "https://cf.shopee.vn/file/c41a54e7803464029ac7eb2ac52b26be", 1, null, null));
    }
}