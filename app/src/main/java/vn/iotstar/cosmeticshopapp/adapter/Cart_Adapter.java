package vn.iotstar.cosmeticshopapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.model.CartItem;

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.MyViewHolder>{
    Context context;
    List<List<CartItem>> array;
    public Cart_Adapter(Context context, List<List<CartItem>> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public Cart_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_cart, null);
        Cart_Adapter.MyViewHolder myViewHolder = new Cart_Adapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Cart_Adapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvCart_item;
        ImageView storeImage;
        TextView storeName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            anhXa();
        }

        private void anhXa() {
            rvCart_item = itemView.findViewById(R.id.rvCart_item);
            storeImage = itemView.findViewById(R.id.storeImage);
            storeName = itemView.findViewById(R.id.storeName);
        }
    }
}
