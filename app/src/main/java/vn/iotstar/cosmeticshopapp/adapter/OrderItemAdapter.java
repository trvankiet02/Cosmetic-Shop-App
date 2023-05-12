package vn.iotstar.cosmeticshopapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.model.OrderItem;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.MyViewHolder>{
    Context context;
    List<OrderItem> array;
    public OrderItemAdapter(Context context, List<OrderItem> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public OrderItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_orderitem, null);
        OrderItemAdapter.MyViewHolder myViewHolder = new OrderItemAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemAdapter.MyViewHolder holder, int position) {
        OrderItem orderItem = array.get(position);
        Glide.with(context)
                .load(orderItem.getProduct().getProductImages().get(0).getImage())
                .into(holder.img_sanpham);
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_sanpham;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_sanpham = itemView.findViewById(R.id.img_sanpham);
        }
    }
}
