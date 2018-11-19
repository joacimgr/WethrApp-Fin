package sunny.com.wethrapp.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        public ForecastHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.item_date);
            textViewCloudCoverage = itemView.findViewById(R.id.item_cloud_coverage);
            textViewTemperature = itemView.findViewById(R.id.item_temperature);
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
