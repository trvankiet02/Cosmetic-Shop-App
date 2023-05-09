package vn.iotstar.cosmeticshopapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import vn.iotstar.cosmeticshopapp.ChiTietSanPhamActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.model.Product;

public class ProductFlashSaleAdapter extends RecyclerView.Adapter<ProductFlashSaleAdapter.MyViewHolder>{
    Context context;
    List<Product> array;
    public ProductFlashSaleAdapter(Context context, List<Product> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public ProductFlashSaleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_danhmuc_flashsale, null);
        ProductFlashSaleAdapter.MyViewHolder myViewHolder = new ProductFlashSaleAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductFlashSaleAdapter.MyViewHolder holder, int position) {
        Product product = array.get(position);
        //format gia
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        String str_price = formatter.format(product.getPrice());
        String str_pricesale = formatter.format(product.getPromotionalPrice());
        String str_d = holder.tv_d.getText().toString();

        //gạch ngang giá cũ
        SpannableString spannable_str_price = new SpannableString(str_price);
        spannable_str_price.setSpan(new StrikethroughSpan(), 0, str_price.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE );

        SpannableString spannable_str_d = new SpannableString(str_d);
        spannable_str_d.setSpan(new StrikethroughSpan(), 0, str_d.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        //set text
        holder.productPrice.setText(spannable_str_price);
        holder.productPriceSale.setText(str_pricesale);
        holder.tv_d.setText(spannable_str_d);
        Glide.with(context)
        .load(product.getProductImages().get(0).getImage())
        .into(holder.productImage);

        //% giam gia
        int prirce = Integer.parseInt(product.getPrice().toString());
        int prirceSale = Integer.parseInt(product.getPromotionalPrice().toString());
        int phanTram = (prirce - prirceSale) * 100 / prirce;
        //phan tram thanh string
        String strphanTram = String.valueOf(phanTram);
        holder.phanTramGiamGia.setText(strphanTram);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chuyen sang activity chi tiet san pham
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
        public ImageView productImage;
        public TextView productPrice, productPriceSale, phanTramGiamGia, tv_d;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            anhXa();
        }

        private void anhXa() {
            productImage = itemView.findViewById(R.id.img_product_flashsale);
            productPrice = itemView.findViewById(R.id.tv_product_price);
            productPriceSale = itemView.findViewById(R.id.tv_product_price_flashsale);
            phanTramGiamGia = itemView.findViewById(R.id.tv_phantram_giamgia_flashsale);
            tv_d = itemView.findViewById(R.id.tv_d);
        }

    }
}
