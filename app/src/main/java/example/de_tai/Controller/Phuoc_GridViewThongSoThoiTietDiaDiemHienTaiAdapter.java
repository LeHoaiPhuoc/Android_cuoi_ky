package example.de_tai.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import example.de_tai.Model.Phuoc_GridViewThongSoThoiTietDiaDiemHienTai;
import example.de_tai.R;

public class Phuoc_GridViewThongSoThoiTietDiaDiemHienTaiAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Phuoc_GridViewThongSoThoiTietDiaDiemHienTai> gridViewThongSoThoiTietDiaDiemHienTaiList;

    public Phuoc_GridViewThongSoThoiTietDiaDiemHienTaiAdapter(Context context, int layout, List<Phuoc_GridViewThongSoThoiTietDiaDiemHienTai> gridViewThongSoThoiTietDiaDiemHienTaiList) {
        this.context = context;
        this.layout = layout;
        this.gridViewThongSoThoiTietDiaDiemHienTaiList = gridViewThongSoThoiTietDiaDiemHienTaiList;
    }

    @Override
    public int getCount() {
        return gridViewThongSoThoiTietDiaDiemHienTaiList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);

        // Ánh xạ
        ImageView imgIcon = convertView.findViewById(R.id.imgIcon);
        TextView tvMoTaIcon = convertView.findViewById(R.id.tvMoTaIcon);
        TextView tvthongSoThoiTietDiaDiemHienTai = convertView.findViewById(R.id.tvthongSoThoiTietDiaDiemHienTai);

        // Gán giá trị
        imgIcon.setImageResource(gridViewThongSoThoiTietDiaDiemHienTaiList.get(position).icon);
        tvMoTaIcon.setText(gridViewThongSoThoiTietDiaDiemHienTaiList.get(position).moTaIcon);
        tvthongSoThoiTietDiaDiemHienTai.setText(gridViewThongSoThoiTietDiaDiemHienTaiList.get(position).thongSoThoiTietDiaDiemHienTai);
        return convertView;
    }
}
