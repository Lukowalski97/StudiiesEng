import org.junit.Test;

import static org.junit.Assert.*;

public class EntryTest {
    @Test
    public void addField() throws Exception {
        Entry testEntry= new BOOK("klucz") ;
        testEntry.addField(new StringPair("nonField","whatever"));
        assertEquals(0,testEntry.fields.size());

        testEntry.addField(new StringPair("Title","whatever"));
        assertEquals(1,testEntry.fields.size());
    }

    @Test
    public void equals() throws Exception {
        Entry testEntry= new BOOK("klucz") ;
        testEntry.addField(new StringPair("Title","whatever"));
        testEntry.addField(new StringPair("publisher","whatever2"));

        Entry testEntry1= new BOOK("klucz") ;
        testEntry1.addField(new StringPair("Title","whatever"));
        testEntry1.addField(new StringPair("PuBLiSher","whatever2"));

        assertEquals(testEntry,testEntry1);

        Entry testEntry2= new BOOK("klsucz") ;
        testEntry1.addField(new StringPair("Title","whatever"));
        testEntry1.addField(new StringPair("PuBLiSher","whatever2"));

        assertNotEquals(testEntry,testEntry2);
    }
    @Test
    public void requiredTest()throws Exception{
        Entry entry= new BOOK("book");
        assertFalse(entry.hasRequiredFields());
        entry.addField(new StringPair("editor","w1"));
        entry.addField(new StringPair("title","w1"));
        entry.addField(new StringPair("publisher","w1"));
        entry.addField(new StringPair("year","w1"));
        assertTrue(entry.hasRequiredFields());
    }

    @Test
    public void isOmittedTest() throws Exception{
        Entry entry= new BOOK("book");
        assertTrue(entry.isNotOmitted("title"));
        assertTrue(entry.isNotOmitted("author"));
        assertTrue(entry.isNotOmitted("publisher"));
        assertTrue(entry.isNotOmitted("year"));

        assertFalse(entry.isNotOmitted("kot"));
        assertFalse(entry.isNotOmitted("sth"));
        assertFalse(entry.isNotOmitted("diff"));
    }


}