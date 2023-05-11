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

import java.util.ArrayList;
import java.util.List;

import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.model.Cart;
import vn.iotstar.cosmeticshopapp.model.CartItem;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.model.ProductQuantity;

public class ProductGioHangAdapter extends RecyclerView.Adapter<ProductGioHangAdapter.MyViewHolder>{
    Context context;
    List<CartItem> array;
    public ProductGioHangAdapter(Context context, List<CartItem> array) {
        this.context = context;
        this.array = array;
    }

    public  List<CartItem> getModelList() {
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
        List<String> sizes = new ArrayList<>();

        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, sizes);

        holder.sizeSpinner.setAdapter(sizeAdapter);
        holder.sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = parent.getItemAtPosition(position).toString();
                holder.txtSize.setText(selectedOption);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ProductName, ProductPrice, txtSize, storeName, txtQuantity;
        Spinner sizeSpinner;
        public boolean isSwipeable;
        public ImageView ProductImage, storeImage;
        public Integer quantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            isSwipeable = true;
            anhXa();
        }

        private void anhXa() {
            sizeSpinner = itemView.findViewById(R.id.size_spinner);
            txtSize = itemView.findViewById(R.id.txtsize);
            ProductPrice = (TextView) itemView.findViewById(R.id.txtPriceProduct);
            ProductName = (TextView) itemView.findViewById(R.id.txtNameProduct);
            ProductImage = (ImageView) itemView.findViewById(R.id.imgProduct);
            storeName = itemView.findViewById(R.id.storeName);
            storeImage = itemView.findViewById(R.id.storeImage);
            txtQuantity = itemView.findViewById(R.id.count_product);
            //btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
