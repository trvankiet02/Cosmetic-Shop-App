package vn.iotstar.cosmeticshopapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.adapter.CategoryHomeAdapter;
import vn.iotstar.cosmeticshopapp.adapter.ProductHomeAdapter;
import vn.iotstar.cosmeticshopapp.adapter.StyleOfStyleAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Category;
import vn.iotstar.cosmeticshopapp.model.CategoryAndStyleResponse;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.model.ProductResponse;
import vn.iotstar.cosmeticshopapp.model.Style;
import vn.iotstar.cosmeticshopapp.model.StyleByCategoryResponse;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;

public class StyleActivity extends AppCompatActivity {
    RecyclerView rcStyle, rvProduct;
    APIService apiService;
    ProductHomeAdapter productHomeAdapter;
    List<Product> products;
    StyleOfStyleAdapter styleOfStyleAdapter;
    List<Style> styleList;
    Style style;
    Category cate;
    TextView tvTitle;
    ImageView imgBack, imgCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style);
        anhXa();
        getCategoryFromAdapter();
        getStyleFromAdapter();
        //set 2 recycler view
        setStyleRecyclerView();
        setProductRecyclerView();
        setImgBack();
        setImgCart();
    }

    private void setImgBack() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setImgCart() {
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StyleActivity.this, GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void anhXa() {
        rcStyle = findViewById(R.id.rcStyle);
        rvProduct = findViewById(R.id.rvProduct);
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        styleList = new ArrayList<>();
        tvTitle = findViewById(R.id.tvTitle);
        imgBack = findViewById(R.id.ivBack);
        imgCart = findViewById(R.id.ivCart);
    }

    private void getCategoryFromAdapter() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        cate = (Category) bundle.getSerializable("category");
        //Log.d("TAG", "getCategoryFromMain: " + cate.getName());
    }

    private void getStyleFromAdapter() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        style = (Style) bundle.getSerializable("style");
        //Log.d("TAG", "getProductFromAdapter: " + style.getId());
    }

    private void setStyleRecyclerView() {
        if (cate != null){
            style = cate.getStyles().get(0);
            tvTitle.setText(cate.getName());
        } else {
            apiService.getCategoryByStyle(style.getId()).enqueue(new Callback<CategoryAndStyleResponse>() {
                @Override
                public void onResponse(Call<CategoryAndStyleResponse> call, Response<CategoryAndStyleResponse> response) {
                    if (response.isSuccessful()) {
                        CategoryAndStyleResponse categoryAndStyleResponse = response.body();
                        cate = categoryAndStyleResponse.getBody().get(0);
                        tvTitle.setText(cate.getName());
                    } else {
                        Log.e("TAG", "onResponse: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<CategoryAndStyleResponse> call, Throwable t) {

                }
            });
        }
        apiService.getStyleSelling(style.getId()).enqueue(new Callback<StyleByCategoryResponse>() {
            @Override
            public void onResponse(Call<StyleByCategoryResponse> call, Response<StyleByCategoryResponse> response) {
                if (response.isSuccessful()) {
                    Style allStyle = new Style();
                    styleList = response.body().getBody();
                    styleOfStyleAdapter = new StyleOfStyleAdapter(StyleActivity.this, styleList);
                    rcStyle.setHasFixedSize(true);
                    GridLayoutManager layoutManager = new GridLayoutManager(StyleActivity.this, 1, RecyclerView.HORIZONTAL, false);
                    rcStyle.setLayoutManager(layoutManager);
                    rcStyle.setAdapter(styleOfStyleAdapter);
                    styleOfStyleAdapter.setOnItemClickListener(new StyleOfStyleAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Style style) {
                            apiService.getProductByStyle(style.getId(), true).enqueue(new Callback<ProductResponse>() {
                                @Override
                                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                                    if (response.isSuccessful()) {
                                        products = response.body().getBody();
                                        if (products == null) {
                                            Log.e("TAG", "onResponse: " + "NULL");
                                        }
                                        productHomeAdapter = new ProductHomeAdapter(StyleActivity.this, products);
                                        rvProduct.setHasFixedSize(true);
                                        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);

                                        rvProduct.setLayoutManager(layoutManager);
                                        rvProduct.setAdapter(productHomeAdapter);
                                        productHomeAdapter.notifyDataSetChanged();
                                    } else {
                                        Log.e("TAG", "onResponse: " + response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<ProductResponse> call, Throwable t) {

                                }
                            });
                        }
                    });
                    styleOfStyleAdapter.notifyDataSetChanged();
                } else {
                    Log.e("TAG", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<StyleByCategoryResponse> call, Throwable t) {

            }
        });
    }

    private void setProductRecyclerView() {
        apiService.getProductByStyle(style.getId(), true).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    products = response.body().getBody();
                    if (products == null) {
                        Log.e("TAG", "onResponse: " + "NULL");
                    }
                    productHomeAdapter = new ProductHomeAdapter(StyleActivity.this, products);
                    rvProduct.setHasFixedSize(true);
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);

                    rvProduct.setLayoutManager(layoutManager);
                    rvProduct.setAdapter(productHomeAdapter);
                    productHomeAdapter.notifyDataSetChanged();
                } else {
                    Log.e("TAG", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });
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