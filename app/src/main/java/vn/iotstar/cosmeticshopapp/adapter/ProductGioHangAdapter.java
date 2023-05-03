package vn.iotstar.cosmeticshopapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.model.Product;

public class ProductGioHangAdapter extends RecyclerView.Adapter<ProductGioHangAdapter.MyViewHolder>{
    Context context;
    List<Product> array;
    public ProductGioHangAdapter(Context context, List<Product> array) {
        this.context = context;
        this.array = array;
    }

    public  List<Product> getModelList() {
        return array;
    }

    @NonNull
    @Override
    public ProductGioHangAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_giohang_sanpham, null);
        ProductGioHangAdapter.MyViewHolder myViewHolder = new ProductGioHangAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductGioHangAdapter.MyViewHolder holder, int position) {
        Product product = array.get(position);
        holder.ProductName.setText(product.getName());
        holder.ProductPrice.setText(product.getPrice());
        Glide.with(context)
                .load(product.getImage())
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
        public TextView ProductName, ProductPrice, txtSize;
        Spinner sizeSpinner;
        public Button btnRemove;
        public boolean isSwipeable;
        public ImageView ProductImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            isSwipeable = true;
            anhXa();
            String[] sizes = {"S", "M", "L", "XL", "XXL"};

            ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, sizes);

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

        private void anhXa() {
            sizeSpinner = itemView.findViewById(R.id.size_spinner);
            txtSize = itemView.findViewById(R.id.txtsize);
            ProductPrice = (TextView) itemView.findViewById(R.id.txtPriceProduct);
            ProductName = (TextView) itemView.findViewById(R.id.txtNameProduct);
            ProductImage = (ImageView) itemView.findViewById(R.id.imgProduct);
            //btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
