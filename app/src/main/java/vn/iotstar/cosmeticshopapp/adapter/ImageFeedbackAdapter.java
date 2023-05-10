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
import vn.iotstar.cosmeticshopapp.model.ReviewImage;

public class ImageFeedbackAdapter extends RecyclerView.Adapter<ImageFeedbackAdapter.MyViewHolder>{
    Context context;
    List<ReviewImage> array;

    public ImageFeedbackAdapter(Context context, List<ReviewImage> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public ImageFeedbackAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_feedback_image, null);
        ImageFeedbackAdapter.MyViewHolder myViewHolder = new ImageFeedbackAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageFeedbackAdapter.MyViewHolder holder, int position) {
        ReviewImage image = array.get(position);
        Glide.with(context)
                .load(image.getImage())
                .into(holder.imgFeedback);

    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgFeedback;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFeedback = itemView.findViewById(R.id.imgFeedback);
        }
    }
}
