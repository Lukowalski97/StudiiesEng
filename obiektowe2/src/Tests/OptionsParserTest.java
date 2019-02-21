import org.junit.Test;

import static org.junit.Assert.*;

public class OptionsParserTest {

    @Test
    public void option0() throws Exception {
        OptionsParser parser= new OptionsParser();
        String [] args = {};
        parser.parseOptions(args);
    }
    @Test
    public void option1() throws Exception {
        OptionsParser parser=new OptionsParser();
        String [ ] args= {"1", "Kraków, Aleja Krasińskiego"};
        parser.parseOptions(args);
    }

    @Test
    public void option2()throws  Exception{
        OptionsParser parser=new OptionsParser();
        String [ ] args= {"2","2019-01-20","18", "Białystok-Warszawska","PM10"};
        parser.parseOptions(args);
    }

    @Test
    public void option3()throws  Exception{
        OptionsParser parser=new OptionsParser();
        String [ ] args= {"3", "PM10","Kraków, Aleja Krasińskiego","2019-01-21", "7","2019-01-21" ,"10"};
        parser.parseOptions(args);
    }

    @Test
    public void option4()throws  Exception{
        OptionsParser parser=new OptionsParser();
        String [ ] args= {"4","2019-01-20" ,"18", "Legnica - Rzeczypospolitej"};
        parser.parseOptions(args);
    }

    @Test
    public void option5() throws Exception{
        OptionsParser parser=new OptionsParser();
        String [ ] args= {"5","2019-01-21" ,"00:00:00"};
        parser.parseOptions(args);
    }

    @Test
    public void option6() throws Exception{
        OptionsParser parser=new OptionsParser();
        String [ ] args= {"6","Wałbrzych - Wysockiego" ,"2019-01-20", "9","3"};
        parser.parseOptions(args);
    }

    @Test
    public void option7() throws Exception{
        OptionsParser parser=new OptionsParser();
        String [ ] args= {"7","PM10"};
        parser.parseOptions(args);
    }

    @Test
    public void option8() throws Exception{
        OptionsParser parser=new OptionsParser();
        String [ ] args= {"8","PM10", "2019-02-20" ,"0", "2019-02-20", "11","Częstochowa, ul. AK/Jana Pawła II", "AM2 Gdańsk Stogi" };
        parser.parseOptions(args);
    }

}