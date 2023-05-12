package vn.iotstar.cosmeticshopapp.fragment_xylydonhang;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.iotstar.cosmeticshopapp.GioHangActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.adapter.DonHangAdapter;
import vn.iotstar.cosmeticshopapp.fragment_login_signup.LoginFragment;
import vn.iotstar.cosmeticshopapp.model.Order;

public class TatCaDonHangFragment extends Fragment {
    LinearLayout ln_empty;
    RecyclerView rvTatCaDonHang;
    View view;
    DonHangAdapter donHangAdapter;
    List<Order> orders = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tat_ca_don_hang, container, false);
        anhXa();
        setRvTatCaDonHang();


        return view;
    }

    private void setRvTatCaDonHang() {

        orders.add(new Order());
        orders.add(new Order());
        orders.add(new Order());

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

    private void anhXa() {
        rvTatCaDonHang = view.findViewById(R.id.rvTatCaDonHang);
        ln_empty = view.findViewById(R.id.ln_empty);
    }
}