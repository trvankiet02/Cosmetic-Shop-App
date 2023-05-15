package vn.iotstar.cosmeticshopapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vn.iotstar.cosmeticshopapp.ChiTietSanPhamActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.seller.ThemSuaSanPhamActivity;

public class ProductEditAdapter extends RecyclerView.Adapter<ProductEditAdapter.ViewHolder> implements Filterable {
    Context context;
    List<Product> array;
    List<Product> defaultArray;
    public ProductEditAdapter(Context context, List<Product> array) {
        this.context = context;
        this.array = array;
        defaultArray = array;
    }
    public void updateProduct(List<Product> productList) {
        this.array = productList;
        notifyDataSetChanged();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                List<Product> filterArray = new ArrayList<>();

                if (charSequence == null || charSequence.length() == 0) {
                    results.count = defaultArray.size();
                    results.values = defaultArray;
                } else {
                    for (Product product : array) {
                        if (product.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            filterArray.add(product);
                        }
                    }
                    results.count = filterArray.size();
                    results.values = filterArray;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                array = (List<Product>) filterResults.values;
                notifyDataSetChanged();
            }

        };
    }
    @NonNull
    @Override
    public ProductEditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_product_edit, null);
        ProductEditAdapter.ViewHolder myViewHolder = new ProductEditAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductEditAdapter.ViewHolder holder, int position) {
        Product product = array.get(position);
        holder.ProductName.setText(product.getName());

        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        holder.ProductPrice.setText(formatter.format(product.getPromotionalPrice()));

        SliderProductImageAdapter sliderProductImageAdapter = new SliderProductImageAdapter(context.getApplicationContext(), product.getProductImages(), 1);
        holder.sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        holder.sliderView.setSliderAdapter(sliderProductImageAdapter);
        holder.storeName.setText(product.getStore().getName());
        holder.tv_soluongdaban.setText(String.valueOf(product.getSold()));
        Glide.with(context).load(product.getStore().getStoreImage()).into(holder.storeImage);
        holder.imgEditProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Bạn đang nhấn vào " + product.getName(), Toast.LENGTH_SHORT).show();
                //chuyển đến trang chỉnh sửa
                Intent intent = new Intent(context, ThemSuaSanPhamActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", product);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements Filterable {
        TextView ProductName, ProductPrice,storeName, tv_soluongdaban;
        SliderView sliderView;
        ImageView storeImage, imgEditProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductName = itemView.findViewById(R.id.txtNameProduct);
            ProductPrice = (TextView) itemView.findViewById(R.id.txtPriceProduct);
            sliderView = itemView.findViewById(R.id.slider);
            storeName = itemView.findViewById(R.id.storeName);
            storeImage = itemView.findViewById(R.id.storeImage);
            tv_soluongdaban = itemView.findViewById(R.id.tv_soluongdaban);
            imgEditProduct = itemView.findViewById(R.id.imgEditProduct);
        }


        @Override
        public Filter getFilter() {
            return getFilter();
        }
    }


}
