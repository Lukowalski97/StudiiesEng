import org.junit.Test;

import static org.junit.Assert.*;

public class StringPairTest {
    @Test
    public void equals() throws Exception {
        assertEquals(new StringPair("first","second"),new StringPair("first","second"));
        assertNotEquals(new StringPair("first","second"),new StringPair("second","first"));

    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals(new StringPair("first","second").toString(),"[first,second]");
        assertNotEquals(new StringPair("first","second").toString(),"[first second]");
    }

}