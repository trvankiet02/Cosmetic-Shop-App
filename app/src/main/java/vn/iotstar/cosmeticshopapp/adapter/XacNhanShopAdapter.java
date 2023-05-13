package vn.iotstar.cosmeticshopapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
//        holder.tvtenshop.setText(cart.getStore().getName());
        //holder.tvsoluongitem.setText(cart.getCartItems().size());


        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem());
        cartItems.add(new CartItem());
        cartItems.add(new CartItem());
        holder.xacNhanShopItemAdapter = new XacNhanShopItemAdapter(context, cartItems);
        holder.rvXacNhanShopItem.setAdapter(holder.xacNhanShopItemAdapter);
        holder.rvXacNhanShopItem.setHasFixedSize(true);
        holder.rvXacNhanShopItem.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        holder.xacNhanShopItemAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvXacNhanShopItem;
        TextView tvtenshop, tvsoluongitem, tvtongcong1;

        XacNhanShopItemAdapter xacNhanShopItemAdapter;
        Switch switchdambaovanchuyen;

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
        }
    }
}
