package vn.iotstar.cosmeticshopapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

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
        holder.ProductPrice.setText(product.getPrice());
        Glide.with(context)
                .load(product.getProductImages().get(0))
                .into(holder.ProductImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Ban dang nhan vao " + product.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ProductName, ProductPrice;
        public ImageView ProductImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductPrice = (TextView) itemView.findViewById(R.id.txtPriceProduct);
            ProductName = (TextView) itemView.findViewById(R.id.txtNameProduct);
            ProductImage = (ImageView) itemView.findViewById(R.id.imgProduct);
        }
    }
}
