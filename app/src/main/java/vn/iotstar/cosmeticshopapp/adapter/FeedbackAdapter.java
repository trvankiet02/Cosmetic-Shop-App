package vn.iotstar.cosmeticshopapp.adapter;

import static java.lang.Float.parseFloat;

import android.content.Context;
import android.util.Log;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.model.Feedback;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.model.Review;
import vn.iotstar.cosmeticshopapp.model.ReviewImage;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder>{
    Context context;
    List<Review> array;

    public FeedbackAdapter(Context context, List<Review> array) {
        this.context = context;
        this.array = array;
    }
    public  List<Review> getModelList() {
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
    Review review = array.get(position);
        holder.tvNameUser.setText(review.getUser().getFirstName() + " " + review.getUser().getLastName());
        try {
            holder.tvDateFeedback.setText(holder.outputFormat.format(holder.inputFormat.parse(review.getCreateAt())));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        holder.tvColorProduct.setText("Thieu");
        holder.tvSizeProduct.setText("Thieu");
        holder.tvFeedback.setText(review.getContent());
        holder.tvLike.setText("Thieu");
        holder.rate.setRating(parseFloat(String.valueOf(review.getRating())));
        List<String> listImages = new ArrayList<>();
        for ( ReviewImage image : review.getReviewImages()) {
            listImages.add(image.getImage());
        }
        Glide.with(context)
                .load(listImages.get(0))
                .into(holder.imgFeedback1);
        /*Glide.with(context)
                .load(review.getReviewImages().get(1))
                .into(holder.imgFeedback2);
        Glide.with(context)
                .load(review.getReviewImages().get(2))
                .into(holder.imgFeedback3);*/
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameUser, tvDateFeedback, tvColorProduct, tvSizeProduct, tvFeedback, tvLike;
        ImageView imgFeedback1, imgFeedback2, imgFeedback3, imgLike;
        RatingBar rate;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
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
