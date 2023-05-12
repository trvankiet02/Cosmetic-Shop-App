package vn.iotstar.cosmeticshopapp.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.ThemSuaDiaChiActivity;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Address;
import vn.iotstar.cosmeticshopapp.model.AddressResponse;
import vn.iotstar.cosmeticshopapp.model.Category;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;

public class DiaChiAdapter extends RecyclerView.Adapter<DiaChiAdapter.MyViewHolder>{
    Context context;
    List<Address> array;
    public DiaChiAdapter(Context context, List<Address> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public DiaChiAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_diachicuatoi, null);
        DiaChiAdapter.MyViewHolder myViewHolder = new DiaChiAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DiaChiAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Address address = array.get(position);
        holder.tvHoTen.setText(address.getFirstName() + " " + address.getLastName());
        holder.tvSoDienThoai.setText(address.getPhone());
        holder.tvDiaChi.setText(address.getAddress());
        if (address.getIsDefault() == true){
            holder.tvMacDinh.setVisibility(View.VISIBLE);
            holder.radio_button.setChecked(true);
        } else {
            holder.tvMacDinh.setVisibility(View.INVISIBLE);
            holder.radio_button.setVisibility(View.INVISIBLE);
            holder.radio_button.setChecked(false);
        }
        holder.builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                holder.progressDialog.setMessage("Đang xóa...");
                holder.progressDialog.show();
                holder.apiService.deleteAddress(address.getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            holder.progressDialog.dismiss();
                            array.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeRemoved(position, 1);
                            notifyItemRangeChanged(position, array.size());
                        } else {
                            holder.progressDialog.dismiss();
                            Log.d("TAG", "onResponse: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        holder.progressDialog.dismiss();
                        Log.d("TAG", "onFailure: " + t.getMessage());
                    }
                });
            }
        });
        holder.builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi người dùng nhấn nút "Hủy" hoặc bấm ngoài dialog
                dialog.dismiss(); // Đóng dialog
            }
        });
        holder.builder.setTitle("Xác nhận"); // Tiêu đề của dialog
        holder.builder.setMessage("Bạn có chắc chắn muốn xóa?"); // Nội dung của dialog
        holder.dialog = holder.builder.create();
        holder.tvDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chuyển qua ThemSuaDiaChiActivity
                Intent intent = new Intent(view.getContext(), ThemSuaDiaChiActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("address", address);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
        holder.ImgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvHoTen, tvSoDienThoai, tvDiaChi, tvMacDinh;
        RadioButton radio_button;
        ImageView ImgXoa;
        AlertDialog.Builder builder;
        AlertDialog dialog;
        APIService apiService;
        ProgressDialog progressDialog;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            anhXa();
        }

        private void anhXa() {
            tvHoTen = itemView.findViewById(R.id.tvHoTen);
            tvSoDienThoai = itemView.findViewById(R.id.tvSoDienThoai);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChi);
            tvMacDinh = itemView.findViewById(R.id.tvMacDinh);
            radio_button = itemView.findViewById(R.id.radio_button);
            ImgXoa = itemView.findViewById(R.id.ImgXoa);
            builder = new AlertDialog.Builder(itemView.getContext());
            apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
            progressDialog = new ProgressDialog(itemView.getContext());
        }
    }
}
