import jdk.nashorn.internal.runtime.ECMAException;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class BOOKTest {

    @Test
    public void parseTest() throws Exception{
        Parser parser= new Parser();
        HashMap<String,Entry> entry = parser.parseBib("BOOKTest.bib");
        assertFalse(entry.get("book")==null);

        assertEquals(entry.get("book").getClass().getSimpleName(),"BOOK");
        System.out.println(entry);
    }


}