package vn.iotstar.cosmeticshopapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.model.Cart;
import vn.iotstar.cosmeticshopapp.model.CartItem;

public class XacNhanShopAdapter extends RecyclerView.Adapter<XacNhanShopAdapter.MyViewHolder>{
    Context context;
    List<Cart> array;
    int totalAmount;
    int giaoHangAmount;
    int baoHoAmount;
    private HashMap<Integer, Boolean> baoHoMap = new HashMap<>();
    private HashMap<Integer, Boolean> giaoHangMap = new HashMap<>();
    public XacNhanShopAdapter(Context context, List<Cart> array) {
        this.context = context;
        this.array = array;
    }
    public interface TotalAmountListener {
        void onTotalAmountChanged(int totalAmount, int giaoHangAmount, int baoHoAmount);
    }
    private TotalAmountListener totalAmountListener;
    public void setTotalAmountListener(TotalAmountListener listener) {
        totalAmountListener = listener;
    }
    public Integer getTotal(){
        Integer total = 0;
        for (int i = 0; i < array.size(); i++) {
            for (CartItem cartItem : array.get(i).getCartItems()) {
                total += cartItem.getProduct().getPromotionalPrice() * cartItem.getQuantity();
            }
        }
        return total;
    }
    public Integer getBaoHoTotal(){
        Integer total = 0;
        for (int i = 0; i < array.size(); i++) {
            if (baoHoMap.get(i) != null && baoHoMap.get(i)) {
                total += 25000;
            }
        }
        return total;
    }
    public Integer getGiaoHangTotal(){
        Integer total = 0;
        for (int i = 0; i < array.size(); i++) {
            if (giaoHangMap.get(i) != null && giaoHangMap.get(i)) {
                total += 10000;
            }
        }
        return total;
    }
    public void calculateTotal() {
        totalAmount = 0;
        giaoHangAmount = 0;
        baoHoAmount = 0;
        for (int i = 0; i < array.size(); i++) {
            for (CartItem cartItem : array.get(i).getCartItems()) {
                totalAmount += cartItem.getProduct().getPromotionalPrice() * cartItem.getQuantity();
            }
            if (baoHoMap.get(i) != null && baoHoMap.get(i)) {
                totalAmount += 25000;
                baoHoAmount += 25000;
            }
            if (giaoHangMap.get(i) != null && giaoHangMap.get(i)) {
                totalAmount += 10000;
                giaoHangAmount += 10000;
            }
        }

        if (totalAmountListener != null) {
            totalAmountListener.onTotalAmountChanged(totalAmount, giaoHangAmount, baoHoAmount);
        }
    }

    @NonNull
    @Override
    public XacNhanShopAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_xacnhan_shop, parent, false);
        XacNhanShopAdapter.MyViewHolder myViewHolder = new XacNhanShopAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull XacNhanShopAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Cart cart = array.get(position);
        if (cart.getCartItems().size() > 0) {
            holder.tvtenshop.setText(cart.getStore().getName());
            holder.tvsoluongitem.setText(String.valueOf(cart.getCartItems().size()));
            List<CartItem> cartItems = cart.getCartItems();
            holder.xacNhanShopItemAdapter = new XacNhanShopItemAdapter(context, cartItems);
            holder.rvXacNhanShopItem.setAdapter(holder.xacNhanShopItemAdapter);
            holder.rvXacNhanShopItem.setHasFixedSize(true);
            holder.rvXacNhanShopItem.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
            holder.xacNhanShopItemAdapter.notifyDataSetChanged();
            holder.total = 0;
            for (int i = 0; i < cartItems.size(); i++) {
                holder.total += cartItems.get(i).getProduct().getPromotionalPrice() * cartItems.get(i).getQuantity();
            }
            if (holder.giaoHang) {
                holder.total += 10000;
            }
            if (holder.baoHo) {
                holder.total += 25000;
            }
            holder.tvtongcong1.setText(String.valueOf(holder.total));

            holder.rgGiaoHang.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i) {
                        case R.id.rb_giaohangtieuchuan: {
                            giaoHangMap.put(position, false);
                            holder.giaoHang = false;
                            calculateTotal();
                            //calculateTotalGiaoHang();
                            notifyDataSetChanged();
                            break;
                        }
                        case R.id.rb_giaohangnhanh: {
                            giaoHangMap.put(position, true);
                            holder.giaoHang = true;
                            calculateTotal();
                            //calculateTotalGiaoHang();
                            notifyDataSetChanged();
                            break;
                        }
                    }
                }
            });
            holder.switchdambaovanchuyen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.baoHo) {
                        baoHoMap.put(position, false);
                        //calculateTotalBaoHo();
                        holder.switchdambaovanchuyen.setBackgroundResource(R.drawable.switch_background);
                        holder.switchdambaovanchuyen.setTrackDrawable(context.getResources().getDrawable(R.drawable.switch_background));
                        holder.baoHo = false;
                        calculateTotal();
                        notifyDataSetChanged();
                    } else {
                        baoHoMap.put(position, true);
                        //calculateTotalBaoHo();
                        holder.switchdambaovanchuyen.setBackgroundResource(R.drawable.backbackground_boder);
                        holder.switchdambaovanchuyen.setTrackDrawable(context.getResources().getDrawable(R.drawable.backbackground_boder));
                        holder.baoHo = true;
                        calculateTotal();
                        notifyDataSetChanged();
                    }
                }
            });
        } else {
            holder.itemView.setVisibility(View.GONE);
        }

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
        Boolean giaoHang = false;
        Boolean baoHo = false;

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
