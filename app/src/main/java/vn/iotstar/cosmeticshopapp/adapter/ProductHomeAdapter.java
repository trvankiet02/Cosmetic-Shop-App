package vn.iotstar.cosmeticshopapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

import vn.iotstar.cosmeticshopapp.ChiTietSanPhamActivity;
import vn.iotstar.cosmeticshopapp.GioHangActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.model.Product;

public class ProductHomeAdapter extends RecyclerView.Adapter<ProductHomeAdapter.MyViewHolder>{
    Context context;
    List<Product> array;
    public ProductHomeAdapter(Context context, List<Product> array) {
        this.context = context;
        this.array = array;
    }


    @NonNull
    @Override
    public ProductHomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_product_2column, null);
        ProductHomeAdapter.MyViewHolder myViewHolder = new ProductHomeAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHomeAdapter.MyViewHolder holder, int position) {
        Product product = array.get(position);
        holder.ProductName.setText(product.getName());
        SliderAdapter sliderAdapter = new SliderAdapter(context.getApplicationContext(), product.getProductImages());
        holder.sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        holder.sliderView.setSliderAdapter(sliderAdapter);
        holder.sliderView.setScrollTimeInSec(3);
        holder.sliderView.setAutoCycle(false);
        holder.sliderView.startAutoCycle();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Ban dang nhan vao " + product.getName(), Toast.LENGTH_SHORT).show();
                Intent chitiet = new Intent(context, ChiTietSanPhamActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", product);
                chitiet.putExtras(bundle);
                context.startActivity(chitiet);
            }
        });
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ProductName, ProductPrice;
        public SliderView sliderView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductPrice = (TextView) itemView.findViewById(R.id.txtPriceProduct);
            ProductName = (TextView) itemView.findViewById(R.id.txtNameProduct);
            sliderView = (SliderView) itemView.findViewById(R.id.slider);
        }
    }
}
