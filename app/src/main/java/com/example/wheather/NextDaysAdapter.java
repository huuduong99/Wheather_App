package com.example.wheather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NextDaysAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Weather> weatherList;

    public NextDaysAdapter(Context context, int layout, List<Weather> weatherList) {
        this.context = context;
        this.layout = layout;
        this.weatherList = weatherList;
    }


    @Override
    public int getCount() {
        return weatherList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtNgayThang,txtTrangThai,txtMaxTemp,txtMinTemp;
        ImageView imgTrangThai;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if(view == null){
            holder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(layout,null);
            holder.txtNgayThang=view.findViewById(R.id.textViewNgayThang);
            holder.txtTrangThai=view.findViewById(R.id.textViewTrangThai);
            holder.txtMaxTemp=view.findViewById(R.id.textViewMaxTemp);
            holder.txtMinTemp=view.findViewById(R.id.textViewMinTemp);
            holder.imgTrangThai=view.findViewById(R.id.imageViewTrangThai);

            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }

        Weather weather=weatherList.get(i);
        holder.txtNgayThang.setText(weather.getDay()+"h");
        holder.txtTrangThai.setText(weather.getState());
        holder.txtMaxTemp.setText(weather.getMaxTemp()+"℃");
        holder.txtMinTemp.setText(weather.getMinTemp()+"℃");
        Picasso.with(context).load("http://openweathermap.org/img/wn/"+weather.getImage()+".png").into(holder.imgTrangThai);
        return view;
    }
}
