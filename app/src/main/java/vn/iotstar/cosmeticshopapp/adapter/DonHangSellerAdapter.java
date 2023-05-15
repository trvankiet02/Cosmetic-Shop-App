package vn.iotstar.cosmeticshopapp.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Order;
import vn.iotstar.cosmeticshopapp.model.SingleOrderResponse;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;

public class DonHangSellerAdapter extends RecyclerView.Adapter<DonHangSellerAdapter.MyViewHolder> {
    Context context;
    List<Order> array;

    public DonHangSellerAdapter(Context context, List<Order> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public DonHangSellerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_xulydonhang_seller, null);
        DonHangSellerAdapter.MyViewHolder myViewHolder = new DonHangSellerAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangSellerAdapter.MyViewHolder holder, int position) {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);

        Order order = array.get(position);
        setTrangThaiTongThe(holder, order, Integer.parseInt(order.getStatus().toString()));
        holder.tv_madonhang.setText(order.getId().toString());
        holder.tv_diachi.setText(order.getAddress());
        holder.tv_sodienthoai.setText(order.getPhone());

        holder.tv_soluongsanpham.setText(String.valueOf(order.getOrderItems().size()));
        String formattedNumber = formatter.format(order.getPrice());
        holder.tv_giadonhang.setText(formattedNumber);

        //adapter chuyển item.Image vào
        holder.orderItemAdapter = new OrderItemAdapter(context.getApplicationContext(), order.getOrderItems());
        holder.rcImageProductOder.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context.getApplicationContext(), 1, RecyclerView.HORIZONTAL, false);
        holder.rcImageProductOder.setLayoutManager(layoutManager);
        holder.rcImageProductOder.setAdapter(holder.orderItemAdapter);
        holder.orderItemAdapter.notifyDataSetChanged();
        try {
            holder.tv_ngaydat.setText(holder.outputFormat.format(holder.inputFormat.parse(order.getCreateAt())));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        //chuyển đến trang xem chi tiết đơn hàng
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context.getApplicationContext(), ChiTietDonHangSellerActivity.class);
//                intent.putExtra("order", order);
//                context.startActivity(intent);
//            }
//        });
    }

    private void setTrangThaiTongThe(DonHangSellerAdapter.MyViewHolder holder, Order o, int trangThai) {
        //đổi img màu, đổi txt trạng thái, đổi text của tv_btn
        //đơn chờ xác nhận
        //đơn đã xác nhận
        //đơn đang giao
        //đơn giao xong
        switch (trangThai) {
            case 0:
                // Xử lý khi trạng thái là đã nhận
                holder.img_mautrangthai.setBackgroundResource(R.drawable.background_tt_0);
                holder.tv_trangthai.setText("ĐÃ HUỶ");
                holder.tv_btn.setText("ĐƠN ĐÃ HUỶ");
                holder.tv_btn.setBackgroundResource(R.drawable.background_xam);
                holder.tv_btn.setTextColor(ContextCompat.getColor(context, R.color.grey_dark));
                holder.tv_btn.setEnabled(false);
                break;
            case 1:
                // Xử lý khi trạng thái là chờ xác nhận
                holder.img_mautrangthai.setBackgroundResource(R.drawable.background_tt_1);
                holder.tv_trangthai.setText("CHƯA XÁC NHẬN");
                holder.tv_btn.setText("XÁC NHẬN");
                holder.tv_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.progressDialog.show();
                        holder.apiService.confirmOrder(o.getId()).enqueue(new Callback<SingleOrderResponse>() {
                            @Override
                            public void onResponse(Call<SingleOrderResponse> call, Response<SingleOrderResponse> response) {
                                holder.progressDialog.dismiss();
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "Xác nhận đơn hàng thành công", Toast.LENGTH_SHORT).show();
                                    o.setStatus(2);
                                    notifyItemChanged(holder.getAdapterPosition());
                                    array.remove(o);
                                    notifyDataSetChanged();
                                } else {
                                    Toast.makeText(context, "Xác nhận đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<SingleOrderResponse> call, Throwable t) {
                                Log.e("TAG", "onFailure: " + t.getMessage());
                            }
                        });
                    }
                });
                break;
            case 2:
                // Xử lý khi trạng thái là đã xác nhận
                holder.img_mautrangthai.setBackgroundResource(R.drawable.background_tt_2);
                holder.tv_trangthai.setText("ĐÃ XÁC NHẬN");
                holder.tv_btn.setText("GIAO HÀNG");
                holder.tv_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.progressDialog.show();
                        holder.apiService.shipOrder(o.getId()).enqueue(new Callback<SingleOrderResponse>() {
                            @Override
                            public void onResponse(Call<SingleOrderResponse> call, Response<SingleOrderResponse> response) {
                                holder.progressDialog.dismiss();
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "Chuyển hàng cho đơn vị vận chuyển", Toast.LENGTH_SHORT).show();
                                    o.setStatus(3);
                                    notifyItemChanged(holder.getAdapterPosition());
                                    array.remove(o);
                                    notifyDataSetChanged();
                                } else {
                                    Toast.makeText(context, "Chuyển đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<SingleOrderResponse> call, Throwable t) {
                                Log.e("TAG", "onFailure: " + t.getMessage());
                            }
                        });
                    }
                });
                break;
            case 3:
                // Xử lý khi trạng thái là đang giao
                holder.img_mautrangthai.setBackgroundResource(R.drawable.background_tt_3);
                holder.tv_trangthai.setText("ĐANG GIAO HÀNG");
                holder.tv_btn.setText("KẾT THÚC GIAO HÀNG");
                holder.tv_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "Đã giao hàng thành công", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 4:
                // Xử lý khi trạng thái là đã nhận
                holder.img_mautrangthai.setBackgroundResource(R.drawable.background_tt_4);
                holder.tv_trangthai.setText("ĐÃ NHẬN HÀNG");
                holder.tv_btn.setText("NGƯỜI NHẬN ĐÃ NHẬN HÀNG");
                holder.tv_btn.setBackgroundResource(R.drawable.background_xam);
                holder.tv_btn.setTextColor(ContextCompat.getColor(context, R.color.grey_dark));
                holder.tv_btn.setEnabled(false);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_mautrangthai;
        TextView tv_trangthai, tv_madonhang, tv_diachi, tv_sodienthoai, tv_ngaydat;
        TextView tv_soluongsanpham, tv_giadonhang;
        TextView tv_btn;
        RecyclerView rcImageProductOder;
        OrderItemAdapter orderItemAdapter;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        ProgressDialog progressDialog;
        APIService apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            anhXa();
        }

        private void anhXa() {
            img_mautrangthai = itemView.findViewById(R.id.img_mautrangthai);
            tv_trangthai = itemView.findViewById(R.id.tv_trangthai);
            tv_madonhang = itemView.findViewById(R.id.tv_madonhang);
            tv_diachi = itemView.findViewById(R.id.tv_diachi);
            tv_sodienthoai = itemView.findViewById(R.id.tv_sodienthoai);
            tv_ngaydat = itemView.findViewById(R.id.tv_ngaydat);
            tv_soluongsanpham = itemView.findViewById(R.id.tv_soluongsanpham);
            tv_giadonhang = itemView.findViewById(R.id.tv_giadonhang);
            tv_btn = itemView.findViewById(R.id.tv_btn);
            rcImageProductOder = itemView.findViewById(R.id.rcImageProductOder);
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Đang xử lý...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
    }
}
