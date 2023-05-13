package vn.iotstar.cosmeticshopapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.model.Cart;
import vn.iotstar.cosmeticshopapp.model.CartItem;

public class XacNhanShopAdapter extends RecyclerView.Adapter<XacNhanShopAdapter.MyViewHolder>{
    Context context;
    List<Cart> array;
    public XacNhanShopAdapter(Context context, List<Cart> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public XacNhanShopAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_xacnhan_shop, null);
        XacNhanShopAdapter.MyViewHolder myViewHolder = new XacNhanShopAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull XacNhanShopAdapter.MyViewHolder holder, int position) {
        Cart cart = array.get(position);
        holder.tvtenshop.setText(cart.getStore().getName());
        holder.tvsoluongitem.setText(String.valueOf(cart.getCartItems().size()));
        List<CartItem> cartItems = cart.getCartItems();
        holder.xacNhanShopItemAdapter = new XacNhanShopItemAdapter(context, cartItems);
        holder.rvXacNhanShopItem.setAdapter(holder.xacNhanShopItemAdapter);
        holder.rvXacNhanShopItem.setHasFixedSize(true);
        holder.rvXacNhanShopItem.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        holder.xacNhanShopItemAdapter.notifyDataSetChanged();


        for (int i = 0; i < cartItems.size(); i++) {
            holder.total += cartItems.get(i).getProduct().getPromotionalPrice() * cartItems.get(i).getQuantity();
        }
        holder.tvtongcong1.setText(holder.total + "đ");
        holder.rgGiaoHang.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.lnTieuChuan:{
                        holder.rbTieuChuan.setChecked(true);
                        holder.rbNhanh.setChecked(false);
                        break;
                    }
                    case R.id.lnNhanh:{
                        holder.rbTieuChuan.setChecked(false);
                        holder.rbNhanh.setChecked(true);
                        holder.total += 10000;
                        notifyDataSetChanged();
                        break;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvXacNhanShopItem;
        TextView tvtenshop, tvsoluongitem, tvtongcong1;
        RadioGroup rgGiaoHang;

        XacNhanShopItemAdapter xacNhanShopItemAdapter;
        Switch switchdambaovanchuyen;
        RadioButton rbTieuChuan, rbNhanh;
        Integer total;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            anhXa();
        }

        private void anhXa() {
            rvXacNhanShopItem = itemView.findViewById(R.id.rvXacNhanShopItem);
            tvtenshop = itemView.findViewById(R.id.tvtenshop);
            tvsoluongitem = itemView.findViewById(R.id.tvsoluongitem);
            tvtongcong1 = itemView.findViewById(R.id.tvtongcong1);
            switchdambaovanchuyen = itemView.findViewById(R.id.switchdambaovanchuyen);
            rgGiaoHang = itemView.findViewById(R.id.rgGiaoHang);
            rbTieuChuan = itemView.findViewById(R.id.rb_giaohangtieuchuan);
            rbNhanh = itemView.findViewById(R.id.rb_giaohangnhanh);
            total = 0;
        }
    }
}
