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

import com.bumptech.glide.Glide;

import java.util.List;

import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.model.Category;
import vn.iotstar.cosmeticshopapp.model.Style;

public class StyleOfStyleAdapter extends RecyclerView.Adapter<StyleOfStyleAdapter.MyViewHolder>{
    Context context;
    List<Style> array;

    public StyleOfStyleAdapter(Context context, List<Style> array) {
        this.context = context;
        this.array = array;
    }
    public void updateStyles(List<Style> styles) {
        array = styles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StyleOfStyleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_style, null);
        StyleOfStyleAdapter.MyViewHolder myViewHolder = new StyleOfStyleAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StyleOfStyleAdapter.MyViewHolder holder, int position) {
        Style style = array.get(position);
        holder.styleName.setText(style.getName());
        Glide.with(context)
                .load(style.getStyleImage())
                .into(holder.styleImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Ban dang nhan vao " + style.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView styleName;
        public ImageView styleImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            styleName = itemView.findViewById(R.id.tv_name_style);
            styleImage = itemView.findViewById(R.id.img_style);
        }
    }
}
