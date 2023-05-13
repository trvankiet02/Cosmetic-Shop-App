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

import java.util.ArrayList;
import java.util.List;

import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.model.CartItem;

public class XacNhanShopItemAdapter extends RecyclerView.Adapter<XacNhanShopItemAdapter.MyViewHolder>{
    Context context;
    List<CartItem> array;
    Integer total = 0;

    public XacNhanShopItemAdapter(Context context, List<CartItem> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public XacNhanShopItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_xacnhan_shopitem, null);
        XacNhanShopItemAdapter.MyViewHolder myViewHolder = new XacNhanShopItemAdapter.MyViewHolder(view);

        return myViewHolder;
    }
    public Integer getTotal(){
        return total;
    }

    @Override
    public void onBindViewHolder(@NonNull XacNhanShopItemAdapter.MyViewHolder holder, int position) {
        CartItem cartItem = array.get(position);
        total += cartItem.getProduct().getPromotionalPrice() * cartItem.getQuantity();
        holder.txtNameProduct.setText(cartItem.getProduct().getName());
        holder.tvSizeProduct.setText(cartItem.getSize());
        holder.tvSoluong.setText(String.valueOf(cartItem.getQuantity()));
        holder.tvPrice.setText(cartItem.getProduct().getPromotionalPrice() + "đ");
        Glide.with(context).load(cartItem.getProduct().getProductImages().get(0).getImage()).into(holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameProduct, tvSizeProduct, tvSoluong,tvPrice;
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
        }
    }
}
