package example.de_tai.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import example.de_tai.Model.HangGioClass;
import example.de_tai.R;

public class Tuong_HangGioAdapter extends ArrayAdapter {

    Context context;
    int layoutItem;
    ArrayList<HangGioClass> lsHangGio = new ArrayList<>();

    public Tuong_HangGioAdapter(Context context, int resource, ArrayList<HangGioClass> lsHangGio) {
        super(context, resource, lsHangGio);
        this.context = context;
        this.layoutItem = resource;
        this.lsHangGio = lsHangGio;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        HangGioClass hangGioClass = lsHangGio.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);
        }

        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        tvTime.setText(hangGioClass.getTime());

        TextView tvUV = (TextView) convertView.findViewById(R.id.tvUV);
        tvUV.setText(String.valueOf(hangGioClass.getUv()));

        TextView tvTemp = (TextView) convertView.findViewById(R.id.tvTemp);
        tvTemp.setText(hangGioClass.getTemp_c());

        TextView tvHumid = (TextView) convertView.findViewById(R.id.tvHumid);
        tvHumid.setText(hangGioClass.getHumidity());

        TextView tvDewpoint = (TextView) convertView.findViewById(R.id.tvDewpoint);
        tvDewpoint.setText(hangGioClass.getDewpoint_c());

        TextView tvWind = (TextView) convertView.findViewById(R.id.tvWind);
        tvWind.setText(hangGioClass.getWind_kph());

        TextView tvText = (TextView) convertView.findViewById(R.id.tvText);
        tvText.setText(hangGioClass.getText());

        TextView tvRain = (TextView) convertView.findViewById(R.id.tvRain);
        tvRain.setText(hangGioClass.getChance_of_rain());

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
        Picasso.get().load(hangGioClass.getIcon()).resize(100, 100).into(imgIcon);

        return convertView;

    }
}
