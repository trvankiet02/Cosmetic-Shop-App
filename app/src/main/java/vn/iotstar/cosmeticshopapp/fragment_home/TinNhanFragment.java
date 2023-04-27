package vn.iotstar.cosmeticshopapp.fragment_home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import vn.iotstar.cosmeticshopapp.R;

public class TinNhanFragment extends Fragment {

    View view;
    RecyclerView rvTinNhan;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_tinnhan, container, false);
        AnhXa();
        demoData();
        setRvTinNhan();
        return view;
    }

    private void AnhXa() {
        rvTinNhan = (RecyclerView) view.findViewById(R.id.rvtinnhan);
    }
    private void setRvTinNhan() {
    }

    private void demoData() {
    }
}