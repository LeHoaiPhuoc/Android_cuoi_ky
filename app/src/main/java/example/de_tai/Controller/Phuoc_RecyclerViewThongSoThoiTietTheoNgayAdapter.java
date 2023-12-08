package example.de_tai.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import example.de_tai.Model.Phuoc_RecyclerViewThongSoThoiTietTheoNgay;
import example.de_tai.R;

public class Phuoc_RecyclerViewThongSoThoiTietTheoNgayAdapter extends RecyclerView.Adapter<Phuoc_RecyclerViewThongSoThoiTietTheoNgayAdapter.RecyclerViewThongSoThoiTietTheoNgayViewHolder> {
    List<Phuoc_RecyclerViewThongSoThoiTietTheoNgay> recyclerViewThongSoThoiTietTheoNgayList;




    public Phuoc_RecyclerViewThongSoThoiTietTheoNgayAdapter(List<Phuoc_RecyclerViewThongSoThoiTietTheoNgay> recyclerViewThongSoThoiTietTheoNgayList) {
        this.recyclerViewThongSoThoiTietTheoNgayList = recyclerViewThongSoThoiTietTheoNgayList;
    }

    @NonNull
    @Override
    public RecyclerViewThongSoThoiTietTheoNgayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phuoc_hang_ngay_item_layout_lv,parent,false);
        return new RecyclerViewThongSoThoiTietTheoNgayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewThongSoThoiTietTheoNgayViewHolder holder, int position) {
        Phuoc_RecyclerViewThongSoThoiTietTheoNgay recyclerViewThongSoThoiTietTheoNgay = recyclerViewThongSoThoiTietTheoNgayList.get(position);

        holder.tvKhaNangCoMuaTheoNgay.setText(String.valueOf(recyclerViewThongSoThoiTietTheoNgay.getKhaNangCoMuaTheoNgay()));
      // Picasso.get().load(recyclerViewThongSoThoiTietTheoNgay.getNameWeather()).into(holder.imgIconThoiTietTheoNgay);
       holder.tvTenThoiTietTheoNgay.setText(recyclerViewThongSoThoiTietTheoNgay.getNameWeather());

        //holder.imgIconThoiTietTheoNgay.setImageResource(R.drawable.baseline_wind_power_24);
        holder.tvTocDoGioTrungBinhTheoNgay.setText(recyclerViewThongSoThoiTietTheoNgay.getTocDoGioTrungBinhTheoNgay());
        holder.imgIconHuongGioTheoNgay.setImageResource(recyclerViewThongSoThoiTietTheoNgay.getIconHuongGioTheoGio());
        holder.tvThu.setText(recyclerViewThongSoThoiTietTheoNgay.getThuTrongTuan());
    }

    @Override
    public int getItemCount() {
        return recyclerViewThongSoThoiTietTheoNgayList.size();
    }

    class RecyclerViewThongSoThoiTietTheoNgayViewHolder extends RecyclerView.ViewHolder {
        TextView tvKhaNangCoMuaTheoNgay;

        TextView tvTocDoGioTrungBinhTheoNgay;
        TextView tvTenThoiTietTheoNgay;
        ImageView imgIconHuongGioTheoNgay;
        TextView tvThu;

        public RecyclerViewThongSoThoiTietTheoNgayViewHolder(@NonNull View itemView) {
            super(itemView);

            tvKhaNangCoMuaTheoNgay = itemView.findViewById(R.id.tvKhaNangCoMuaTheoNgay);
            tvTenThoiTietTheoNgay = itemView.findViewById(R.id.tvTenThoiTietTheoNgay);
            tvTocDoGioTrungBinhTheoNgay = itemView.findViewById(R.id.tvTocDoGioTrungBinhTheoNgay);
            imgIconHuongGioTheoNgay = itemView.findViewById(R.id.imgIconHuongGioTheoNgay);
            tvThu = itemView.findViewById(R.id.tvThu);
        }
    }
}