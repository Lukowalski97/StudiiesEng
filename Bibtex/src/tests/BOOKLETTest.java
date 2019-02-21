import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class BOOKLETTest {

    @Test
    public void parseTest() throws Exception{
        Parser parser= new Parser();
        HashMap<String,Entry> entry = parser.parseBib("BOOKLETTest.bib");
        assertFalse(entry.get("booklet")==null);

        assertEquals(entry.get("booklet").getClass().getSimpleName(),"BOOKLET");
        System.out.println(entry);
    }

}