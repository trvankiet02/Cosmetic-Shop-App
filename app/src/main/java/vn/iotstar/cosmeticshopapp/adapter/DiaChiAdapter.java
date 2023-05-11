package vn.iotstar.cosmeticshopapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.ThemSuaDiaChiActivity;
import vn.iotstar.cosmeticshopapp.model.Address;
import vn.iotstar.cosmeticshopapp.model.Category;

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
    public void onBindViewHolder(@NonNull DiaChiAdapter.MyViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chuyển qua ThemSuaDiaChiActivity
                Intent intert = new Intent(view.getContext(), ThemSuaDiaChiActivity.class);
                view.getContext().startActivity(intert);
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
        }
    }
}
