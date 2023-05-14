package vn.iotstar.cosmeticshopapp.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.GioHangActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.helper.SwipeHelper;
import vn.iotstar.cosmeticshopapp.model.AddToCartResponse;
import vn.iotstar.cosmeticshopapp.model.Cart;
import vn.iotstar.cosmeticshopapp.model.CartItem;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{
    Context context;
    List<Cart> array;
    List<CartItem> cartItemList = new ArrayList<>();
    List<Cart> returnCart = new ArrayList<>();
    Boolean isSelectAll = false;
    public CartAdapter(Context context, List<Cart> array) {
        this.context = context;
        this.array = array;
    }
    public List<CartItem> getSelectedCartItem (){
        return cartItemList;
    }

    public List<Cart> getSelectedCart() {
        return returnCart;
    }


    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_cart, null);
        CartAdapter.MyViewHolder myViewHolder = new CartAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        Cart cart = array.get(position);
        if (cart.getCartItems().size() >0 ) {
            Log.e("TAG", "onBindViewHolder: " + cart.getCartItems().size());
            for (CartItem cartItem : cart.getCartItems()) {
                cart.addCartItem(cartItem);
            }
            holder.storeName.setText(cart.getStore().getName());
            Glide.with(context).load(cart.getStore().getStoreImage()).into(holder.storeImage);
            holder.cartItemAdapter = new CartItemAdapter(context, cart.getCartItems());
            holder.rvCart_item.setAdapter(holder.cartItemAdapter);
            //holder.rvCart_item.setHasFixedSize(true);
            holder.rvCart_item.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
            holder.cartItemAdapter.notifyDataSetChanged();


            Cart newCart = cart;
            newCart.setCartItems(cartItemList);
            returnCart.add(newCart);

            holder.swipeHelper = new SwipeHelper(context.getApplicationContext(), holder.rvCart_item, false) {
                @Override
                public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                    Typeface typeface = Typeface.create("sans-serif", Typeface.SANS_SERIF.getStyle());
                    // Button Xóa
                    underlayButtons.add(new SwipeHelper.UnderlayButton(
                            "Xóa",
                            null, // icon set to null to remove image
                            Color.parseColor("#d31900"),
                            Color.parseColor("#FFFFFF"),
                            typeface,
                            new UnderlayButtonClickListener() {
                                @Override
                                public void onClick(int pos) {
                                    holder.progressDialog.show();
                                    holder.apiService.deleteCartItem(cart.getCartItems().get(pos).getId()).enqueue(new Callback<AddToCartResponse>() {
                                        @Override
                                        public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                                            holder.progressDialog.dismiss();
                                            Toast.makeText(
                                                    context,
                                                    "Đã xóa",
                                                    Toast.LENGTH_SHORT
                                            ).show();
                                            cart.getCartItems().remove(pos);
                                            holder.cartItemAdapter.notifyItemRemoved(pos);
                                            holder.cartItemAdapter.notifyItemRangeRemoved(pos, 1);
                                            holder.cartItemAdapter.notifyItemRangeChanged(pos, cart.getCartItems().size());
                                            if (holder.cartItemAdapter.getItemCount() == 0) {
                                                holder.itemView.setVisibility(View.GONE);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                                        }
                                    });

                                }
                            }
                    ));
                }
            };
            cartItemList = holder.cartItemAdapter.getSelectedCartItemList();

        } else {
            holder.itemView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvCart_item;
        CartItemAdapter cartItemAdapter;
        ImageView storeImage;
        TextView storeName;
        SwipeHelper swipeHelper;
        APIService apiService;
        ProgressDialog progressDialog;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            anhXa();
        }

        private void anhXa() {
            rvCart_item = itemView.findViewById(R.id.rvCart_item);
            storeImage = itemView.findViewById(R.id.storeImage);
            storeName = itemView.findViewById(R.id.storeName);
            apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Vui lòng đợi");
            progressDialog.setMessage("Đang xử lý...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
    }
}
