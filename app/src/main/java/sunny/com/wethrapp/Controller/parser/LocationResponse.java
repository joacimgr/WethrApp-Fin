package sunny.com.wethrapp.Controller.parser;


/**
 * This class holds gson response when gathering information from
 * http://smhi.se/wpt-a/backend_solr/autocomplete/search
 */
public class LocationResponse {

    private int geonameid;
    private String place;
    private double lon;
    private double lat;
    private String municipality;

    public int getGeonameid() {
        return geonameid;
    }

    public void setGeonameid(int geonameid) {
        this.geonameid = geonameid;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }
}

