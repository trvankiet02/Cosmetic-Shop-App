package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.adapter.CategorySideBarAdapter;
import vn.iotstar.cosmeticshopapp.adapter.DiaChiAdapter;
import vn.iotstar.cosmeticshopapp.adapter.StyleSideBarAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Address;
import vn.iotstar.cosmeticshopapp.model.Category;
import vn.iotstar.cosmeticshopapp.model.ListAddressResponse;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;

public class DiaChiCuaToiActivity extends AppCompatActivity {
    RecyclerView rvDiaChi;
    TextView tvThemDiaChi;
    DiaChiAdapter diaChiAdapter;
    APIService apiService;
    SharedPrefManager sharedPrefManager;
    List<Address> addressList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_chi_cua_toi);
        anhXa();
        setRvDiaChi();
        setBtnThemDiaChi();
    }

    private void setBtnThemDiaChi() {
        tvThemDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiaChiCuaToiActivity.this, ThemSuaDiaChiActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setRvDiaChi() {
        apiService.getAddressByUserId(sharedPrefManager.getUser().getId()).enqueue(new Callback<ListAddressResponse>() {
            @Override
            public void onResponse(Call<ListAddressResponse> call, Response<ListAddressResponse> response) {
                if (response.isSuccessful()){
                    addressList = response.body().getBody();
                    diaChiAdapter = new DiaChiAdapter(getApplicationContext(), addressList);
                    rvDiaChi.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                    rvDiaChi.setAdapter(diaChiAdapter);
                } else {
                    Toast.makeText(DiaChiCuaToiActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListAddressResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage() );
            }
        });
    }

    private void anhXa() {
        rvDiaChi = findViewById(R.id.rvDiaChi);
        tvThemDiaChi = findViewById(R.id.tvThemDiaChi);
        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
    }
}