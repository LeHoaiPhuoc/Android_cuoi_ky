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

public class Nam_GD2nAdapter extends ArrayAdapter {

    Context context;
    int layoutItem;

    ArrayList<Nam_Weather_GD2_next> lsGD2_next = new ArrayList<>();



    public Nam_GD2nAdapter(@NonNull Context context, int resource, ArrayList<Nam_Weather_GD2_next> lsGD2_next) {
        super(context, resource,lsGD2_next);

        this.context =context;
        this.layoutItem=resource;
        this.lsGD2_next=lsGD2_next;
    }



    public View getView(int position,  View convertView,  ViewGroup parent) {

        Nam_Weather_GD2_next weather_gd2_next =lsGD2_next.get(position);

        if (convertView ==null){
            convertView = LayoutInflater.from(context).inflate(layoutItem, null);

        }
        TextView tvSunrise = (TextView) convertView.findViewById(R.id.tvSunrise);
        tvSunrise.setText(weather_gd2_next.getSunrise());

        TextView tvSunset = (TextView) convertView.findViewById(R.id.tvSunset);
        tvSunset.setText(weather_gd2_next.getSunset());


       /* TextView tvWinkph = (TextView) convertView.findViewById(R.id.tvWinkph);
        tvWinkph.setText(weather_gd2_next.getWind_kph());

        TextView tvWindir = (TextView) convertView.findViewById(R.id.tvWindir);
        tvWindir.setText(weather_gd2_next.getWin_dir());
*/

        return convertView;
    }



}
