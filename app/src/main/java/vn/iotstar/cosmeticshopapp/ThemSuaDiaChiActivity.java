package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class ThemSuaDiaChiActivity extends AppCompatActivity {
    Switch switchMacDinh;
    boolean isMacDinh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sua_dia_chi);
        anhXa();
        setClick();
    }

    private void setClick() {
        //đổi background khi bật tắt switch
        switchMacDinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMacDinh) {
                    switchMacDinh.setBackgroundResource(R.drawable.switch_background);
                    switchMacDinh.setTrackDrawable(getResources().getDrawable(R.drawable.switch_background));
                    isMacDinh = false;
                } else {
                    switchMacDinh.setBackgroundResource(R.drawable.backbackground_boder);
                    switchMacDinh.setTrackDrawable(getResources().getDrawable(R.drawable.backbackground_boder));
                    isMacDinh = true;
                }
            }
        });
    }


    private void anhXa() {
        switchMacDinh = findViewById(R.id.switchMacDinh);
    }
}