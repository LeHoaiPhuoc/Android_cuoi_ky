package example.de_tai.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import example.de_tai.Model.Nam_Weather_GD2_next;
import example.de_tai.R;

public class Nam_WindAdapter extends ArrayAdapter {
    Context context;
    int layoutItem;

    ArrayList<Nam_Weather_GD2_next> lsWind = new ArrayList<>();



    public Nam_WindAdapter(@NonNull Context context, int resource, ArrayList<Nam_Weather_GD2_next> lsWind) {
        super(context, resource,lsWind);

        this.context =context;
        this.layoutItem=resource;
        this.lsWind=lsWind;
    }



    public View getView(int position, View convertView, ViewGroup parent) {

        Nam_Weather_GD2_next weather_gd2_next =lsWind.get(position);

        if (convertView ==null){
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);

        }

        TextView tvWinkph = (TextView) convertView.findViewById(R.id.tvWinkph);
        tvWinkph.setText(weather_gd2_next.getWind_kph());

        TextView tvWindir = (TextView) convertView.findViewById(R.id.tvWindir);
        tvWindir.setText(weather_gd2_next.getWin_dir());


        return convertView;
    }



}

