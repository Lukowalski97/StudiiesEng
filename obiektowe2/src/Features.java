import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.io.FileWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

/**
 * Features class is responsible for whole program logic, it calculates data, draws charts, parse JSON from urls/file, save JSON
 * to files. It has 3 inner classes- each is structure to contain more than one class parsed from JSON in pair or in set.
 */
public class Features {
    /**
     * This HashMap contains every station saved in findAll.json file, it represent every measure station.
     * Key for this Map is station's name- it makes searching for station O(c) .
     */
    private HashMap<String, MeasureStation> stations;

    /**
     * This public constructor creates new HashMap to store stations and fill this map with
     * other function- called  {@link #prepareStations()} .
     * @throws IOException when findAll.json cannot be found in local files.
     */
    public Features() throws IOException {
        stations = new HashMap<>();
        prepareStations();
    }

    /**
     * This void method fills HashMap of stations, using GSON API to parse file into set of {@link MeasureStation} objects
     * @throws IOException when findAll.json cannot be found in local files.
     */
    private void prepareStations() throws IOException {
        Gson gson = new Gson();

        MeasureStation[] list;
        jsonPathStrategy strategy = new jsonPathAll();
        BufferedReader reader = new BufferedReader(new FileReader(new File((strategy.getPath(0)))));
        list = gson.fromJson(reader, MeasureStation[].class);
        reader.close();
        for (MeasureStation tmp : list) {
            stations.put(tmp.getStationName(), tmp);
        }

    }

    /**
     * Method just search for station in stations HashMap set
     * @param name name of the station we are looking for
     * @return {@link MeasureStation} object representing one measure station
     */
    public MeasureStation findStationByName(String name) {
        return stations.get(name);
    }

    /**
     * Method get Air Index of station - it may be Very good, good etc.
     * It use GSON API to parse {@link AirIndex} object from [id]ind.JSON file or from REST API
     * @param station {@link MeasureStation} object representing station for which we want to get index
     * @return {@link AirIndex } object represeting air index measured and calculated for station from parameter
     * @throws Exception when there is no file representing index and REST API doesn't give us JSON representation
     * baased on stationID
     */
    public AirIndex getAirIndex(MeasureStation station) throws Exception {
        AirIndex ind;
        Gson gson = new Gson();
        jsonPathStrategy strategy = new jsonPathInd();
        String path = strategy.getPath(station.getId());


        String url = "http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/" + station.getId();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            ind = gson.fromJson(reader, AirIndex.class);

            if (ind.isNull()) {
                InputStream is = new URL(url).openStream();
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                ind = gson.fromJson(isr, AirIndex.class);
                String outp = gson.toJson(ind);
                FileWriter fileWriter = new FileWriter("files\\" + station.getId() + "ind.json");
                fileWriter.write(outp);
                fileWriter.close();

            }
            return ind;
        } catch (FileNotFoundException ex) {
            try {
                InputStream is = new URL(url).openStream();
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                ind = gson.fromJson(isr, AirIndex.class);
                String outp = gson.toJson(ind);
                FileWriter fileWriter = new FileWriter("files\\" + station.getId() + "ind.json");
                fileWriter.write(outp);
                fileWriter.close();
                return ind;
            } catch (IOException ex2) {
                throw new Exception("Air index of" + station.getId() + " " + station.getStationName() + " cannot be found!");
            }
        }

    }

    /**
     * This method creates {@link MeasureData} object representing recent measures made by station's sensor we want.
     * @param sensorId - ID of sensor whose measures are represented by {@link MeasureData} object.
     * @return {@link MeasureData} object with list of measures, their values made every hour sorted by date.
     * @throws Exception  when there is no file representing measures  and REST API doesn't give us JSON representation
     * baased on sensor's ID
     */
    public MeasureData getMeasureData(long sensorId) throws Exception {
        MeasureData data = null;
        Gson gson = new Gson();

        jsonPathStrategy strategy = new jsonPathDt();
        String path = strategy.getPath(sensorId);

        String url = "http://api.gios.gov.pl/pjp-api/rest/data/getData/" + sensorId;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            try {
                data = gson.fromJson(reader, MeasureData.class);
            } catch (JsonSyntaxException ex) {

            }

           /* if (data.isNull()) {
                InputStream is = new URL(url).openStream();
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                data = gson.fromJson(isr, MeasureData.class);
                String outp = gson.toJson(data);
                FileWriter fileWriter = new FileWriter("D:\\projekty\\obiektowe2\\files\\" + sensorId + "dt.json");
                fileWriter.write(outp);
                fileWriter.close();

            }*/

            return data;

        } catch (FileNotFoundException ex) {

            try {
                InputStream is = new URL(url).openStream();
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                data = gson.fromJson(isr, MeasureData.class);
                String outp = gson.toJson(data);
                FileWriter fileWriter = new FileWriter("files\\" + sensorId + "dt.json");
                fileWriter.write(outp);
                fileWriter.close();
                return data;
            } catch (IOException e) {
                return null;

            }



        }

    }

    /**
     * This method returns single parameter's measure value based on date and station's ID
     * @param param {@link Param} object representing measured param, for instance CO- Carbon monoxide
     * @param date String representing date (yyyy-MM-dd HH:MM:SS) when measure took place
     * @param stationId ID of station from which we want measure
     * @return {@link MeasureValues} representing single value and date when it was measured of one single parameter
     * @throws Exception when files cannot be found or REST API cannot give us proper JSON representation. Exception
     * also may be thrown when date is not listed in JSON file representing measures or when paramter is not measured
     * on checked station.
     */
    public MeasureValues getValuebyDateAndStation(String param, String date, long stationId) throws Exception {

        Sensor sensor = getSensor(stationId, param);
        MeasureData data = getMeasureData(sensor.getId());
        Date dateChecked = DateHandler.toDate(date);
        if (data == null)
            throw new Exception("Nie znaleziono na stacji " + stationId + "pomiarow parametru " + param + " mierzonego : " + date);
        for (MeasureValues tmp : data.getValues()) {
            Date dateTmp= DateHandler.toDate(tmp.getDate());
            if (dateChecked.equals(dateTmp))
                return tmp;
        }
        throw new Exception("Nie znaleziono na stacji " + stationId + " parametru " + param + " mierzonego : " + date + "\n" +
                "Id stanowiska pomiarowego: " + sensor.getId());
    }

    /**
     * This method returns array of every sensor connected with station
     * @param stationId ID of station from which we want get sensors
     * @return array of {@link Sensor} representing list of station's sensors, single sensor measures only one parameter.
     * @throws Exception when file cannot be found, REST API cannot give us JSON file.
     */
    public Sensor[] getSensors(long stationId) throws Exception {
        jsonPathStrategy strategy = new jsonPathSp();
        String path = strategy.getPath(stationId);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            Gson gson = new Gson();
            Sensor[] sensors = gson.fromJson(reader, Sensor[].class);
            if (sensors.length == 0) {
                String url = "http://api.gios.gov.pl/pjp-api/rest/station/sensors/" + stationId;
                InputStream is = new URL(url).openStream();
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                sensors = gson.fromJson(isr, Sensor[].class);
                String outp = gson.toJson(sensors);
                FileWriter fileWriter = new FileWriter("files\\" + stationId + "sp.json");
                fileWriter.write(outp);
                fileWriter.close();
            }
            return sensors;
        } catch (FileNotFoundException ex1) {
            String url = "http://api.gios.gov.pl/pjp-api/rest/station/sensors/" + stationId;
            try {
                InputStream is = new URL(url).openStream();
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                Gson gson = new Gson();
                Sensor[] sensors = gson.fromJson(isr, Sensor[].class);
                String outp = gson.toJson(sensors);
                FileWriter fileWriter = new FileWriter("files\\" + stationId + "sp.json");
                fileWriter.write(outp);
                fileWriter.close();
                return sensors;
            } catch (IOException ex2) {
                throw new Exception("Nie mozna znaleźć stanowisk pomiarowych dla stacji o nrze " + stationId);
            }
        }
    }

    /**
     * This method returns single sensor of specified parameter and station
     * @param stationId ID of station from which we want to get sensor's information
     * @param param {@link Param} object representing parameter which we want to check
     * @return {@link Sensor} object representing single sensor
     * @throws Exception when file cannot be found, REST API cannot give us JSON file or
     * when station we wanted to check lacks in sensors measuring given parameter.
     */
    public Sensor getSensor(long stationId, String param) throws Exception {
        Sensor[] sensors = getSensors(stationId);
        for (Sensor tmp : sensors) {
            if (tmp.getParam().getParamCode().equals(param))
                return tmp;
        }
        throw new Exception("Parametr "+param + "nie znaleziony na stacji " + stationId);
    }

    /**
     * This method returns average amount of measured parameter in given time period.
     * @param param {@link Param} object represting checked parameter
     * @param stationID ID of station we want to check average value
     * @param dateFrom Date object representing date from which we want to count average value
     * @param dateTo Date object representing date upto which we want to count average value
     * @return average value of measured parameter
     * @throws Exception when file cannot be found REST API doesn't give proper JSON file or
     * when there were no measures in given period of time, or when this station doesn't have sensor
     * for given parameter.
     */
    public Double getAverage(String param, long stationID, Date dateFrom, Date dateTo) throws Exception {
        double outp = 0;
        Sensor sensor = getSensor(stationID, param);
        MeasureData data = getMeasureData(sensor.getId());
        if(data==null ){
            throw new Exception("Nie można znaleźć danych pomiarowych dla stanowiska "+ sensor.getId() + " \n" +
                    "ze stacji " + stationID);
        }
        int n = 0;
        for (MeasureValues tmp : data.getValues()) {
            Date tmpDate = DateHandler.toDate(tmp.getDate());
            if (tmpDate.before(dateTo) && tmpDate.after(dateFrom)) {
                outp += tmp.getValue();
                n++;
            }
        }
        if(n==0){
            throw new Exception("Nie znaleziono na  stacji "+ stationID+" żadnego pomiaru w tym zakresie czasu.");
        }

        return outp / (double) n;
    }

    /**
     * returns parameter which fluctuated most from given date until now
     * @param stations list of stations we want to check for fluctuations
     * @param date date after which fluctuations will be calculated
     * @return {@link Param} object representing parameter we were looking for
     * @throws Exception when there is no proper file, REST API doesn't provide JSON file or when
     * there were no measures on some stations/sensors.
     */
    public Param greatestDeltas(ArrayList<MeasureStation> stations, String date) throws Exception {
        Param topParam = null;
        double topDelta = 0;
        for (MeasureStation tmp : stations) {
            Sensor[] sensors = getSensors(tmp.getId());
            for (Sensor sensor : sensors) {

                MeasureData data = getMeasureData(sensor.getId());
                if(data== null){
                    throw new Exception("Pomiary ze stanowiska o ID " + sensor.getId() + " nie moga zostac odnalezione");
                }
                double delta = sumDeltas(data, date);
                if (delta > topDelta) {
                    topDelta = delta;
                    topParam = sensor.getParam();
                }
            }
        }if(topParam!=null){
            return topParam;
        }
        throw new Exception("nie znaleziono zadnego parametru na podanych stacjach");
    }

    public double sumDeltas(MeasureData data, String date) throws Exception {

        double outp = data.getValues().get(0).getValue();
        double prev = outp;
        Date date1 = DateHandler.toDate(date);

        for (MeasureValues tmp : data.getValues()) {
            Date tmpDate = DateHandler.toDate(tmp.getDate());
            if (tmpDate.after(date1) && tmpDate.before(new Date())) {
                outp += Math.abs(prev - tmp.getValue());
                prev = tmp.getValue();
            }
        }
        return outp;
    }

    /**
     * This class represents pair of Sensor and Value, it is used in lowestValue function
     */
    public class ValueSensorsPair {
        private MeasureValues value;
        private Sensor sensor;

        public ValueSensorsPair(MeasureValues value, Sensor sensor) {
            this.value = value;
            this.sensor = sensor;
        }


        public MeasureValues getValue() {
            return value;
        }

        public void setValue(MeasureValues value) {
            this.value = value;
        }

        public Sensor getSensor() {
            return sensor;
        }

        public void setSensor(Sensor sensor) {
            this.sensor = sensor;
        }
    }

    /**
     * This method search in every station for lowest measured parameter and it's value in specified date. Measured
     * value must be higher than 0
     * @param date represents a date we want to check
     * @return {@link ValueSensorsPair} object representing pair of value and sensor which measured this value
     * @throws Exception when files cannot be found, REST API doesn;t provide JSON files or
     * when date canont be found in any measures list
     */
    public ValueSensorsPair lowestValue(String date) throws Exception {
        MeasureValues outp = null;
        Sensor sensor = null;
        Date dateChecked= DateHandler.toDate(date);
        boolean flag = false;
        for (HashMap.Entry<String, MeasureStation> tmpStation : stations.entrySet()) {
            Sensor[] tmpSensors = getSensors(tmpStation.getValue().getId());
            for (Sensor tmpSensor : tmpSensors) {
                MeasureData tmpData = getMeasureData(tmpSensor.getId());
                if (tmpData != null) {

                    for (MeasureValues tmpValue : tmpData.getValues()) {
                        Date dateTmp= DateHandler.toDate(tmpValue.getDate());
                        if (!flag && dateTmp.equals(dateChecked) && tmpValue.getValue()>0) {
                            outp = tmpValue;
                            flag = true;
                            sensor = tmpSensor;
                        }
                        if (dateTmp.equals(dateChecked) && tmpValue.getValue()>0 &&tmpValue.getValue() < outp.getValue()) {
                            outp = tmpValue;
                            sensor = tmpSensor;
                        }
                    }
                }
            }
        }
        if(outp!=null && sensor!= null ) {
            return new ValueSensorsPair(outp, sensor);
        }else {
            throw new Exception("Date provided cannot be found in any measures!: " + date);
        }
    }

    /**
     * This method returns set of Sensor and value pairs. Values must exceed their pollution standard
     * to be included in this set on given date. When there are no exceeds the list is empty. We use class
     * {@link ValueSensorPair}.
     * This method uses different class than {@link ValueSensorsPair} because we want to sort this list by exceeds.
     * So this method is able to compare objects by value's exceeds.
     * @param station {@link MeasureStation} object representing station we are checking
     * @param date representing date we want to check for pollution's exceeds
     * @return list of values-sensor pairs which exceeded pollution standards
     * @throws Exception when files cannot be found, REST API doesn't provide JSON files
     */
    public ArrayList<ValueSensorPair> findNMostPolluted(MeasureStation station,String date) throws Exception {
        Sensor[] sensors = getSensors(station.getId());
        Date dateChecked= DateHandler.toDate(date);
        ArrayList<ValueSensorPair> outp = new ArrayList<>();
        for(Sensor tmpSensor : sensors){
            MeasureData tmpData= getMeasureData(tmpSensor.getId());
                for(MeasureValues tmpValue : tmpData.getValues()){
                    Date dateTmp= DateHandler.toDate(tmpValue.getDate());
                    if(dateChecked.equals(dateTmp)){
                        ValueSensorPair tmpPair = new ValueSensorPair(tmpValue,tmpSensor);
                        if(tmpPair.deltaPollutedValue()>0) {
                            outp.add(tmpPair);
                        }
                        break;
                    }
                }
        }
        Collections.sort(outp);
        return outp;

    }

    /**
     * This class represents pair of value and sensor. It alaso has pollution Strategy for sorting.
     * while sorting not values are compared but their exceeds from pollution standards. It implements
     * Comparable interface to make it easy to sort in Containers.
     */
    class ValueSensorPair implements Comparable<ValueSensorPair> {


        private MeasureValues value;
        private PollutionStrategy pollutionStrategy;
        private Sensor sensor;

        public MeasureValues getValue() {
            return value;
        }

        public ValueSensorPair(MeasureValues value, Sensor sensor) throws Exception {
            this.value = value;
            this.sensor = sensor;
            this.pollutionStrategy = setPollutionStrategy();
        }

        public void setValues(MeasureValues value) {
            this.value = value;
        }

        public PollutionStrategy setPollutionStrategy() throws Exception {
            String form = sensor.getParam().getParamFormula();
            if (form.contains("CO")) {
                return new PollutionCO();
            } else if (form.contains("C6H6")) {
                return new PollutionC6H6();
            } else if (form.contains("NO")) {
                return new PollutionNO();
            } else if (form.contains("PB")) {
                return new PollutionPB();
            } else if (form.contains("PM10")) {
                return new PollutionPM10();
            } else if (form.contains("PM")) {
                return new PollutionPM25();
            } else if (form.contains("SO2")) {
                return new PollutionSO2();
            }else{
                return new PollutionOther();
            }
        }

        public String toString(){
            return sensor.getParam().getParamName() + " o wartości: " +value.getValue();
        }

        public Sensor getSensor() {
            return sensor;
        }

        public void setSensor(Sensor sensor) {
            this.sensor = sensor;
        }

        public double deltaPollutedValue() {
            return this.pollutionStrategy.getPollutionMax(this.getValue().getValue());
        }



        @Override
        public int compareTo(ValueSensorPair o) {
            return ((int) (this.deltaPollutedValue() - o.deltaPollutedValue()));
        }
    }

    /**
     * This method finds two values  and station where they were measured. One value is maximum and second is minimum of given
     * parameter.
     * @param param {@link Param} object represenitng parameter we want to check
     * @return {@link StationValueExtremes} object which represent two vaalues and corresponding stationns
     * @throws Exception when there is no file, REST API doesn't give correct JSON file
     */
    public StationValueExtremes getParamExtremes(Param param) throws Exception{

        MeasureValues lowValue = null;
        MeasureValues highValue=null;
        MeasureStation highStation=null;
        MeasureStation lowStation=null;
        boolean flag = false;
        for (HashMap.Entry<String, MeasureStation> tmpStation : stations.entrySet()) {
            Sensor[] tmpSensors = getSensors(tmpStation.getValue().getId());
            for (Sensor tmpSensor : tmpSensors) {
                if(tmpSensor.getParam().getParamFormula().trim().equals(param.getParamFormula().trim())){
                    MeasureData tmpData = getMeasureData(tmpSensor.getId());
                    if (tmpData != null) {

                        for (MeasureValues tmpValue : tmpData.getValues()) {

                            if (!flag && tmpValue.getValue() > 0) {

                                lowValue = tmpValue;
                                highValue = tmpValue;
                                lowStation = tmpStation.getValue();
                                highStation = tmpStation.getValue();
                                flag = true;

                            }
                            if (tmpValue.getValue() > 0 && tmpValue.getValue() < lowValue.getValue()) {
                                lowValue = tmpValue;
                                lowStation = tmpStation.getValue();
                            }
                            if (tmpValue.getValue() > 0 && tmpValue.getValue() > highValue.getValue()) {
                                highValue = tmpValue;
                                highStation = tmpStation.getValue();
                            }
                        }
                    }
                }
            }
        }
        if(lowStation!=null ) {
            return new StationValueExtremes(lowStation, highStation, lowValue, highValue);
        }else{
            throw new Exception("Nie znaleziono żadnej stacji, która mierzy podany parametr, być może podałeś zły wzór, skrót.\n" +
                    "Przykładowo tlenek węgla to 'CO' ");
        }

    }

    /**
     * This class represents two extreme values- highest and lowest, and in addition stores information about
     * their station, where they were measured
     */
    class StationValueExtremes {
        MeasureStation stationLow;
        MeasureStation stationHigh;
        MeasureValues valueLow;
        MeasureValues valueHigh;

        public StationValueExtremes(MeasureStation stationLow,MeasureStation stationHigh,
                                    MeasureValues valueLow, MeasureValues valueHigh){
            this.stationHigh=stationHigh;
            this.stationLow=stationLow;
            this.valueHigh=valueHigh;
            this.valueLow=valueLow;
        }

        public MeasureStation getStationLow() {
            return stationLow;
        }

        public void setStationLow(MeasureStation stationLow) {
            this.stationLow = stationLow;
        }

        public MeasureStation getStationHigh() {
            return stationHigh;
        }

        public void setStationHigh(MeasureStation stationHigh) {
            this.stationHigh = stationHigh;
        }

        public MeasureValues getValueLow() {
            return valueLow;
        }

        public void setValueLow(MeasureValues valueLow) {
            this.valueLow = valueLow;
        }

        public MeasureValues getValueHigh() {
            return valueHigh;
        }

        public void setValueHigh(MeasureValues valueHigh) {
            this.valueHigh = valueHigh;
        }
    }

    /**
     * This method returns list of objects containing infromation about stations and measures who were made there.
     * Measures must meet few conditions specified in params.
     * @param param represents param measuures we are looking for
     * @param dateFrom represents beginning of time period we are checking
     * @param dateTo represents end of time period we are checking
     * @param stations represents stations we want to check
     * @return ArrayList of {@link stationMeasures} objects representing station and measures
     * @throws Exception when there is no file, REST API doesn't provide correct JSON file, or when
     * there are no measures in given time period in any station
     */
    public ArrayList<stationMeasures> dataForDrawingChart(Param param,String dateFrom, String dateTo, MeasureStation [] stations)
            throws Exception{
        ArrayList<stationMeasures> outp = new ArrayList<>();
        Date dateFrom1= DateHandler.toDate(dateFrom);
        Date dateTo1= DateHandler.toDate(dateTo);

        for( MeasureStation tmpStation: stations ){
            Sensor tmpSensor= getSensor(tmpStation.getId(),param.getParamFormula());
            MeasureData tmpData =getMeasureData(tmpSensor.getId());
            stationMeasures tmpMeasureSet= new stationMeasures(tmpStation);
            for(MeasureValues tmpValue: tmpData.getValues()){
                Date tmpDate= DateHandler.toDate(tmpValue.getDate());
                if(tmpDate.equals(dateFrom1) || tmpDate.equals(dateTo1) ||
                        ( tmpDate.before(dateTo1) && tmpDate.after(dateFrom1))){
                    tmpMeasureSet.addValue(tmpValue);
                }
            }
            if(tmpMeasureSet.getSetSize()==0){
                throw new Exception("Nie znaleziono żadnego pomiaru podanego parametru w przedziale" +
                        " podanej daty na stacji: "+ tmpStation.getStationName() + " o id " + tmpStation.getId());
            }else{
                outp.add(tmpMeasureSet);
            }

        }
        return outp;
    }

    /**
     * This method draws a chart based on {@link stationMeasures} list, usually provided by dataForDrawingChart method
     * @param list list of Stations and measures to draw
     */
    public void drawChart(ArrayList<stationMeasures> list){
        double maxValue=0;
        String maxName="";
        for(stationMeasures tmp: list){
            if(tmp.getStation().getStationName().length()>maxName.length()){
                maxName= tmp.getStation().getStationName();
            }
            for(MeasureValues tmpValues : tmp.getValues()){
                if(tmpValues.getValue() > maxValue){
                    maxValue=tmpValues.getValue();
                }
            }
        }
        for(stationMeasures tmp : list){
            System.out.println(tmp.toString(maxValue,maxName));
        }
    }

    /**
     * This class stores information about station and contains values who were made on this station.
     * It is used to draw charts.
     */
    class stationMeasures{
        private MeasureStation station;
        private ArrayList<MeasureValues> values;

        public stationMeasures(MeasureStation station){
            this.station=station;
            this.values = new ArrayList<>();
        }
        public void addValue(MeasureValues value ){
            values.add(value);
        }
        public String toString(double maxValue, String longestName){
            StringBuilder builder=new StringBuilder();
            for(MeasureValues tmp: values){
                String name= station.getStationName();
                double val=tmp.getValue();
                builder.append(tmp.getDate() +" ( " + name + " )" + missingSpaces(name,longestName) + drawChart(val,maxValue) + val + "\n" );
            }
            return new String (builder);
        }
        public int getSetSize(){
            return this.values.size();
        }

        private String missingSpaces(String word, String maxWord){
            int spaces= maxWord.length() - word.length();
            char[] help = new char[spaces];
            for(int i=0;i<spaces;i++){
                help[i]=' ';
            }

            return new String(help);
        }
        private String drawChart(double value, double maxValue){
            char square= (char) 8718;
            int valPerSquare;
            if(maxValue<50){
                valPerSquare=1;
            }else {
                valPerSquare=(int) maxValue / 50;
            }
            int length;
            if(value - Math.floor(value) >=0.5){
                length =(int) value/valPerSquare + 2;
            }else{
                length= (int) value/valPerSquare +1;
            }
            char[] help= new char[length];
            for(int i=0;i<length-1;i++){
                help[i]=square;
            }
            help[help.length-1]=' ';
            return new String(help);
        }

        public MeasureStation getStation() {
            return station;
        }

        public void setStation(MeasureStation station) {
            this.station = station;
        }

        public ArrayList<MeasureValues> getValues() {
            return values;
        }

        public void setValues(ArrayList<MeasureValues> values) {
            this.values = values;
        }
    }
}
