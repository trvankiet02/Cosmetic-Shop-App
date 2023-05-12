package vn.iotstar.cosmeticshopapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.model.Address;
import vn.iotstar.cosmeticshopapp.model.Order;

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
        Order order = array.get(position);
        setTrangThaiTongThe(holder, Integer.parseInt(order.getStatus().toString()));
        holder.tv_madonhang.setText(order.getId().toString());
        holder.storeName.setText(order.getStore().getName());
        Glide.with(context)
                .load(order.getStore().getStoreImage())
                .into(holder.storeImage);
        //holder.tv_soluongsanpham.setText(order.getOrderItems().size());
        holder.tv_giadonhang.setText(order.getPrice().toString());
        //order.getOrderItems().get(0).getProduct();
        //adapter chuyển item.Image vào
    }

    public void setTrangThaiTongThe(DonHangAdapter.MyViewHolder holder, int trangThai){
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

                    }
                });
                holder.tv_danhgia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                holder.tv_danhan.setBackgroundResource(R.drawable.background_xam);
                holder.tv_danhan.setTextColor(ContextCompat.getColor(context, R.color.grey_dark));
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
        }
    }
}
