package vn.iotstar.cosmeticshopapp.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.ChiTietDonHangActivity;
import vn.iotstar.cosmeticshopapp.DanhGiaDonHangActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Address;
import vn.iotstar.cosmeticshopapp.model.NullBodyResponse;
import vn.iotstar.cosmeticshopapp.model.Order;
import vn.iotstar.cosmeticshopapp.model.OrderItem;
import vn.iotstar.cosmeticshopapp.model.SingleOrderResponse;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder>{
    Context context;
    List<Order> array;
    public DonHangAdapter(Context context, List<Order> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public DonHangAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_xulydonhang, null);
        DonHangAdapter.MyViewHolder myViewHolder = new DonHangAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangAdapter.MyViewHolder holder, int position) {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);

        Order order = array.get(position);
        setTrangThaiTongThe(holder, order, Integer.parseInt(order.getStatus().toString()));
        holder.tv_madonhang.setText(order.getId().toString());
        holder.storeName.setText(order.getStore().getName());
        Glide.with(context)
                .load(order.getStore().getStoreImage())
                .into(holder.storeImage);
        holder.tv_soluongsanpham.setText(String.valueOf(order.getOrderItems().size()));
        String formattedNumber = formatter.format(order.getPrice());
        holder.tv_giadonhang.setText(formattedNumber);
        //order.getOrderItems().get(0).getProduct();

        //adapter chuyển item.Image vào
        holder.orderItemAdapter = new OrderItemAdapter(context.getApplicationContext(), order.getOrderItems());
        holder.rcImageProductOder.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context.getApplicationContext(), 1, RecyclerView.HORIZONTAL, false);
        holder.rcImageProductOder.setLayoutManager(layoutManager);
        holder.rcImageProductOder.setAdapter(holder.orderItemAdapter);
        holder.orderItemAdapter.notifyDataSetChanged();

        //chuyển đến trang xem chi tiết đơn hàng
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ChiTietDonHangActivity.class);
                intent.putExtra("order", order);
                context.startActivity(intent);
            }
        });
        holder.builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                holder.progressDialog.setMessage("Đang xử lý...");
                holder.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                holder.progressDialog.show();
                //callAPI
                holder.apiService.cancelOrder(order.getId()).enqueue(new Callback<SingleOrderResponse>() {
                    @Override
                    public void onResponse(Call<SingleOrderResponse> call, Response<SingleOrderResponse> response) {
                        holder.progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            Toast.makeText(context.getApplicationContext(), "Hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context.getApplicationContext(), ChiTietDonHangActivity.class);
                            intent.putExtra("order", response.body().getBody());
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context.getApplicationContext(), "Hủy đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SingleOrderResponse> call, Throwable t) {
                        Log.e("TAG", "onFailure: " + t.getMessage());
                    }
                });

            }
        });
        holder.builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        holder.builder.setTitle("Xác nhận hành động");
        holder.builder.setMessage("Bạn có chắc chắn muốn hủy đơn hàng này?");
        holder.dialog = holder.builder.create();
    }

    public void setTrangThaiTongThe(DonHangAdapter.MyViewHolder holder, Order o,int trangThai){
        //đổi img màu, đổi txt trạng thái, ẩn nút xóa,
        //đơn chờ xác nhận thì có thể Hủy đơn
        //đơn đã xác nhận thì không được hủy
        //đơn đang giao thì không được hủy, nút đã nhận rõ  bấm được
        //đơn giao xong thì sẽ k hủy, mà có nút trả lại, có nút feedback, nút đã nhận biến mất
        //đơn hủy thì có nút xóa đơn
        switch (trangThai) {
            case 0:
                // Xử lý khi trạng thái là hủy
                holder.img_mautrangthai.setBackgroundResource(R.drawable.background_tt_0);
                holder.tv_trangthai.setText("ĐÃ THU HỒI");
                holder.tv_danhan.setVisibility(View.GONE);
                holder.tv_huydon.setVisibility(View.GONE);
                holder.tv_danhgia.setVisibility(View.GONE);
                holder.img_xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            case 1:
                // Xử lý khi trạng thái là chờ xác nhận
                holder.img_mautrangthai.setBackgroundResource(R.drawable.background_tt_1);
                holder.tv_trangthai.setText("CHƯA XÁC NHẬN");
                holder.img_xoa.setVisibility(View.GONE);
                holder.tv_danhan.setVisibility(View.GONE);
                holder.tv_danhgia.setVisibility(View.GONE);
                holder.tv_huydon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.dialog.show();
                    }
                });
                break;
            case 2:
                // Xử lý khi trạng thái là đã xác nhận
                holder.img_mautrangthai.setBackgroundResource(R.drawable.background_tt_2);
                holder.tv_trangthai.setText("ĐÃ XÁC NHẬN");
                holder.img_xoa.setVisibility(View.GONE);
                holder.tv_danhan.setVisibility(View.GONE);
                holder.tv_huydon.setVisibility(View.GONE);
                holder.tv_danhgia.setVisibility(View.GONE);
                break;
            case 3:
                // Xử lý khi trạng thái là đang giao
                holder.img_mautrangthai.setBackgroundResource(R.drawable.background_tt_3);
                holder.tv_trangthai.setText("ĐANG GIAO HÀNG");
                holder.tv_danhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.progressDialog.setMessage("Đang xử lý...");
                        holder.progressDialog.show();
                        holder.apiService.receiveOrder(o.getId()).enqueue(new Callback<SingleOrderResponse>() {
                            @Override
                            public void onResponse(Call<SingleOrderResponse> call, Response<SingleOrderResponse> response) {
                                holder.progressDialog.dismiss();
                                if (response.isSuccessful() && response.code() == 200){
                                    Toast.makeText(context, "Đã nhận đơn hàng", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context.getApplicationContext(), ChiTietDonHangActivity.class);
                                    intent.putExtra("order", response.body().getBody());
                                    context.startActivity(intent);
                                } else {
                                    Toast.makeText(context, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<SingleOrderResponse> call, Throwable t) {
                                Log.e("TAG", "onFailure: " + t.getMessage());
                            }
                        });

                    }
                });
                holder.img_xoa.setVisibility(View.GONE);
                holder.tv_danhgia.setVisibility(View.GONE);
                holder.tv_huydon.setVisibility(View.GONE);
                break;
            case 4:
                // Xử lý khi trạng thái là đã nhận
                holder.img_mautrangthai.setBackgroundResource(R.drawable.background_tt_4);
                holder.tv_trangthai.setText("ĐÃ NHẬN HÀNG");
                holder.tv_huydon.setVisibility(View.GONE);
                holder.img_xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //callAPI

                    }
                });
                holder.tv_danhgia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //chuyển đến trang đánh giá
                        Intent intent = new Intent(context.getApplicationContext(), DanhGiaDonHangActivity.class);
                        intent.putExtra("order", o);
                        context.startActivity(intent);
                    }
                });
                holder.tv_danhan.setBackgroundResource(R.drawable.background_xam);
                holder.tv_danhan.setTextColor(ContextCompat.getColor(context, R.color.grey_dark));
                //nếu đơn đã đánh giá
//                if (o.getreview == 1) {
//                    holder.tv_danhgia.setBackgroundResource(R.drawable.background_xam);
//                    holder.tv_danhgia.setTextColor(ContextCompat.getColor(context, R.color.grey_dark));
//                    holder.tv_danhgia.setEnabled(false);
//                }
                if (o.getReview() != null){
                    holder.tv_danhgia.setBackgroundResource(R.drawable.background_xam);
                    holder.tv_danhgia.setTextColor(ContextCompat.getColor(context, R.color.grey_dark));
                    holder.tv_danhgia.setEnabled(false);
                }
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
        ImageView img_mautrangthai, storeImage, img_xoa;
        TextView tv_trangthai, tv_madonhang, tv_soluongsanpham, tv_giadonhang, storeName;
        TextView tv_danhgia, tv_huydon, tv_danhan;
        OrderItemAdapter orderItemAdapter;
        RecyclerView rcImageProductOder;
        AlertDialog.Builder builder;
        AlertDialog dialog;
        APIService apiService;
        ProgressDialog progressDialog;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            anhXa();
        }
        private void anhXa() {
            img_mautrangthai = itemView.findViewById(R.id.img_mautrangthai);
            storeImage = itemView.findViewById(R.id.storeImage3);
            storeName = itemView.findViewById(R.id.storeName3);
            img_xoa = itemView.findViewById(R.id.img_xoa);
            tv_trangthai = itemView.findViewById(R.id.tv_trangthai);
            tv_madonhang = itemView.findViewById(R.id.tv_madonhang);
            tv_soluongsanpham = itemView.findViewById(R.id.tv_soluongsanpham);
            tv_giadonhang = itemView.findViewById(R.id.tv_giadonhang);
            tv_danhgia = itemView.findViewById(R.id.tv_danhgia);
            tv_huydon = itemView.findViewById(R.id.tv_huydon);
            tv_danhan = itemView.findViewById(R.id.tv_danhan);
            rcImageProductOder = itemView.findViewById(R.id.rcImageProductOder);
            builder = new AlertDialog.Builder(itemView.getContext());
            apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
            progressDialog = new ProgressDialog(itemView.getContext());
        }
    }
}
