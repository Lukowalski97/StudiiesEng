import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class OptionsOperatorTest {

    @Test(expected = FileNotFoundException.class)
    public void exceptionTest()throws Exception{
      Parser  testParser= new Parser();
        testParser.parseBib("noexistingFile");
    }


}