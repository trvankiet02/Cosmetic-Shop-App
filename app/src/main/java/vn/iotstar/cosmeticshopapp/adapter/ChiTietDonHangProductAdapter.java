package vn.iotstar.cosmeticshopapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.model.CartItem;
import vn.iotstar.cosmeticshopapp.model.OrderItem;

public class ChiTietDonHangProductAdapter extends RecyclerView.Adapter<ChiTietDonHangProductAdapter.MyViewHolder>{
    Context context;
    List<OrderItem> array;

    public ChiTietDonHangProductAdapter(Context context, List<OrderItem> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public ChiTietDonHangProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_chitietdonhang_product, null);
        ChiTietDonHangProductAdapter.MyViewHolder myViewHolder = new ChiTietDonHangProductAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietDonHangProductAdapter.MyViewHolder holder, int position) {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);

        OrderItem orderItem = array.get(position);
        holder.txtNameProduct.setText(orderItem.getProduct().getName());
        holder.tvSizeProduct.setText(orderItem.getSize());
        holder.tvSoluong.setText(String.valueOf(orderItem.getQuantity()));
        holder.tvSoluong1.setText(String.valueOf(orderItem.getQuantity()));

        String formattedNumber = formatter.format(orderItem.getUnitPrice());
        holder.tvPrice.setText(formattedNumber);
        Glide.with(context)
                .load(orderItem.getProduct().getProductImages().get(0).getImage())
                .into(holder.imgProduct);

    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameProduct, tvSizeProduct, tvSoluong,tvPrice, tvSoluong1;
        ImageView imgProduct;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            anhXa();
        }

        private void anhXa() {
            txtNameProduct = itemView.findViewById(R.id.txtNameProduct);
            tvSizeProduct = itemView.findViewById(R.id.tvSizeProduct);
            tvSoluong = itemView.findViewById(R.id.tvSoluong);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvSoluong1 = itemView.findViewById(R.id.tvSoluong1);
        }
    }
}
