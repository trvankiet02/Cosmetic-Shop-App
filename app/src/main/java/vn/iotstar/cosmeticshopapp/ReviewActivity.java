package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.adapter.FeedbackAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.model.Review;
import vn.iotstar.cosmeticshopapp.model.ReviewResponse;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;

public class ReviewActivity extends AppCompatActivity {

    RatingBar rate_sanpham_duoi;
    ProgressBar progressBar_nho, progressBar_vua, progressBar_lon;
    TextView tv_rate_sanpham_duoi, tv_nho, tv_vua, tv_lon, tv_rate_num;
    Product product;
    LinearLayout lnReview;
    RecyclerView rvReview;
    List<Review> reviews;
    APIService apiService;
    FeedbackAdapter reviewAdapter;
    ImageView imgLike;
    SharedPrefManager sharedPrefManager;
    ProgressDialog progressDialog;
    boolean isLiked;
    DecimalFormat df = new DecimalFormat("#.#");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        AnhXa();
        getProductId();
        setRvReview();
    }

    private void AnhXa() {
        rvReview = (RecyclerView) findViewById(R.id.rvFeedback);
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        rate_sanpham_duoi = (RatingBar) findViewById(R.id.rate_sanpham_duoi);
        tv_rate_sanpham_duoi = (TextView) findViewById(R.id.tv_rate1);
        tv_nho = (TextView) findViewById(R.id.tv_progress_bar_nho);
        tv_vua = (TextView) findViewById(R.id.tv_progress_bar_vua);
        tv_lon = (TextView) findViewById(R.id.tv_progress_bar_lon);
        progressBar_nho = (ProgressBar) findViewById(R.id.progress_bar_nho);
        progressBar_vua = (ProgressBar) findViewById(R.id.progress_bar_vua);
        progressBar_lon = (ProgressBar) findViewById(R.id.progress_bar_lon);
        lnReview = (LinearLayout) findViewById(R.id.lnReview);
        tv_rate_num = (TextView) findViewById(R.id.tv_rate_num);

        sharedPrefManager = new SharedPrefManager(this);
        progressDialog = new ProgressDialog(this);
        setProgressDialog();
    }

    private void setProgressDialog() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
    private void getProductId() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        product = (Product) bundle.getSerializable("product");
    }
    private float avgRating(List<Review> reviews){
        float avg = 0;
        if (reviews.size() == 0){
            return 0.0f;
        }
        for (Review review : reviews){
            avg += review.getRating();
        }
        return avg/reviews.size();
    }
    private void setRatingTable(List<Review> reviewList, ProgressBar nho, ProgressBar vua, ProgressBar lon,
                                TextView tvNho, TextView tvVua, TextView tvLon){
        int nhoCount = 0;
        int vuaCount = 0;
        int lonCount = 0;
        for (Review review : reviewList){
            if (review.getRating() <= 2){
                nhoCount++;
            }
            else if (review.getRating() == 5){
                lonCount++;
            }
            else {
                vuaCount++;
            }
        }
        Log.e("TAG", "setRatingTable: " + nhoCount*100/reviewList.size() + " " + vuaCount*100/reviewList.size() + " " + lonCount*100/reviewList.size() + " " + reviewList.size());
        nho.setProgress(nhoCount*100/reviewList.size());
        tvNho.setText(String.valueOf(nhoCount*100/reviewList.size()));
        vua.setProgress(vuaCount*100/reviewList.size());
        tvVua.setText(String.valueOf(vuaCount*100/reviewList.size()));
        lon.setProgress(lonCount*100/reviewList.size());
        tvLon.setText(String.valueOf(lonCount*100/reviewList.size()));
    }

    private void setRvReview(){
        apiService.getReview(product.getId()).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful()){
                    reviews = response.body().getBody();
                    if(reviews.size() == 0){
                        lnReview.setVisibility(View.GONE);
                    }
                    else {
                        setRatingTable(reviews, progressBar_nho, progressBar_vua, progressBar_lon,
                                tv_nho, tv_vua, tv_lon);
                        tv_rate_sanpham_duoi.setText(String.valueOf(reviews.size()));
                        tv_rate_num.setText(df.format(avgRating(reviews)));
                        rate_sanpham_duoi.setRating(avgRating(reviews));

                        reviewAdapter = new FeedbackAdapter(getApplicationContext(), reviews);
                        rvReview.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                        rvReview.setLayoutManager(layoutManager);
                        rvReview.setAdapter(reviewAdapter);
                        reviewAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {

            }
        });

    }
}