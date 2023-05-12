package vn.iotstar.cosmeticshopapp.fragment_product;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.adapter.CategoryHomeAdapter;
import vn.iotstar.cosmeticshopapp.adapter.ProductHomeAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.FollowProductResponse;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.model.User;
import vn.iotstar.cosmeticshopapp.model.UserFollowProduct;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;

public class FavouritedProductFragment extends Fragment {
    View view;
    RecyclerView rvFavouritedProduct;
    ProductHomeAdapter productHomeAdapter;
    APIService apiService;
    SharedPrefManager sharedPrefManager;
    User user;
    List<UserFollowProduct> userFollowProductList;
    List<Product> productList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favourited_product, container, false);
        rvFavouritedProduct = view.findViewById(R.id.rvFavouritedProduct);
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        sharedPrefManager = new SharedPrefManager(getContext());
        user = sharedPrefManager.getUser();
        productList = new ArrayList<>();
        userFollowProductList = new ArrayList<>();
        apiService.getFollowProduct(user.getId()).enqueue(new Callback<FollowProductResponse>() {
            @Override
            public void onResponse(Call<FollowProductResponse> call, Response<FollowProductResponse> response) {
                if (response.isSuccessful()){
                    userFollowProductList = response.body().getBody();
                    Log.e("TAG", "onResponse: " + userFollowProductList.size());
                    for (UserFollowProduct userFollowProduct : userFollowProductList) {
                        productList.add(userFollowProduct.getProduct());
                    }
                    productHomeAdapter = new ProductHomeAdapter(getContext(), productList);
                    rvFavouritedProduct.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
                    rvFavouritedProduct.setLayoutManager(layoutManager);
                    rvFavouritedProduct.setAdapter(productHomeAdapter);
                    productHomeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<FollowProductResponse> call, Throwable t) {

            }
        });

        return view;
    }
}