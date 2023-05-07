package vn.iotstar.cosmeticshopapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;

import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.model.Feedback;
import vn.iotstar.cosmeticshopapp.model.Product;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder>{
    Context context;
    List<Feedback> array;

    public FeedbackAdapter(Context context, List<Feedback> array) {
        this.context = context;
        this.array = array;
    }
    public  List<Feedback> getModelList() {
        return array;
    }

    @NonNull
    @Override
    public FeedbackAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_feedback, null);
        FeedbackAdapter.MyViewHolder myViewHolder = new FeedbackAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameUser, tvDateFeedback, tvColorProduct, tvSizeProduct, tvFeedback, tvLike;
        ImageView imgFeedback1, imgFeedback2, imgFeedback3, imgLike;
        RatingBar rate;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        boolean isLiked = false;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            anhXa();
            setOnClick();

        }

        private void setOnClick() {
            imgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isLiked) {
                        imgLike.setBackgroundResource(R.drawable.img_icon_like);
                        int like = Integer.parseInt(tvLike.getText().toString());
                        like = like - 1;
                        tvLike.setText(String.valueOf(like));
                        isLiked = false;
                    } else {
                        imgLike.setBackgroundResource(R.drawable.img_icon_liked);
                        int like = Integer.parseInt(tvLike.getText().toString());
                        like = like + 1;
                        tvLike.setText(String.valueOf(like));
                        isLiked = true;
                    }
                }
            });
        }

        private void anhXa() {
            tvNameUser = itemView.findViewById(R.id.tvNameUser);
            tvDateFeedback = itemView.findViewById(R.id.tvDateFeedback);
            tvColorProduct = itemView.findViewById(R.id.tvColorProduct);
            tvSizeProduct = itemView.findViewById(R.id.tvSizeProduct);
            tvFeedback = itemView.findViewById(R.id.tvFeedback);
            tvLike = itemView.findViewById(R.id.tvLike);
            imgFeedback1 = itemView.findViewById(R.id.imgFeedback1);
            imgFeedback2 = itemView.findViewById(R.id.imgFeedback2);
            imgFeedback3 = itemView.findViewById(R.id.imgFeedback3);
            imgLike = itemView.findViewById(R.id.imgLike);
            rate = itemView.findViewById(R.id.rate);
        }

    }
}
