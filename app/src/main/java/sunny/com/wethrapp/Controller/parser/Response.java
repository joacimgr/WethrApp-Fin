package sunny.com.wethrapp.Controller.parser;

import java.util.List;

/**
 * This class serves as holder for our response from gson.
 * Example requests:
 * https://maceo.sth.kth.se/api/category/pmp3g/version/2/geotype/point/lon/14.333/lat/60.383/
 * https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/14.333/lat/60.383/data.json
 */
public class Response {

    private String approvedTime;
    private String referenceTime;
    private Geometry geometry;
    private List<TimeSeries> timeSeries;

    public String getApprovedTime() {
        return approvedTime;
    }

    public void setApprovedTime(String approvedTime) {
        this.approvedTime = approvedTime;
    }

    public String getReferenceTime() {
        return referenceTime;
    }

    public void setReferenceTime(String referenceTime) {
        this.referenceTime = referenceTime;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public List<TimeSeries> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(List<TimeSeries> timeSeries) {
        this.timeSeries = timeSeries;
    }

    public static class Geometry {

        private String type;
        private List<List<Double>> coordinates;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<List<Double>> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<List<Double>> coordinates) {
            this.coordinates = coordinates;
        }
    }

    public static class TimeSeries {

        private String validTime;
        private List<Parameters> parameters;

        public String getValidTime() {
            return validTime;
        }

        public void setValidTime(String validTime) {
            this.validTime = validTime;
        }

        public List<Parameters> getParameters() {
            return parameters;
        }

        public void setParameters(List<Parameters> parameters) {
            this.parameters = parameters;
        }

        public static class Parameters {

            private String name;
            private String levelType;
            private int level;
            private String unit;
            private List<Double> values;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLevelType() {
                return levelType;
            }

            public void setLevelType(String levelType) {
                this.levelType = levelType;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public List<Double> getValues() {
                return values;
            }

            public void setValues(List<Double> values) {
                this.values = values;
            }
        }
    }
}
