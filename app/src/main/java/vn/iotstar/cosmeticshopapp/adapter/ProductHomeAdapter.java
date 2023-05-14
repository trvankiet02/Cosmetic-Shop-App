package vn.iotstar.cosmeticshopapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import vn.iotstar.cosmeticshopapp.ChiTietSanPhamActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.model.Style;

public class ProductHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    Context context;
    List<Product> array;
    private boolean isLoading = false;
    private LoadMoreListener loadMoreListener;
    public interface LoadMoreListener {
        void onLoadMore();
    }
    public ProductHomeAdapter(Context context, List<Product> array) {
        this.context = context;
        this.array = array;
    }
    public void updateProduct(List<Product> productList) {
        this.array = productList;
        notifyDataSetChanged();
    }
    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_product_2column, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder);
        }

        // Kiểm tra nếu vị trí hiện tại là vị trí cuối cùng và trạng thái tải dữ liệu đang bật
        if (position == array.size() - 1 && isLoading && loadMoreListener != null) {
            loadMoreListener.onLoadMore();
        }
    }

    private void showLoadingView(LoadingViewHolder holder) {
        // Hiển thị ProgressBar hoặc thông báo tải dữ liệu
        holder.progressBar.setVisibility(View.VISIBLE);
    }

    private void populateItemRows(ItemViewHolder holder, int position) {
        Product product = array.get(position);
        holder.ProductName.setText(product.getName());

        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        holder.ProductPrice.setText(formatter.format(product.getPromotionalPrice()));

        SliderProductImageAdapter sliderProductImageAdapter = new SliderProductImageAdapter(context.getApplicationContext(), product.getProductImages(), 1);
        holder.sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        holder.sliderView.setSliderAdapter(sliderProductImageAdapter);
        holder.storeName.setText(product.getStore().getName());
        holder.tv_soluongdaban.setText(String.valueOf(product.getSold()));
        Glide.with(context).load(product.getStore().getStoreImage()).into(holder.storeImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chitiet = new Intent(context, ChiTietSanPhamActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", product);
                chitiet.putExtras(bundle);
                context.startActivity(chitiet);
                ((Activity) context).finish();
            }
        });
    }
    public void setLoaded() {
        isLoading = false;
    }

    public void addLoading() {
        isLoading = true;
        array.add(null);
        notifyItemInserted(array.size() - 1);
    }
    public void removeLoading() {
        isLoading = false;
        int position = array.size() - 1;
        Product item = array.get(position);
        if (item == null) {
            array.remove(position);
            notifyItemRemoved(position);
        }
    }
    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView ProductName, ProductPrice,storeName, tv_soluongdaban;
        SliderView sliderView;
        ImageView storeImage;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductName = itemView.findViewById(R.id.txtNameProduct);
            ProductPrice = (TextView) itemView.findViewById(R.id.txtPriceProduct);
            sliderView = itemView.findViewById(R.id.slider);
            storeName = itemView.findViewById(R.id.storeName);
            storeImage = itemView.findViewById(R.id.storeImage);
            tv_soluongdaban = itemView.findViewById(R.id.tv_soluongdaban);
        }
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    /*public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ProductName, ProductPrice;
        public SliderView sliderView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductPrice = (TextView) itemView.findViewById(R.id.txtPriceProduct);
            ProductName = (TextView) itemView.findViewById(R.id.txtNameProduct);
            sliderView = (SliderView) itemView.findViewById(R.id.slider);
        }
    }*/
}
