package sunny.com.wethrapp.view;

import android.graphics.drawable.Icon;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.sql.Date;
import java.util.List;

import sunny.com.wethrapp.R;
import sunny.com.wethrapp.model.DB.entity.TimeSeriesInstance;

import static sunny.com.wethrapp.model.DB.entity.Converters.dateToStringPresentable;


/**
 * This class is a RecyclerView Adaptor for our ShowForecastListActivity, inflating the view
 * with items from current list of timeseriesInstanceList.
 */
public class ForecastAdaptor extends RecyclerView.Adapter<ForecastAdaptor.ForecastHolder> {

    /**
     * This class holds current items view and maps the object to the relevant
     * XML.
     */
    class ForecastHolder extends RecyclerView.ViewHolder{
        private TextView textViewDate;
        private TextView textViewCloudCoverage;
        private TextView textViewTemperature;
        private ImageView cloudIconView;

        public ForecastHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.item_date);
            textViewCloudCoverage = itemView.findViewById(R.id.item_cloud_coverage);
            textViewTemperature = itemView.findViewById(R.id.item_temperature);
            cloudIconView = itemView.findViewById(R.id.item_view_icon);
        }
    }

    private static final String TAG = "LogAppTest";
    private List<TimeSeriesInstance> timeSeriesInstanceList;

    public ForecastAdaptor(List<TimeSeriesInstance> timeSeriesInstanceList) {
        this.timeSeriesInstanceList = timeSeriesInstanceList;
    }

    @NonNull
    @Override
    public ForecastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new ForecastHolder(itemView);
    }

    /**
     * This method sets the items with information gathered from the current
     * timeseriesList.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ForecastHolder holder, int position) {
        TimeSeriesInstance currentForecast = timeSeriesInstanceList.get(position);
        Date timeDate = new Date(currentForecast.getTimeForValues());
        String present = dateToStringPresentable(timeDate);
        holder.textViewDate.setText(present);
        holder.textViewTemperature.setText(String.valueOf(currentForecast.getTemperature()));
        holder.textViewCloudCoverage.setText(String.valueOf(currentForecast.getCloudCoverage()));
        String hoursString = present.split("  ")[1].split(":")[0];
        hoursString = hoursString.substring(1,3);
        Integer hoursInteger = Integer.parseInt(hoursString);
        int cc = (int)currentForecast.getCloudCoverage();
        Log.d(TAG, "time1: " + hoursString + " integer: " + hoursInteger.toString());
        Log.d(TAG, "cc: " + cc );
        if(hoursInteger > 7 || hoursInteger < 20){
            switch (cc){
                case 1: holder.cloudIconView.setImageResource( R.mipmap.d1); break;
                case 2: holder.cloudIconView.setImageResource( R.mipmap.d2); break;
                case 3: holder.cloudIconView.setImageResource( R.mipmap.d3); break;
                case 4: holder.cloudIconView.setImageResource( R.mipmap.d4); break;
                case 5: holder.cloudIconView.setImageResource( R.mipmap.d5); break;
                case 6: holder.cloudIconView.setImageResource( R.mipmap.d6); break;
                case 7: holder.cloudIconView.setImageResource( R.mipmap.d7); break;
                case 8: holder.cloudIconView.setImageResource( R.mipmap.d8); break;
                case 9: holder.cloudIconView.setImageResource( R.mipmap.d9); break;
                case 10: holder.cloudIconView.setImageResource( R.mipmap.d10); break;
                case 11: holder.cloudIconView.setImageResource( R.mipmap.d11); break;
                case 12: holder.cloudIconView.setImageResource( R.mipmap.d12); break;
                case 13: holder.cloudIconView.setImageResource( R.mipmap.d13); break;
                case 14: holder.cloudIconView.setImageResource( R.mipmap.d14); break;
                case 15: holder.cloudIconView.setImageResource( R.mipmap.d15); break;
                case 16: holder.cloudIconView.setImageResource( R.mipmap.d16); break;
                case 17: holder.cloudIconView.setImageResource( R.mipmap.d17); break;
                case 18: holder.cloudIconView.setImageResource( R.mipmap.d18); break;
                case 19: holder.cloudIconView.setImageResource( R.mipmap.d19); break;
                case 20: holder.cloudIconView.setImageResource( R.mipmap.d20); break;
                case 21: holder.cloudIconView.setImageResource( R.mipmap.d21); break;
                case 22: holder.cloudIconView.setImageResource( R.mipmap.d22); break;
                case 23: holder.cloudIconView.setImageResource( R.mipmap.d23); break;
                case 24: holder.cloudIconView.setImageResource( R.mipmap.d24); break;
                case 25: holder.cloudIconView.setImageResource( R.mipmap.d25); break;
                case 26: holder.cloudIconView.setImageResource( R.mipmap.d26); break;
                case 27: holder.cloudIconView.setImageResource( R.mipmap.d27); break;
            }
        } else {
            switch (hoursInteger){
                case 1: holder.cloudIconView.setImageResource( R.mipmap.n1); break;
                case 2: holder.cloudIconView.setImageResource( R.mipmap.n2); break;
                case 3: holder.cloudIconView.setImageResource( R.mipmap.n3); break;
                case 4: holder.cloudIconView.setImageResource( R.mipmap.n4); break;
                case 5: holder.cloudIconView.setImageResource( R.mipmap.n5); break;
                case 6: holder.cloudIconView.setImageResource( R.mipmap.n6); break;
                case 7: holder.cloudIconView.setImageResource( R.mipmap.n7); break;
                case 8: holder.cloudIconView.setImageResource( R.mipmap.n8); break;
                case 9: holder.cloudIconView.setImageResource( R.mipmap.n9); break;
                case 10: holder.cloudIconView.setImageResource( R.mipmap.n10); break;
                case 11: holder.cloudIconView.setImageResource( R.mipmap.n11); break;
                case 12: holder.cloudIconView.setImageResource( R.mipmap.n12); break;
                case 13: holder.cloudIconView.setImageResource( R.mipmap.n13); break;
                case 14: holder.cloudIconView.setImageResource( R.mipmap.n14); break;
                case 15: holder.cloudIconView.setImageResource( R.mipmap.n15); break;
                case 16: holder.cloudIconView.setImageResource( R.mipmap.n16); break;
                case 17: holder.cloudIconView.setImageResource( R.mipmap.n17); break;
                case 18: holder.cloudIconView.setImageResource( R.mipmap.n18); break;
                case 19: holder.cloudIconView.setImageResource( R.mipmap.n19); break;
                case 20: holder.cloudIconView.setImageResource( R.mipmap.n20); break;
                case 21: holder.cloudIconView.setImageResource( R.mipmap.n21); break;
                case 22: holder.cloudIconView.setImageResource( R.mipmap.n22); break;
                case 23: holder.cloudIconView.setImageResource( R.mipmap.n23); break;
                case 24: holder.cloudIconView.setImageResource( R.mipmap.n24); break;
                case 25: holder.cloudIconView.setImageResource( R.mipmap.n25); break;
                case 26: holder.cloudIconView.setImageResource( R.mipmap.n26); break;
                case 27: holder.cloudIconView.setImageResource( R.mipmap.n27); break;
            }
        }

    }

    /**
     * This method returns the size of current timeseriesList.
     * @return
     */
    @Override
    public int getItemCount() {
        return timeSeriesInstanceList.size();
    }

    public void setTimeSeriesInstanceList(List<TimeSeriesInstance> timeSeriesInstanceList){
        this.timeSeriesInstanceList = timeSeriesInstanceList;
    }


}
