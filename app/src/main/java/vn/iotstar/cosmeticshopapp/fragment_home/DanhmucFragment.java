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
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvStyle.setLayoutManager(layoutManager);
        rvStyle.setAdapter(categorySideBarAdapter);
        styleSideBarAdapter.notifyDataSetChanged();
    }
    private void AnhXa(){
        rvCategory = (RecyclerView) view.findViewById(R.id.rvCategory);
        rvStyle = (RecyclerView) view.findViewById(R.id.rvStyle);

    }
    private void demoData(){
        categoryList = new ArrayList<>();
        categoryList.add(new Category(1, "cate 1", "image 1", null, null));
        categoryList.add(new Category(2, "cate 2", "image 2", null, null));
        categoryList.add(new Category(3, "cate 3", "image 3", null, null));
        categoryList.add(new Category(4, "cate 4", "image 4", null, null));

        styleList = new ArrayList<>();
        styleList.add(new Style(1, "style 1", "image 1", 1, null, null));
        styleList.add(new Style(2, "style 2", "image 2", 1, null, null));
        styleList.add(new Style(3, "style 3", "image 3", 2, null, null));
        styleList.add(new Style(4, "style 4", "image 4", 2, null, null));
        styleList.add(new Style(5, "style 5", "image 5", 3, null, null));
    }
}