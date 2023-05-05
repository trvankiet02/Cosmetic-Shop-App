package vn.iotstar.cosmeticshopapp.fragment_home;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.adapter.CategoryHomeAdapter;
import vn.iotstar.cosmeticshopapp.adapter.ProductHomeAdapter;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_muasam, container, false);
        AnhXa();
        setRvProductDeNghi();
        setRvCategoryHome();
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