import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class changes String to Date, it is sometimes used to compare dates.
 */
public class DateHandler {


    /**
     * This method parses String to Date object
     * @param date String representing date
     * @return Date
     * @throws Exception when String has date in wrong foromat
     */
    public static Date toDate(String date)throws  Exception{
        Date outp;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            outp = formatter.parse(date);
            return outp;
        }catch(ParseException e){
            e.printStackTrace();
            throw new Exception ("Wrong format date!!!");
        }
    }


}
