import java.io.FileDescriptor;
import java.io.FileInputStream;

/**
 * Program is using GSON API to downloaad and store JSON files from  http://powietrze.gios.gov.pl/pjp/content/api
 * which is Polish governemnt's site monitoring air pollution. It is described in polish:
 * "Data powinna być zapisana w formacie: \"yyyy-MM-dd HH\"\",np  2017-03-28 11, dane są mierzone co godzinę. \n " +
 *"Argumenty podaje się następująco: {numer opcji} [lista argumentów opcji] \" 1 Działoszyn\"\n" +
 *"Opcje programu oraz ich argumenty są następujące:\n" +
 *"1 {nazwa stacji pomiarowej} - program wypisze aktualny indeks jakości powietrza \n" +
 *"dla podanej stacji pomiarowej, np. : 1 Działoszyn \n" +
 *"2 {data} {stacja pomiarowa} {parametr} - program wypisze dla tych danych" +
 **"wartości podanego parametru np. : 2 2017-03-28 11:00:00 Działoszyn PM10\n" +
* "3 {parametr} {stacja pomiarowa} {data odkąd} {data do}  - program wypisze \n" +
 *"średnią wartość parametru za podany okres, np. : \n" +
 *"3 PM10 Działoszyn \n" +
* "4 {data}  [nazwy stacji] - program wypisze parametr, który na danych stacjach od podanej daty\n" +
* "uległ największym wahaniom, np. : 4 2017-03-28 11:00:00 Działoszyn Mielec Kielce  \n" +
* "5  {data} - program odszuka i wypisze parametr, którego wartość była namniejsza o podanej godzinie podanego dnia,\n" +
* "np. : 5 2017-03-28 11:00:00\n" +
* "6 {nazwa stacji} {data} {liczba N}- program wypisze N  posrotowanych rosnąco względem wartości przekroczenia normy\n" +
 *"nazw parametrów i stanowisk pomiarów, gdzie zanieczyszczenia\n" +
 *"w podanej dacie przekroczyły normy zanieczyszczenia, np. 6 Działoszyn 2017-03-28 11:00:00 3 \n" +
* "7 {parametr} - program wypisze stanowisko oraz datę pomiaru gdzie parametr przyjął największą oraz najmniejszą wartość.\n" +
* "Np. : 7 {CO}\n" +
* "8 {parametr} {data odkąd} [data dokąd} [lista stacji] - program wypisze wykresy wartości \n" +
* "dla podanych stacji danego parametru w podanym zakresie,np . : 8 CO 2017-03-28 11:00:00 2017-03-28 13:00:00 \"Działoszyn\" \"Kielce"
 *
 *
 * It is used to check and calculate informations based on JSON DATA
 */
public class Main {
    /**
     * This method only initiates functions which are stored in different classes.
     * @param args String array with program input arguments .
     * @throws Exception
     */
    public static void main(String[] args)throws Exception {


    OptionsParser optionsParser = new OptionsParser();
    optionsParser.parseOptions(args);

    }
}


