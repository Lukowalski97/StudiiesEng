import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class ARTICLETest {

    @Test
    public void parseTest() throws Exception{
        Parser parser= new Parser();
        HashMap<String,Entry> entry = parser.parseBib("ARTICLETest.bib");
        assertFalse(entry.get("article")==null);

        assertEquals(entry.get("article").getClass().getSimpleName(),"ARTICLE");
        System.out.println(entry);
    }

}