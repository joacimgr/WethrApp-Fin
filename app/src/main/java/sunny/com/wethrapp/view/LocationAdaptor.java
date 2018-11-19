package sunny.com.wethrapp.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sunny.com.wethrapp.R;
import sunny.com.wethrapp.model.DB.entity.Location;

public class LocationAdaptor extends RecyclerView.Adapter<LocationAdaptor.LocationHolder> {

    class LocationHolder extends RecyclerView.ViewHolder{
        private TextView textPlace;
        private TextView textMunicipality;

        public LocationHolder(@NonNull View itemView) {
            super(itemView);
            textPlace = itemView.findViewById(R.id.item_place);
            textMunicipality = itemView.findViewById(R.id.item_text_municipality);
        }
    }

    private static final String TAG = "LogAppTest";
    private List<Location> locationList;

    public LocationAdaptor(List<Location> locationList) {
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public LocationAdaptor.LocationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.location_place_view, viewGroup, false);
        return new LocationHolder(itemView);
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
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }
}
