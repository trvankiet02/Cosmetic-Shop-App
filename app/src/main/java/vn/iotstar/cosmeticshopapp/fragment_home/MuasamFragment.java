package vn.iotstar.cosmeticshopapp.fragment_home;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.GioHangActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.adapter.CategoryHomeAdapter;
import vn.iotstar.cosmeticshopapp.adapter.ProductHomeAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Category;
import vn.iotstar.cosmeticshopapp.model.CategoryAndStyleResponse;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.model.ProductResponse;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPref.SharedPrefManager;


public class MuasamFragment extends Fragment {
private static final String TAG = MuasamFragment.class.getName();
    private TextView txtTimer;

    CountDownTimer Timer;
    View view;
    RecyclerView rvProductDeNghi;
    RecyclerView rvFlashSale;
    List<Product> products;
    ProductHomeAdapter productHomeAdapter;
    RecyclerView rvCategoryHome;
    List<Category> categoryList;
    CategoryHomeAdapter categoryHomeAdapter;
    ImageView GioHang;
    APIService apiService;
    SharedPrefManager sharedPrefManager;
    Boolean isLoading = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_muasam, container, false);
        AnhXa();
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
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        sharedPrefManager = new SharedPrefManager(getContext());
        rvFlashSale = (RecyclerView) view.findViewById(R.id.rvFlashSale);
    }

    private void setRvProductDeNghi(){
        apiService.getRandomProduct(4).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()){
                    products = response.body().getBody();
                    if (products == null){
                        Log.e(TAG, "onResponse: " + "NULL" );
                    }
                    productHomeAdapter = new ProductHomeAdapter(getContext(), products);
                    rvProductDeNghi.setHasFixedSize(true);
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);

                    rvProductDeNghi.setLayoutManager(layoutManager);
                    rvProductDeNghi.setAdapter(productHomeAdapter);
                    productHomeAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });

    }
    private void setRvCategoryHome(){
        apiService.getCategory().enqueue(new Callback<CategoryAndStyleResponse>() {
            @Override
            public void onResponse(Call<CategoryAndStyleResponse> call, Response<CategoryAndStyleResponse> response) {
                if  (response.isSuccessful()){
                    categoryList = response.body().getBody();
                    categoryHomeAdapter = new CategoryHomeAdapter(getContext(), categoryList);
                    rvCategoryHome.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false);
                    rvCategoryHome.setLayoutManager(layoutManager);
                    rvCategoryHome.setAdapter(categoryHomeAdapter);
                    categoryHomeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CategoryAndStyleResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}