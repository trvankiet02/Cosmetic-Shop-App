package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.smarteist.autoimageslider.SliderView;

import java.util.List;

import vn.iotstar.cosmeticshopapp.adapter.SliderProductImageAdapter;
import vn.iotstar.cosmeticshopapp.model.ProductImage;

public class FullScreenActivity extends AppCompatActivity {
    SliderView sliderView;
    SliderProductImageAdapter sliderProductImageAdapter;
    TextView tvNum;
    ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        sliderView = (SliderView) findViewById(R.id.slider);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        tvNum = (TextView) findViewById(R.id.tvNum);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        List<ProductImage> imageList = (List<ProductImage>) bundle.getSerializable("image_url");

        SliderProductImageAdapter sliderProductImageAdapter = new SliderProductImageAdapter(this, imageList, 1);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(sliderProductImageAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}