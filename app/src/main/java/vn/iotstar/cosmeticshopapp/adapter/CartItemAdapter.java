package vn.iotstar.cosmeticshopapp.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.AddToCartResponse;
import vn.iotstar.cosmeticshopapp.model.CartItem;
import vn.iotstar.cosmeticshopapp.model.ProductQuantity;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.MyViewHolder>{
    Context context;
    List<CartItem> array;
    ArrayList<CartItem> selectedCartItems;

    public CartItemAdapter(Context context, List<CartItem> array) {
        this.context = context;
        this.array = array;
        selectedCartItems = new ArrayList<>();
    }
    public List<CartItem> getSelectedCartItemList() {
        return selectedCartItems;
    }
    public  List<CartItem> getModelList() {
        return array;
    }


    @NonNull
    @Override
    public CartItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_cart_item, null);
        CartItemAdapter.MyViewHolder myViewHolder = new CartItemAdapter.MyViewHolder(view);


        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CartItem cartItem = array.get(position);
        holder.checkBox.setChecked(selectedCartItems.contains(cartItem));
        holder.ProductName.setText(cartItem.getProduct().getName());
        holder.ProductPrice.setText(String.valueOf(cartItem.getProduct().getPromotionalPrice()) + "đ");
        holder.txtQuantity.setText(String.valueOf(cartItem.getQuantity()));
        Glide.with(context).load(cartItem.getProduct().getProductImages().get(0).getImage()).into(holder.ProductImage);
        holder.txtSize.setText(cartItem.getSize());
        List<String> sizes = new ArrayList<>();
        for (ProductQuantity productQuantity : cartItem.getProduct().getProductQuantities()) {
            sizes.add(productQuantity.getSize());
        }
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, sizes);

        holder.sizeSpinner.setAdapter(sizeAdapter);

        // Thiết lập giá trị được chọn trong Spinner
        int selectedIndex = sizes.indexOf(cartItem.getSize());
        holder.sizeSpinner.setSelection(selectedIndex);
        Integer cartId = cartItem.getCart().getId();


        // Thiết lập sự kiện chọn giá trị trong Spinner
        holder.sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onItemSelectedHandler(parent, view, position, id);
                if (!holder.sizeSpinner.getSelectedItem().toString().equals(holder.txtSize.getText().toString())){
                    holder.progressDialog.show();
                    CartItem newCartItem = new CartItem(cartItem.getId(), cartItem.getProduct(),
                            holder.sizeSpinner.getSelectedItem().toString(),
                            cartItem.getQuantity(), cartItem.getCreateAt(), cartItem.getUpdateAt());
                    holder.apiService.updateCartItem(newCartItem, cartId).enqueue(new Callback<AddToCartResponse>() {
                        @Override
                        public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                holder.progressDialog.dismiss();
                                holder.txtSize.setText(holder.sizeSpinner.getSelectedItem().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<AddToCartResponse> call, Throwable t) {

                        }
                    });
                    /*new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            holder.progressDialog.dismiss();
                            holder.txtSize.setText(holder.sizeSpinner.getSelectedItem().toString());
                        }
                    }, 2000);*/
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        holder.btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.progressDialog.show();
                int newQuantity = Integer.parseInt(holder.txtQuantity.getText().toString()) + 1;
                if (newQuantity > cartItem.getProduct().getProductQuantities().get(selectedIndex).getQuantity()) {
                    Toast.makeText(context, "Số lượng sản phẩm không đủ", Toast.LENGTH_SHORT).show();
                    holder.progressDialog.dismiss();
                    return;
                } else {
                    //holder.txtQuantity.setText(String.valueOf(newQuantity));
                    CartItem newCartItem = new CartItem(cartItem.getId(), cartItem.getProduct(),
                            holder.sizeSpinner.getSelectedItem().toString(),
                            newQuantity, cartItem.getCreateAt(), cartItem.getUpdateAt());
                    holder.apiService.updateCartItem(newCartItem, cartId).enqueue(new Callback<AddToCartResponse>() {
                        @Override
                        public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                holder.progressDialog.dismiss();
                                holder.txtQuantity.setText(String.valueOf(newQuantity));
                            }
                        }
                        @Override
                        public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                            Log.e("TAG", "onFailure: " + t.getMessage());
                        }
                    });
                }
            }
        });
        holder.btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.progressDialog.show();
                int newQuantity = Integer.parseInt(holder.txtQuantity.getText().toString()) - 1;
                if (newQuantity < 1) {
                    Toast.makeText(context, "Số lượng sản phẩm không hợp lệ", Toast.LENGTH_SHORT).show();
                    holder.progressDialog.dismiss();
                    return;
                } else {
                    //holder.txtQuantity.setText(String.valueOf(newQuantity));
                    CartItem newCartItem = new CartItem(cartItem.getId(), cartItem.getProduct(),
                            holder.sizeSpinner.getSelectedItem().toString(),
                            newQuantity, cartItem.getCreateAt(), cartItem.getUpdateAt());
                    holder.apiService.updateCartItem(newCartItem, cartId).enqueue(new Callback<AddToCartResponse>() {
                        @Override
                        public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                holder.progressDialog.dismiss();
                                holder.txtQuantity.setText(String.valueOf(newQuantity));
                            }
                        }
                        @Override
                        public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                            Log.e("TAG", "onFailure: " + t.getMessage());
                        }
                    });
                }
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (selectedCartItems.contains(cartItem)){
                    selectedCartItems.remove(cartItem);
                    notifyDataSetChanged();
                } else {
                    selectedCartItems.add(cartItem);
                    notifyDataSetChanged();
                }
            }
        });
    }


    private void onItemSelectedHandler(AdapterView<?> parent, View view, int position, long id) {
        Adapter adapter = (Adapter) parent.getAdapter();
        String size = adapter.getItem(position).toString();
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ProductName, ProductPrice, txtSize, storeName, txtQuantity;
        Spinner sizeSpinner;
        public boolean isSwipeable;
        public ImageView ProductImage, storeImage;
        public ProgressDialog progressDialog;
        APIService apiService;
        ImageButton btnTang, btnGiam;
        public CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            isSwipeable = true;
            progressDialog = new ProgressDialog(itemView.getContext());
            progressDialog.setMessage("Đang cập nhật giỏ hàng...");
            //progressDialog.setCancelable(false);
            //progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
            anhXa();
        }

        private void anhXa() {
            sizeSpinner = itemView.findViewById(R.id.size_spinner);
            txtSize = itemView.findViewById(R.id.txtsize);
            ProductPrice = (TextView) itemView.findViewById(R.id.txtPriceProduct);
            ProductName = (TextView) itemView.findViewById(R.id.txtNameProduct);
            ProductImage = (ImageView) itemView.findViewById(R.id.imgProduct);
            storeName = itemView.findViewById(R.id.storeName);
            storeImage = itemView.findViewById(R.id.storeImage);
            txtQuantity = itemView.findViewById(R.id.count_product);
            btnTang = itemView.findViewById(R.id.btnTang);
            btnGiam = itemView.findViewById(R.id.btnGiam);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
