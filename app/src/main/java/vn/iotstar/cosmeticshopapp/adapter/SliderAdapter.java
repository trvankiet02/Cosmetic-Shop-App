package vn.iotstar.cosmeticshopapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.io.Serializable;
import java.util.List;

import vn.iotstar.cosmeticshopapp.ChiTietSanPhamActivity;
import vn.iotstar.cosmeticshopapp.FullScreenActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.model.ProductImage;
import vn.iotstar.cosmeticshopapp.model.SliderData;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    // list for storing urls of images.
    private final List<ProductImage> mSliderItems;
    private final int REQUEST_CODE;

    // Constructor
    public SliderAdapter(Context context, List<ProductImage> sliderDataArrayList, int REQUEST_CODE) {
        this.mSliderItems = sliderDataArrayList;
        this.REQUEST_CODE = REQUEST_CODE;
    }

    // We are inflating the slider_layout
    // inside on Create View Holder method.
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdapterViewHolder(inflate);
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

        final ProductImage sliderItem = mSliderItems.get(position);

        // Glide is use to load image
        // from url in your imageview.
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImage())
                .fitCenter()
                .into(viewHolder.imageViewBackground);
        if (REQUEST_CODE == ChiTietSanPhamActivity.REQUEST_CODE){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), FullScreenActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("image_url", (Serializable) mSliderItems);
                    intent.putExtras(bundle);
                    ((Activity) view.getContext()).startActivityForResult(intent, 1);
                }
            });
        }

    }

    // this method will return
    // the count of our list.
    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    static class SliderAdapterViewHolder extends ViewHolder {
        // Adapter class for initializing
        // the views of our slider view.
        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.myimage);
            this.itemView = itemView;
        }
    }
}

