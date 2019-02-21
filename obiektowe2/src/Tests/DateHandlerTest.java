import org.junit.Test;

import javax.activation.DataHandler;

import java.util.Date;

import static org.junit.Assert.*;

public class DateHandlerTest {

    @Test
    public void dataParse() throws  Exception{

        Date date = DateHandler.toDate("2017-03-28 12:00:00");
        System.out.println(date);
        assertNotNull(date);
    }




}