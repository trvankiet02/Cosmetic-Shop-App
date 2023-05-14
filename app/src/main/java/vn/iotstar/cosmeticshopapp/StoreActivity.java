package vn.iotstar.cosmeticshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.adapter.ProductHomeAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.fragment_home.MoiFragment;
import vn.iotstar.cosmeticshopapp.model.FollowStoreResponse;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.model.ProductResponse;
import vn.iotstar.cosmeticshopapp.model.Store;
import vn.iotstar.cosmeticshopapp.model.User;
import vn.iotstar.cosmeticshopapp.model.UserFollowStore;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;

public class StoreActivity extends AppCompatActivity {
    ImageView ivStoreImage, ivFavouriteProduct, ivCart, img_background;
    SearchView search_view;
    TextView tvStoreName, tvEmail, tvStar, tvFollowCount;
    Button btnFollow;
    APIService apiService;
    SharedPrefManager sharedPrefManager;
    User user;
    RecyclerView rvProduct;
    List<Product> soldProductList;
    List<Product> newProductList;
    List<Product> allProductList;
    ProductHomeAdapter productHomeAdapter;
    TextView tv_SoldProduct, tv_NewProduct, tv_AllProduct;
    Store store;
    boolean isFollow;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        anhXa();
        //nhận dữ liệu
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            store = (Store) bundle.get("store");
            getStoreInfo();
            getProductList();
            setClick();
        }
        setBtnFollow();
        setSearchView();
    }
    private void reloadActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


    private void setBtnFollow() {
        apiService.getFollowStore(sharedPrefManager.getUser().getId(), store.getId()).enqueue(new Callback<FollowStoreResponse>() {
            @Override
            public void onResponse(Call<FollowStoreResponse> call, Response<FollowStoreResponse> response) {
                btnFollow.setBackgroundResource(R.drawable.white_background);
                btnFollow.setTextColor(getResources().getColor(R.color.black));
                isFollow = false;
                if (response.isSuccessful()){
                    Log.d("TAG", "onResponse: " + response.body().getBody().size());
                    if (response.body().getBody().size() > 0){
                        btnFollow.setBackgroundResource(R.drawable.black_background);
                        btnFollow.setTextColor(getResources().getColor(R.color.white));
                        isFollow = true;
                    } else {
                        btnFollow.setBackgroundResource(R.drawable.white_background);
                        btnFollow.setTextColor(getResources().getColor(R.color.black));
                        isFollow = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<FollowStoreResponse> call, Throwable t) {

            }
        });
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPrefManager.getUser().getId() == -1){
                    Intent intent = new Intent(StoreActivity.this, LoginSignupActivity.class);
                    startActivity(intent);
                    Toast.makeText(StoreActivity.this, "Vui lòng đăng nhập để thực hiện chức năng này", Toast.LENGTH_SHORT).show();
                }
                progressDialog.show();
                if (isFollow){
                    apiService.followStore(sharedPrefManager.getUser().getId(), store.getId()).enqueue(new Callback<FollowStoreResponse>() {
                        @Override
                        public void onResponse(Call<FollowStoreResponse> call, Response<FollowStoreResponse> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()){
                                btnFollow.setBackgroundResource(R.drawable.white_background);
                                btnFollow.setTextColor(getResources().getColor(R.color.black));
                                isFollow = false;
                            }
                        }

                        @Override
                        public void onFailure(Call<FollowStoreResponse> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });
                } else {
                    apiService.followStore(sharedPrefManager.getUser().getId(), store.getId()).enqueue(new Callback<FollowStoreResponse>() {
                        @Override
                        public void onResponse(Call<FollowStoreResponse> call, Response<FollowStoreResponse> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()){
                                btnFollow.setBackgroundResource(R.drawable.black_background);
                                btnFollow.setTextColor(getResources().getColor(R.color.white));
                                isFollow = true;
                            }
                        }

                        @Override
                        public void onFailure(Call<FollowStoreResponse> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void setClick() {
        tv_AllProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllProductList();
            }
        });
        tv_NewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNewProductList();
            }
        });
        tv_SoldProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProductList();
            }
        });
    }

    private void getAllProductList() {
        apiService.getProductByStore(store.getId(), true).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()){
                    allProductList = response.body().getBody();
                    productHomeAdapter = new ProductHomeAdapter(StoreActivity.this, allProductList);
                    rvProduct.setAdapter(productHomeAdapter);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(StoreActivity.this, 2);
                    rvProduct.setLayoutManager(gridLayoutManager);
                    rvProduct.addItemDecoration(new StoreActivity.LinePagerIndicatorDecoration());
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });
    }

    private void getNewProductList() {
        apiService.getNewProduct(store.getId()).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()){
                    newProductList = response.body().getBody();
                    productHomeAdapter = new ProductHomeAdapter(StoreActivity.this, newProductList);
                    rvProduct.setAdapter(productHomeAdapter);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(StoreActivity.this, 2);
                    rvProduct.setLayoutManager(gridLayoutManager);
                    rvProduct.addItemDecoration(new StoreActivity.LinePagerIndicatorDecoration());
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });
    }

    private void getStoreInfo() {
        tvStoreName.setText(store.getName());
        tvEmail.setText(store.getEmail());
        tvStar.setText(String.valueOf(Math.round(store.getRating()*10.0) /10.0));
        tvFollowCount.setText(String.valueOf(store.getUserFollowStores().size() + " Người theo dõi"));
        Glide.with(this).load(store.getStoreImage()).into(ivStoreImage);
        if(store.getFeatureImage().equals("")){
            Glide.with(this).load(store.getFeatureImage()).into(img_background);
        }
    }

    private void getProductList() {
        apiService.getSoldProduct(store.getId()).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()){
                    soldProductList = response.body().getBody();
                    productHomeAdapter = new ProductHomeAdapter(StoreActivity.this, soldProductList);
                    rvProduct.setAdapter(productHomeAdapter);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(StoreActivity.this, 2);
                    rvProduct.setLayoutManager(gridLayoutManager);
                    rvProduct.addItemDecoration(new StoreActivity.LinePagerIndicatorDecoration());
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });

    }
    private void filterListener(String text) {
        List<Product> productList = new ArrayList<>();
        for (Product product: allProductList){
            if (product.getName().toLowerCase().contains(text.toLowerCase())
                    || product.getStore().getName().toLowerCase().contains(text.toLowerCase())){
                productList.add(product);
            }
            if (productList.size() == 0) {
                rvProduct.setVisibility(View.GONE);
            } else {
                productHomeAdapter.updateProduct(productList);
            }
        }
    }
    private void setSearchView() {
        search_view.clearFocus();
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.equals("")){
                    productHomeAdapter.updateProduct(soldProductList);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filterListener(newText);
                return true;
            }
        });
    }

    private void anhXa() {
        ivStoreImage = findViewById(R.id.ivStoreImage);
        img_background = findViewById(R.id.img_background);
        tvStoreName = findViewById(R.id.tvStoreName);
        tvEmail = findViewById(R.id.tvEmail);
        tvStar = findViewById(R.id.tvStar);
        tvFollowCount = findViewById(R.id.tvUserFollow);
        btnFollow = findViewById(R.id.btnFollow);
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        sharedPrefManager = new SharedPrefManager(this);
        user = sharedPrefManager.getUser();
        rvProduct = findViewById(R.id.rvProduct);
        soldProductList = new ArrayList<>();
        newProductList = new ArrayList<>();
        tv_SoldProduct = findViewById(R.id.tv_SoldProduct);
        tv_NewProduct = findViewById(R.id.tv_NewProduct);
        tv_AllProduct = findViewById(R.id.tv_AllProduct);
        search_view = findViewById(R.id.search_view);
        ivFavouriteProduct = findViewById(R.id.ivFavouriteProduct);
        ivCart = findViewById(R.id.ivCart);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        allProductList = new ArrayList<>();
    }
    public class LinePagerIndicatorDecoration extends RecyclerView.ItemDecoration {

        private int colorActive = 0xFFFFFFFF;
        private int colorInactive = 0x66FFFFFF;

        private  final float DP = Resources.getSystem().getDisplayMetrics().density;

        /**
         * Height of the space the indicator takes up at the bottom of the view.
         */
        private final int mIndicatorHeight = (int) (DP * 16);

        /**
         * Indicator stroke width.
         */
        private final float mIndicatorStrokeWidth = DP * 2;

        /**
         * Indicator width.
         */
        private final float mIndicatorItemLength = DP * 16;
        /**
         * Padding between indicators.
         */
        private final float mIndicatorItemPadding = DP * 4;

        /**
         * Some more natural animation interpolation
         */
        private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

        private final Paint mPaint = new Paint();

        public LinePagerIndicatorDecoration() {
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(mIndicatorStrokeWidth);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setAntiAlias(true);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);

            int itemCount = parent.getAdapter().getItemCount();

            // center horizontally, calculate width and subtract half from center
            float totalLength = mIndicatorItemLength * itemCount;
            float paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding;
            float indicatorTotalWidth = totalLength + paddingBetweenItems;
            float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2F;

            // center vertically in the allotted space
            float indicatorPosY = parent.getHeight() - mIndicatorHeight / 2F;

            drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount);


            // find active page (which should be highlighted)
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            int activePosition = layoutManager.findFirstVisibleItemPosition();
            if (activePosition == RecyclerView.NO_POSITION) {
                return;
            }

            // find offset of active page (if the user is scrolling)
            final View activeChild = layoutManager.findViewByPosition(activePosition);
            int left = activeChild.getLeft();
            int width = activeChild.getWidth();

            // on swipe the active item will be positioned from [-width, 0]
            // interpolate offset for smooth animation
            float progress = mInterpolator.getInterpolation(left * -1 / (float) width);

            drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress, itemCount);
        }

        private void drawInactiveIndicators(Canvas c, float indicatorStartX, float indicatorPosY, int itemCount) {
            mPaint.setColor(colorInactive);

            // width of item indicator including padding
            final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

            float start = indicatorStartX;
            for (int i = 0; i < itemCount; i++) {
                // draw the line for every item
                c.drawLine(start, indicatorPosY, start + mIndicatorItemLength, indicatorPosY, mPaint);
                start += itemWidth;
            }
        }

        private void drawHighlights(Canvas c, float indicatorStartX, float indicatorPosY,
                                    int highlightPosition, float progress, int itemCount) {
            mPaint.setColor(colorActive);

            // width of item indicator including padding
            final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

            if (progress == 0F) {
                // no swipe, draw a normal indicator
                float highlightStart = indicatorStartX + itemWidth * highlightPosition;
                c.drawLine(highlightStart, indicatorPosY,
                        highlightStart + mIndicatorItemLength, indicatorPosY, mPaint);
            } else {
                float highlightStart = indicatorStartX + itemWidth * highlightPosition;
                // calculate partial highlight
                float partialLength = mIndicatorItemLength * progress;

                // draw the cut off highlight
                c.drawLine(highlightStart + partialLength, indicatorPosY,
                        highlightStart + mIndicatorItemLength, indicatorPosY, mPaint);

                // draw the highlight overlapping to the next item as well
                if (highlightPosition < itemCount - 1) {
                    highlightStart += itemWidth;
                    c.drawLine(highlightStart, indicatorPosY,
                            highlightStart + partialLength, indicatorPosY, mPaint);
                }
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = mIndicatorHeight;
        }
    }
}