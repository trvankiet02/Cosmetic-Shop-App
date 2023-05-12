package vn.iotstar.cosmeticshopapp.fragment_home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.GioHangActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.adapter.CategorySideBarAdapter;
import vn.iotstar.cosmeticshopapp.adapter.StyleSideBarAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Category;
import vn.iotstar.cosmeticshopapp.model.CategoryAndStyleResponse;
import vn.iotstar.cosmeticshopapp.model.Style;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;


public class DanhmucFragment extends Fragment {
    View view;
    RecyclerView rvCategory;
    RecyclerView rvStyle;
    List<Category> categoryList;
    List<Style> styleList;
    CategorySideBarAdapter categorySideBarAdapter;
    StyleSideBarAdapter styleSideBarAdapter;
    APIService apiService;
    SharedPrefManager sharedPrefManager;
    ImageView ivFavouriteProduct, ivCart;
    public interface OnButtonClickListener {
        void onButtonClick();
    }
    private OnButtonClickListener onButtonClickListener;
    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.onButtonClickListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_danhmuc, container, false);

        AnhXa();
        setRvCategory();
        setIvCart();
        return view;

    }
    private void setRvCategory(){
        apiService.getCategory().enqueue(new Callback<CategoryAndStyleResponse>() {
            @Override
            public void onResponse(Call<CategoryAndStyleResponse> call, Response<CategoryAndStyleResponse> response) {
                if (response.isSuccessful()) {
                    categoryList = response.body().getBody();
                    categorySideBarAdapter = new CategorySideBarAdapter(getContext(), categoryList);
                    styleSideBarAdapter = new StyleSideBarAdapter(getContext(), categoryList.get(0).getStyles());

                    rvStyle.setHasFixedSize(true);
                    rvStyle.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    rvStyle.setAdapter(styleSideBarAdapter);

                    categorySideBarAdapter.setOnItemClickListener(new CategorySideBarAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Category category) {
                            styleSideBarAdapter.updateStyles(category.getStyles());
                        }
                    });

                    rvCategory.setHasFixedSize(true);
                    rvCategory.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    rvCategory.setAdapter(categorySideBarAdapter);
                }
            }

            @Override
            public void onFailure(Call<CategoryAndStyleResponse> call, Throwable t) {

            }
        });

    }
    private void AnhXa(){
        rvCategory = (RecyclerView) view.findViewById(R.id.rvCategory);
        rvStyle = (RecyclerView) view.findViewById(R.id.rvStyle);
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        sharedPrefManager = new SharedPrefManager(getContext());
        ivCart = (ImageView) view.findViewById(R.id.ivCart);
        ivFavouriteProduct = (ImageView) view.findViewById(R.id.ivFavouriteProduct);
        ivFavouriteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onButtonClickListener != null) {
                    onButtonClickListener.onButtonClick();
                }
            }
        });
    }
    private void setIvCart(){
        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
    }
}