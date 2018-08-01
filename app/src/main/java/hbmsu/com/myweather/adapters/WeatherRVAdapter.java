package hbmsu.com.myweather.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.johnhiott.darkskyandroidlib.models.DataPoint;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hbmsu.com.myweather.R;
import hbmsu.com.myweather.Utils;
import hbmsu.com.myweather.ViewClick;
import hbmsu.com.myweather.custom.MyTextView;
import hbmsu.com.myweather.model.DataItem;

public class WeatherRVAdapter extends RecyclerView.Adapter<WeatherRVAdapter.ViewHolder> {
    Context context;
    List<DataPoint> data;
    ViewClick viewClick;

    public WeatherRVAdapter(Context context, ViewClick viewClick) {
        this.context = context;
        this.viewClick = viewClick;
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public WeatherRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.daily_weather_row, parent, false);
        return new WeatherRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherRVAdapter.ViewHolder holder, int position) {
        DataPoint item = getItem(position);
        if (item != null) {
            holder.tvDate.setText(Utils.formatMilliToDate(item.getTime()));
            holder.tvMaxDegree.setText(String.format(context.getString(R.string.degree),
                    (int) item.getTemperatureMax(), Utils.getDegreeUnit()));
            holder.tvMinDegree.setText(String.format(context.getString(R.string.degree),
                    (int) item.getTemperatureMin(), Utils.getDegreeUnit()));
            holder.tvSummary.setText(item.getSummary());
            try {
                holder.tvHumidity.setText(String.valueOf((int) (Double.parseDouble(item.getHumidity()) * 100)) + "%");
            } catch (Exception e) {
                e.printStackTrace();
                holder.tvHumidity.setText(item.getHumidity() + "%");

            }
            holder.tvVisibility.setText(Utils.getVisibility(item.getVisibility()));
//            holder.tvVisibility.setText( item.getVisibility());
            holder.itemView.setTag(new Object[]{holder.cardView, position});
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object[] objects = (Object[]) v.getTag();
                    viewClick.OnClick((Integer) objects[1], (CardView) objects[0]);
                }
            });

        }
    }

    private DataPoint getItem(int position) {
        if (data != null && data.size() > 0) {
            return data.get(position);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addItems(List<DataPoint> daily) {
        this.data.clear();
        this.data = daily;

        notifyItemRangeInserted(0, getItemCount());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.tvDate)
        MyTextView tvDate;
        @BindView(R.id.tvDegree)
        MyTextView tvDegree;
        @BindView(R.id.tvSummary)
        MyTextView tvSummary;
        @BindView(R.id.tvMaxDegree)
        MyTextView tvMaxDegree;
        @BindView(R.id.tvMinDegree)
        MyTextView tvMinDegree;
        @BindView(R.id.tvVisibility)
        MyTextView tvVisibility;
        @BindView(R.id.tvHumidity)
        MyTextView tvHumidity;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
