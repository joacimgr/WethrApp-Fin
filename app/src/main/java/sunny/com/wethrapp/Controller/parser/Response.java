package sunny.com.wethrapp.Controller.parser;

import java.util.List;

public class Response {

    private String approvedTime;
    private String referenceTime;
    private GeometryBean geometry;
    private List<TimeSeriesBean> timeSeries;

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

    public GeometryBean getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryBean geometry) {
        this.geometry = geometry;
    }

    public List<TimeSeriesBean> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(List<TimeSeriesBean> timeSeries) {
        this.timeSeries = timeSeries;
    }

    public static class GeometryBean {

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

    public static class TimeSeriesBean {

        private String validTime;
        private List<ParametersBean> parameters;

        public String getValidTime() {
            return validTime;
        }

        public void setValidTime(String validTime) {
            this.validTime = validTime;
        }

        public List<ParametersBean> getParameters() {
            return parameters;
        }

        public void setParameters(List<ParametersBean> parameters) {
            this.parameters = parameters;
        }

        public static class ParametersBean {

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
