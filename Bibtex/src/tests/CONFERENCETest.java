import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class CONFERENCETest {

    @Test
    public void parseTest() throws Exception{
        Parser parser= new Parser();
        String tmp="conference";
        HashMap<String,Entry> entry = parser.parseBib("CONFERENCETest.bib");
        assertFalse(entry.get(tmp)==null);

        assertEquals(entry.get(tmp).getClass().getSimpleName(),tmp.toUpperCase());
        System.out.println(entry);
    }

}