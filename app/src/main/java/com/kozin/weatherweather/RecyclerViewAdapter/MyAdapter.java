package com.kozin.weatherweather.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kozin.weatherweather.ModelFuture.WeatherForecastResult;
import com.kozin.weatherweather.R;
import com.squareup.picasso.Picasso;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private WeatherForecastResult weatherForecastResult;
    private Context context;

    public MyAdapter(Context ct, WeatherForecastResult articles){
        this.context = ct;
        this.weatherForecastResult = articles;
    }

    /**
     * holder for views
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);

        return new MyViewHolder(view);
    }

    /**
     * placing data into params and displaying them
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder viewHolder, final int position) {
        String dateTime = weatherForecastResult.getList().get(position).getDt_txt() + "";
        String temper = weatherForecastResult.getList().get(position).getMain().getTemp() + "°";
        String description = weatherForecastResult.getList().get(position).getWeather().get(0).getDescription() + "";

        Picasso.get().load(new StringBuilder("http://openweathermap.org/img/wn/")
                .append(weatherForecastResult.list.get(position).weather.get(0).getIcon())
                .append(".png").toString()).into(viewHolder.imgForecastIcon);

        viewHolder.tvDateTime.setText(dateTime);
        viewHolder.tvTemper.setText(temper);
        viewHolder.tvDescription.setText(description);

    }


    @Override
    public int getItemCount() {
        return weatherForecastResult.getList().size();
    }

    /**
     * initialization of data keepers(params)
     */
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvDateTime, tvTemper, tvDescription;
        ImageView imgForecastIcon;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDateTime = (TextView) itemView.findViewById(R.id.tvDateTime);
            tvTemper = (TextView) itemView.findViewById(R.id.tvTemper);
            imgForecastIcon = (ImageView) itemView.findViewById(R.id.imgForecastIcon);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
        }
    }
}

