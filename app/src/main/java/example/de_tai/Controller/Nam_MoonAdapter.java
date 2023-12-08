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

public class Nam_MoonAdapter extends ArrayAdapter {
    Context context;
    int layoutItem;

    ArrayList<Nam_Weather_GD2_next> lsMoon = new ArrayList<>();



    public Nam_MoonAdapter(@NonNull Context context, int resource, ArrayList<Nam_Weather_GD2_next> lsMoon) {
        super(context, resource,lsMoon);

        this.context =context;
        this.layoutItem=resource;
        this.lsMoon=lsMoon;
    }



    public View getView(int position, View convertView, ViewGroup parent) {

        Nam_Weather_GD2_next weather_gd2_next =lsMoon.get(position);

        if (convertView ==null){
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);

        }

        TextView tvMoonrise = (TextView) convertView.findViewById(R.id.tvMoonrise);
        tvMoonrise.setText(weather_gd2_next.getMoonrise());

        TextView tvMoonset = (TextView) convertView.findViewById(R.id.tvMoonset);
        tvMoonset.setText(weather_gd2_next.getMoonset());

        TextView tvMoonphase = (TextView) convertView.findViewById(R.id.tvMoonphase);
        tvMoonphase.setText(weather_gd2_next.getMoonphase());


        return convertView;
    }



}
