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

public class CategoryHomeAdapter extends RecyclerView.Adapter<CategoryHomeAdapter.MyViewHolder>{
    Context context;
    List<Category> array;

    public CategoryHomeAdapter(Context context, List<Category> array) {
        this.context = context;
        this.array = array;
    }




    @NonNull
    @Override
    public CategoryHomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_muasam_danhmuc, null);
        CategoryHomeAdapter.MyViewHolder myViewHolder = new CategoryHomeAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHomeAdapter.MyViewHolder holder, int position) {
        Category category = array.get(position);
        holder.categoryName.setText(category.getName());
        Glide.with(context)
                .load(category.getCategoryImage())
                .into(holder.categoryImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Ban dang nhan vao " + category.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryName;
        public ImageView categoryImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = (TextView) itemView.findViewById(R.id.tvCategoryName);
            categoryImage = (ImageView) itemView.findViewById(R.id.imgCategoryImage);
        }
    }
}
