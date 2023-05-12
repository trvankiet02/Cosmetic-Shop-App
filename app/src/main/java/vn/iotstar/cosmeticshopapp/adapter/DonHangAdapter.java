package vn.iotstar.cosmeticshopapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.model.Address;
import vn.iotstar.cosmeticshopapp.model.Order;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder>{
    int trangThai;
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

    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_mautrangthai, storeImage, img_xoa;
        TextView tv_trangthai, tv_madonhang, tv_soluongsanpham, tv_giasanpham, storeName;
        TextView tv_mualai, tv_huydon, tv_danhan;
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
            tv_giasanpham = itemView.findViewById(R.id.tv_giasanpham);
            tv_mualai = itemView.findViewById(R.id.tv_mualai);
            tv_huydon = itemView.findViewById(R.id.tv_huydon);
            tv_danhan = itemView.findViewById(R.id.tv_danhan);
        }
    }
}
