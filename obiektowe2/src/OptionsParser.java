import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class Parses arguments from command line, checks their correctnes and use proper functions based on input.
 */
public class OptionsParser {

    private Features features;

    public OptionsParser() throws IOException{
        features=new Features();
    }

    /**
     * This method parses options from command line, when there are no arguments it shows help
     * @param args String array containing user's input
     * @throws Exception when given arguments doesn't match to parser's options
     */
    public void parseOptions(String [] args)throws  Exception{

        if(args.length==0){
            System.out.println("Wywołałeś program bez argumentów- posiada on 8 możliwych opcji użycia.\n" +
                    "Data powinna być zapisana w formacie: \"yyyy-MM-dd HH\"\",np  2017-03-28 11, dane są mierzone co godzinę. \n " +
            "Argumenty podaje się następująco: {numer opcji} [lista argumentów opcji] \" 1 Działoszyn\"\n" +
                    "Opcje programu oraz ich argumenty są następujące:\n" +
                    "1 {nazwa stacji pomiarowej} - program wypisze aktualny indeks jakości powietrza \n" +
                    "dla podanej stacji pomiarowej, np. : 1 Działoszyn \n" +
                    "2 {data} {stacja pomiarowa} {parametr} - program wypisze dla tych danych" +
                    "wartości podanego parametru np. : 2 2017-03-28 11:00:00 Działoszyn PM10\n" +
                    "3 {parametr} {stacja pomiarowa} {data odkąd} {data do}  - program wypisze \n" +
                    "średnią wartość parametru za podany okres, np. : \n" +
                    "3 PM10 Działoszyn \n" +
                    "4 {data}  [nazwy stacji] - program wypisze parametr, który na danych stacjach od podanej daty\n" +
                    "uległ największym wahaniom, np. : 4 2017-03-28 11:00:00 Działoszyn Mielec Kielce  \n" +
                    "5  {data} - program odszuka i wypisze parametr, którego wartość była namniejsza o podanej godzinie podanego dnia,\n" +
                    "np. : 5 2017-03-28 11:00:00\n" +
                    "6 {nazwa stacji} {data} {liczba N}- program wypisze N  posrotowanych rosnąco względem wartości przekroczenia normy\n" +
                    "nazw parametrów i stanowisk pomiarów, gdzie zanieczyszczenia\n" +
                    "w podanej dacie przekroczyły normy zanieczyszczenia, np. 6 Działoszyn 2017-03-28 11:00:00 3 \n" +
                    "7 {parametr} - program wypisze stanowisko oraz datę pomiaru gdzie parametr przyjął największą oraz najmniejszą wartość.\n" +
                    "Np. : 7 {CO}\n" +
                    "8 {parametr} {data odkąd} [data dokąd} [lista stacji] - program wypisze wykresy wartości \n" +
                    "dla podanych stacji danego parametru w podanym zakresie,np . : 8 CO 2017-03-28 11:00:00 2017-03-28 13:00:00 \"Działoszyn\" \"Kielce\"\n"
                    );
        }else{
            int option= Integer.parseInt(args[0]);
            if(!Character.isDigit(args[0].trim().charAt(0)) && (option<1 ||option>8 ) ){
                throw new IllegalArgumentException("Incorrect first argument- wrong option number!");
            }else{
                switch(option){
                    case 1:
                        option1(args);
                        break;
                    case 2:
                        option2(args);
                        break;
                    case 3:
                        option3(args);
                        break;
                    case 4:
                        option4(args);
                        break;
                    case 5:
                        option5(args);
                        break;
                    case 6:
                        option6(args);
                        break;
                    case 7:
                        option7(args);
                        break;
                    case 8:
                        option8(args);
                        break;
                }
            }
        }
    }

    /**
     * This method represents first functionality of program- it finds {@link AirIndex } of given {@link MeasureStation}
     * @param args String array of arguments for option
     * @throws Exception
     */
    public void option1(String [] args)throws Exception{
        if(args.length!=2){
            throw new IllegalArgumentException("Wrong arguments amount!!! ");
        }else{
            MeasureStation station=features.findStationByName(args[1]);
            AirIndex ind= features.getAirIndex(station);
            System.out.println("Stacja " + station.getStationName() + " ma indeks:" +ind );
        }
    }
    /**
     * This method represents second functionality of program- it finds given parameter's and date's value
     * @param args String array of arguments for option
     * @throws Exception
     */
    public void option2(String [] args)throws Exception{
        if(args.length!=5){
            throw new IllegalArgumentException("Wrong arguments amount!!! ");
        }else{
            MeasureStation station=features.findStationByName(args[3]);
            String date = reDate(args[1],args[2]);
            MeasureValues value= features.getValuebyDateAndStation(args[4],date,station.getId());
            System.out.println( "Parametr " + args[4] + value);
        }

/**
 * This method represents third functionality of program- it finds average value of parameter from given time period
 * @param args String array of arguments for option
 * @throws Exception
 */
    }
    public void option3(String [] args) throws Exception{
        if(args.length!=7){
            throw new IllegalArgumentException("Wrong arguments amount!!! ");
        }else{
            MeasureStation station=features.findStationByName(args[2]);
            String dateF=reDate(args[3],args[4]);
            Date dateFrom= DateHandler.toDate(dateF);
            String dateT= reDate(args[5],args[6]);
            Date dateTo= DateHandler.toDate(dateT);
           double average= features.getAverage(args[1],station.getId(),dateFrom,dateTo);
            System.out.println( "Parametr " + args[1] + " przyjął na stacji  " + station.getStationName() +
                    " w ostatnim czasie średnią wartość: " + average);
        }
    }
    /**
     * This method represents 4th functionality of program- it finds parameter which had highest fluctuations in given
     * time period
     * @param args String array of arguments for option
     * @throws Exception
     */
    public void option4(String [] args)throws Exception{
        if(args.length<4){
            throw new IllegalArgumentException("Wrong arguments amount!!! ");
        }else{
            ArrayList<MeasureStation> stations= new ArrayList<>();
            String date=reDate(args[1],args[2]);
            for(int i=3;i<args.length;i++){
                MeasureStation tmp= features.findStationByName(args[i]);
                if(tmp!=null) {
                    stations.add(tmp);
                }
            }
            if(stations.size()==0){
                throw new Exception(" Żadna z podanych stacji nie widnieje w spisie");
            }
            Param param=features.greatestDeltas(stations,date);
            System.out.println( "Parametr, który uległ w największym wahaniom od " + date +" to " + param);
        }
    }
    /**
     * This method represents 5th functionality of program- it finds lowest value amomng all stations and parameters
     * @param args String array of arguments for option
     * @throws Exception
     */
    public void option5(String [] args)throws  Exception{
        if(args.length!=3){
            throw new IllegalArgumentException("Zła ilość argumentów! Wymagane 3.");
        }else{
            String date= args[1] + " " + args[2];
            Features.ValueSensorsPair value= features.lowestValue(date);
            System.out.println("Najmniejszą wartością odnalezioną " + date + " był parametr" + value.getSensor().getParam().getParamFormula() +
            " o wartości " + value.getValue().getValue() + "ug ");
        }
    }
    /**
     * This method represents 6th functionality of program- it finds find N sensors and values which exceeded pollution's staandards
     * @param args String array of arguments for option
     * @throws Exception
     */
    public void option6(String [] args)throws Exception{
        if(args.length!=5){
            throw new IllegalArgumentException("Zła ilośc argumentów! Wymagane 5.");
        }else{
            String date = reDate(args[2],args[3]);
            MeasureStation station = features.findStationByName(args[1]);
            int n= Integer.parseInt(args[4]);

            ArrayList<Features.ValueSensorPair> outp = features.findNMostPolluted(station,date);
            System.out.println(n + " posortowanych stanowisk i pomiarów z dnia " + date + " wg rosnącej wartości odchyleń od \n" +
                    "dopuszczalnej normy zanieczyszczenia parametru:\n");
            for(int i=0; i < Math.min(outp.size(),n); i++){
                System.out.println( (i+1) + ". : pomiar: " + outp.get(i));
            }
        }
    }
    /**
     * This method represents 7th functionality of program- it finds given parameter's highest and lowest values
     * and corresponding stations
     * @param args String array of arguments for option
     * @throws Exception
     */
    public void option7(String [ ] args )throws Exception{
        if(args.length!=2){
            throw new IllegalArgumentException("Zła ilośc argumentów! Wymagane 2.");
        }else{
            String param= args[1];
            if(param.contains("pm2.5") || param.contains("PM2.5") || param.contains("PM2,5") || param.contains("pm2,5")){
                param="PM25";
            }
            param =param.toUpperCase();
            Param param1= new Param();
            param1.setParamFormula(param);
            Features.StationValueExtremes outp = features.getParamExtremes(param1);
            System.out.println("Parametr " + param1.getParamFormula() +" przyjął wartość największą:\n " +
                    outp.getValueHigh().getValue() + " dnia: " + outp.getValueHigh().getDate() + " na stacji " + outp.getStationHigh().getStationName()+
                    "\n a wartośc najmniejszą :\n" +
                    outp.getValueLow().getValue() + " dnia " + outp.getValueLow().getDate() + " na stacji " + outp.getStationLow().getStationName());
        }
    }
    /**
     * This method represents 8th functionality of program- it draws a Chart for given parameters, stations and period of time
     * @param args String array of arguments for option
     * @throws Exception
     */
    public void option8(String [] args)throws Exception{
        if(args.length<7){
            throw new IllegalArgumentException("Zła ilośc argumentów! Wymagane minimum 7.");
        }else{
            String param= args[1];
            if(param.contains("pm2.5") || param.contains("PM2.5") || param.contains("PM2,5") || param.contains("pm2,5")){
                param="PM25";
            }
            param =param.toUpperCase();
            Param param1= new Param();
            param1.setParamFormula(param);

            String dateFrom= reDate(args[2],args[3]);
            String dateTo=reDate(args[4],args[5]);

            MeasureStation [] list = new MeasureStation[args.length-6];
            for(int i=6;i<args.length;i++){
                MeasureStation tmp = features.findStationByName(args[i]);
                list[i-6]= tmp;
            }
            ArrayList<Features.stationMeasures> data= features.dataForDrawingChart(param1,dateFrom,dateTo,list);
            System.out.println("Pomiary dla parametru " + param + " :");
            features.drawChart(data);
        }

    }

    /**
     * This method converts two String from output to one String in proper date format
     * @param date String represenitng year,month,day
     * @param hour String representing hour
     * @return
     */
    public String reDate(String date, String hour){
        return date.trim() + " " + hour.trim() + ":00:00";
    }
}
