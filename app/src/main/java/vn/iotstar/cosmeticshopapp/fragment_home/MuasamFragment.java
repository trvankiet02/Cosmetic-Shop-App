package vn.iotstar.cosmeticshopapp.fragment_home;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.GioHangActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.adapter.CategoryHomeAdapter;
import vn.iotstar.cosmeticshopapp.adapter.ProductFlashSaleAdapter;
import vn.iotstar.cosmeticshopapp.adapter.ProductHomeAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Category;
import vn.iotstar.cosmeticshopapp.model.CategoryAndStyleResponse;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.model.ProductResponse;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;


public class MuasamFragment extends Fragment {
    private static final String TAG = MuasamFragment.class.getName();
    private TextView txtGio, txtPhut, txtGiay;

    CountDownTimer Timer;
    View view;
    RecyclerView rvProductDeNghi;
    RecyclerView rvFlashSale;
    List<Product> products;
    ProductHomeAdapter productHomeAdapter;
    RecyclerView rvCategoryHome;
    List<Category> categoryList;
    CategoryHomeAdapter categoryHomeAdapter;
    ImageView GioHang, ivFavouriteProduct;
    APIService apiService;
    SharedPrefManager sharedPrefManager;
    Boolean isLoading = false;
    ProductFlashSaleAdapter productFlashSaleAdapter;
    List<Product> productFlashSaleList = new ArrayList<>();
    public interface OnButtonClickListener {
        void onButtonClick();
    }
    private OnButtonClickListener onButtonClickListener;
    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.onButtonClickListener = listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_muasam, container, false);
        AnhXa();
        setRvProductDeNghi();
        setRvCategoryHome();
        setRvProductSale();
        setFavoriteProduct();
        GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gioHang = new Intent(getActivity(), GioHangActivity.class);
                startActivity(gioHang);
            }
        });
        return view;

    }

    private void setFavoriteProduct() {
        ivFavouriteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onButtonClickListener != null) {
                    onButtonClickListener.onButtonClick();
                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        txtGio = (TextView) view.findViewById(R.id.txtGio);
        txtPhut = (TextView) view.findViewById(R.id.txtPhut);
        txtGiay = (TextView) view.findViewById(R.id.txtGiay);

        long now = System.currentTimeMillis();
        long midnight = getMidnight(now);
        CountDownTimer timer = new CountDownTimer(midnight - now, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int hours = (int) (millisUntilFinished / (60 * 60 * 1000));
                int minutes = (int) ((millisUntilFinished / (60 * 1000)) % 60);
                int seconds = (int) ((millisUntilFinished / 1000) % 60);
                String timeGio = String.format("%02d", hours);
                String timePhut = String.format("%02d", minutes);
                String timeGiay = String.format("%02d", seconds);

                txtGio.setText(timeGio);
                txtPhut.setText(timePhut);
                txtGiay.setText(timeGiay);
            }

            @Override
            public void onFinish() {
                txtGio.setText("00");
                txtPhut.setText("00");
                txtGiay.setText("00");
            }
        };
        timer.start();
    }

    private long getMidnight(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
    private void AnhXa(){
        rvProductDeNghi = (RecyclerView) view.findViewById(R.id.rvProduct2);
        rvCategoryHome = (RecyclerView) view.findViewById(R.id.rcCategory);
        GioHang = (ImageView) view.findViewById(R.id.icon_bag);
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        sharedPrefManager = new SharedPrefManager(getContext());
        rvFlashSale = (RecyclerView) view.findViewById(R.id.rvFlashSale);
        ivFavouriteProduct = (ImageView) view.findViewById(R.id.ivFavouriteProduct);
    }

    private void setRvProductDeNghi(){
        apiService.getRandomProduct(10).enqueue(new Callback<ProductResponse>() {
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
    private void setRvProductSale() {
        apiService.getRandomProduct(10).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if  (response.isSuccessful()) {
                    List<Product> productFlashSaleList1 = response.body().getBody();
                    for (Product product : productFlashSaleList1) {
                        if(product.getPrice() > product.getPromotionalPrice())
                        {
                            productFlashSaleList.add(product);
                        }
                    }
                    if (productFlashSaleList == null) {
                    } else {
                        productFlashSaleAdapter = new ProductFlashSaleAdapter(getContext(), productFlashSaleList);
                        rvFlashSale.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false);
                        rvFlashSale.setLayoutManager(layoutManager);
                        rvFlashSale.setAdapter(productFlashSaleAdapter);
                        productFlashSaleAdapter.notifyDataSetChanged();
                    }
                }else {
                    Log.e(TAG, "onResponse: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });
    }
}