package vn.iotstar.cosmeticshopapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.model.Style;

public class TinNhanAdapter extends RecyclerView.Adapter<TinNhanAdapter.MyViewHolder>{
    Context context;
    List<Style> array;

    @NonNull
    @Override
    public TinNhanAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_tinnhan, null);
        TinNhanAdapter.MyViewHolder myViewHolder = new TinNhanAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TinNhanAdapter.MyViewHolder holder, int position) {

        //chua xong
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Ban dang nhan vao ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgIcon;
        public TextView txtName;
        public TextView txtDes;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
            txtName = (TextView) itemView.findViewById(R.id.txtname);
            txtDes = (TextView) itemView.findViewById(R.id.txtdecription);
        }
    }
}
