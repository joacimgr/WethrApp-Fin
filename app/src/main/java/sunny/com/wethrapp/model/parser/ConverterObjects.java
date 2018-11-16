/*
package sunny.com.wethrapp.model.parser;

public class ConverterObjects {

    public class Response {

        private int responseId;


        private String approvedTime;
        private String referenceTime;
        private Geometry geometry;
        private TimeSeries[] timeSeries;

        public Response(int responseId, String approvedTime, String referenceTime, Geometry geometry, TimeSeries[] timeSeries) {
            this.responseId = responseId;
            this.approvedTime = approvedTime;
            this.referenceTime = referenceTime;
            this.geometry = geometry;
            this.timeSeries = timeSeries;
        }

        public int getResponseId() {return responseId;}

        public void setResponseId(int responseId) {this.responseId = responseId;}

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

        public TimeSeries[] getTimeSeries() {
            return timeSeries;
        }

        public void setTimeSeries(TimeSeries[] timeSeries) {
            this.timeSeries = timeSeries;
        }
    }

    public class Geometry {
        private String type;
        private Coordinates[] coordinates;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Coordinates[] getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(Coordinates[] coordinates) {
            this.coordinates = coordinates;
        }

        public Geometry(int geoId, String type, Coordinates[] coordinates) {
            this.type = type;
            this.coordinates = coordinates;
        }
    }

    public class TimeSeries {
        private String validTime;
        private Parameter[] parameter;

        public TimeSeries(String validTime, Parameter[] parameter) {

            this.validTime = validTime;
            this.parameter = parameter;
        }
        public String getValidTime() {
            return validTime;
        }
        public void setValidTime(String validTime) {
            this.validTime = validTime;
        }
        public Parameter[] getParameter() {
            return parameter;
        }
        public void setParameter(Parameter[] parameter) {
            this.parameter = parameter;
        }



    }

    public class Coordinates {
        private double[] coorinates;

        public double[] getCoorinates() {
            return coorinates;
        }

        public void setCoorinates(double[] coorinates) {
            this.coorinates = coorinates;
        }

        public Coordinates(double[] coorinates) {
            this.coorinates = coorinates;
        }
    }

    public class Parameter {
        private String name;
        private String levelType;
        private int level;
        private String unit;

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

        public int[] getValues() {
            return values;
        }

        public void setValues(int[] values) {
            this.values = values;
        }

        public Parameter(String name, String levelType, int level, String unit, int[] values) {

            this.name = name;
            this.levelType = levelType;
            this.level = level;
            this.unit = unit;
            this.values = values;
        }

        private int[] values;
    }
}
*/
