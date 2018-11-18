package sunny.com.wethrapp.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sunny.com.wethrapp.R;
import sunny.com.wethrapp.model.DB.entity.TimeSeriesInstance;


public class ForecastAdaptor extends RecyclerView.Adapter<ForecastAdaptor.ForecastHolder> {
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

    @Override
    public void onBindViewHolder(@NonNull ForecastHolder holder, int position) {
        TimeSeriesInstance currentForecast = timeSeriesInstanceList.get(position);
        String date = String.valueOf(currentForecast.getTimeForValues());
        String[] dateSplit = date.split(" ");
        String[] timeSplit = dateSplit[3].split(":");
        StringBuffer sb = new StringBuffer();
        sb.append(dateSplit[0]);
        sb.append(" ");
        sb.append(dateSplit[2]);
        sb.append(" ");
        sb.append(dateSplit[1]);
        sb.append(" ");
        sb.append(timeSplit[0]);
        sb.append(":");
        sb.append(timeSplit[1]);

        holder.textViewDate.setText(sb.toString());
        holder.textViewTemperature.setText(String.valueOf(currentForecast.getTemperature()));
        holder.textViewCloudCoverage.setText(String.valueOf(currentForecast.getCloudCoverage()));
    }

    @Override
    public int getItemCount() {
        return timeSeriesInstanceList.size();
    }

    public void setTimeSeriesInstanceList(List<TimeSeriesInstance> timeSeriesInstanceList){
        this.timeSeriesInstanceList = timeSeriesInstanceList;
    }

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
}
