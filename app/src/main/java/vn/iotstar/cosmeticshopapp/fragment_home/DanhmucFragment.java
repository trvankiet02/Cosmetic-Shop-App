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

}