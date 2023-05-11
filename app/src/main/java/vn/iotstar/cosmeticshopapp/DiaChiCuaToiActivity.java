package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.iotstar.cosmeticshopapp.adapter.CategorySideBarAdapter;
import vn.iotstar.cosmeticshopapp.adapter.DiaChiAdapter;
import vn.iotstar.cosmeticshopapp.adapter.StyleSideBarAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Address;
import vn.iotstar.cosmeticshopapp.model.Category;
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
    }

    private void setRvDiaChi() {
        addressList.add(new Address());
        addressList.add(new Address());
        addressList.add(new Address());

        diaChiAdapter = new DiaChiAdapter(getApplicationContext(), addressList);
        rvDiaChi.setHasFixedSize(true);
        rvDiaChi.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        rvDiaChi.setAdapter(diaChiAdapter);
    }

    private void anhXa() {
        rvDiaChi = findViewById(R.id.rvDiaChi);
        tvThemDiaChi = findViewById(R.id.tvThemDiaChi);
        sharedPrefManager = new SharedPrefManager(getApplicationContext());
    }
}