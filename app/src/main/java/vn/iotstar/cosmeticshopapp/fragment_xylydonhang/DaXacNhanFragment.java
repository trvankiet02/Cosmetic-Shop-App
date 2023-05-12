package vn.iotstar.cosmeticshopapp.fragment_xylydonhang;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.adapter.DonHangAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Order;
import vn.iotstar.cosmeticshopapp.model.OrderResponse;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;

public class DaXacNhanFragment extends Fragment {

    LinearLayout ln_empty;
    RecyclerView rvTatCaDonHang;
    View view;
    DonHangAdapter donHangAdapter;
    List<Order> orders = new ArrayList<>();
    APIService apiService;
    SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_da_xac_nhan, container, false);
        anhXa();
        setRvDaXacNhan();
        return view;
    }
    private void setRvDaXacNhan() {
        apiService.getOrder(sharedPrefManager.getUser().getId(), 2).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()){
                    orders = response.body().getBody();

                    if(orders.size() != 0) {
                        ln_empty.setVisibility(view.GONE);

                        donHangAdapter = new DonHangAdapter(getContext(), orders);
                        rvTatCaDonHang.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        rvTatCaDonHang.setLayoutManager(layoutManager);
                        rvTatCaDonHang.setAdapter(donHangAdapter);
                        donHangAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {

            }
        });
    }

    private void anhXa() {
        rvTatCaDonHang = view.findViewById(R.id.rvTatCaDonHang);
        ln_empty = view.findViewById(R.id.ln_empty);
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        sharedPrefManager = new SharedPrefManager(getContext());
    }

}