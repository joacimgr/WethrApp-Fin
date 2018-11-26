package sunny.com.wethrapp.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

import sunny.com.wethrapp.R;
import sunny.com.wethrapp.model.DB.entity.Location;

/**
 * This class is a RecyclerView Adaptor for our LocationListActivity, inflating the view
 * with items from current list of LocationList.
 */
public class LocationAdaptor extends RecyclerView.Adapter<LocationAdaptor.LocationHolder> {

    class LocationHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textPlace;
        private TextView textMunicipality;
        private OnItemClickLocationListener listener;

        public LocationHolder(@NonNull View itemView, OnItemClickLocationListener listener) {
            super(itemView);
            textPlace = itemView.findViewById(R.id.item_place);
            textMunicipality = itemView.findViewById(R.id.item_text_municipality);
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAdapterPosition());
        }
    }

    private static final String TAG = "LogAppTest";
    private List<Location> locationList;
    private OnItemClickLocationListener listener;

    public LocationAdaptor(List<Location> locationList, OnItemClickLocationListener listener) {
        this.locationList = locationList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LocationAdaptor.LocationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.location_place_view, viewGroup, false);
        return new LocationHolder(itemView, listener);
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }



    @Override
    public void onBindViewHolder(@NonNull LocationAdaptor.LocationHolder locationHolder, int position) {
        Log.d(TAG, "locationList.get(0).getPlace(): " + locationList.get(0).getPlace());
        Location currentLocation = locationList.get(position);
        locationHolder.textMunicipality.setText(currentLocation.getMunicipality());
        locationHolder.textPlace.setText(currentLocation.getPlace());
        locationHolder.itemView.setOnClickListener(view -> listener.onItemClick(view, position));
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }
}
