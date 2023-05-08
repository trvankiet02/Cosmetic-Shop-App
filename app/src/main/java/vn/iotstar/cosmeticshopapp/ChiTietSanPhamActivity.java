package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.iotstar.cosmeticshopapp.adapter.FeedbackAdapter;
import vn.iotstar.cosmeticshopapp.adapter.ProductHomeAdapter;
import vn.iotstar.cosmeticshopapp.model.Feedback;
import vn.iotstar.cosmeticshopapp.model.Product;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    Spinner sizeSpinner;
    TextView txtSize;
    RecyclerView rvProducGoiY;
    List<Product> products;
    ProductHomeAdapter productHomeAdapter;
    ImageView GioHang;

    RecyclerView rvFeedback;
    List<Feedback> feedbacks;
    FeedbackAdapter feedbackAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);

        AnhXa();
        SetSpinner();
        demoData();
        setRvProductGoiY();
        set2Feedback();
        GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gioHang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(gioHang);
            }
        });
    }


    private void AnhXa() {
        sizeSpinner = (Spinner) findViewById(R.id.size_spinner);
        txtSize = findViewById(R.id.txtsize);
        rvProducGoiY = (RecyclerView) findViewById(R.id.rvProduct);
        rvFeedback = (RecyclerView) findViewById(R.id.rvFeedback);
        GioHang = (ImageView) findViewById(R.id.img_icon_bag);
    }

    private void SetSpinner() {
        String[] sizes = {"S", "M", "L", "XL", "XXL"};

        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sizes);

        sizeSpinner.setAdapter(sizeAdapter);
        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = parent.getItemAtPosition(position).toString();
                txtSize.setText(selectedOption);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void demoData(){
        products = new ArrayList<>();

        feedbacks = new ArrayList<>();
        feedbacks.add(new Feedback());
        feedbacks.add(new Feedback());
        feedbacks.add(new Feedback());
        feedbacks.add(new Feedback());
        feedbacks.add(new Feedback());
       }
    private void setRvProductGoiY(){
        productHomeAdapter = new ProductHomeAdapter(this, products);
        rvProducGoiY.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvProducGoiY.setLayoutManager(layoutManager);
        rvProducGoiY.setAdapter(productHomeAdapter);
        productHomeAdapter.notifyDataSetChanged();
    }
    private void set2Feedback(){
        List<Feedback> twoFeedbacks = new ArrayList<>(feedbacks.subList(0, 2));

        feedbackAdapter = new FeedbackAdapter(this, twoFeedbacks);
        rvFeedback.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvFeedback.setLayoutManager(layoutManager);
        rvFeedback.setAdapter(feedbackAdapter);
        feedbackAdapter.notifyDataSetChanged();
    }
}