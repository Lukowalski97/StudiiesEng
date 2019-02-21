import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ParserTest {
    Parser testParser;

    @Test
    public void parseBib() throws Exception { // this test shouldn't pass due to changes made on purpose
        testParser = new Parser();
        HashMap<String, Entry> list1 = testParser.parseBib("test.bib");
        HashMap<String, Entry> list2 = testParser.parseBib("test2.bib");
        HashMap<String, Entry> list3 = testParser.parseBib("test3.bib");
        assertEquals(list1, list2);
        assertFalse(list1.equals(list3));
        assertFalse(list2.equals(list3));
    }

    @Test(expected = FileNotFoundException.class)
    public void exceptionTest() throws Exception {
        testParser = new Parser();
        testParser.parseBib("noexistingFile");
    }

    @Test
    public void concatTest() throws Exception {
        testParser = new Parser();
        HashMap<String, Entry> list1 = testParser.parseBib("concatTest.bib");
        HashMap<String, Entry> list2 = testParser.parseBib("concatTest1.bib");
        HashMap<String, Entry> list3 = testParser.parseBib("concatTest2.bib");
        assertEquals(list1, list2);
        assertNotEquals(list1, list3);
    }

    @Test
    public void StringsTest() throws Exception {// this test shouldn't pass due to changes made on purpose
        testParser = new Parser();
        HashMap<String, Entry> list1 = testParser.parseBib("stringTest.bib");
        HashMap<String, Entry> list2 = testParser.parseBib("stringTest1.bib");
        HashMap<String, Entry> list3 = testParser.parseBib("stringTest2.bib");
        assertEquals(list1, list2);
        assertNotEquals(list1, list3);
    }

}