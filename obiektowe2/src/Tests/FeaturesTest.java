import com.google.gson.Gson;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import static com.sun.javaws.JnlpxArgs.verify;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;


public class FeaturesTest {
    @Test
    public void getAirIndex() throws Exception { //this test should not pass
        Features features = new Features();
        MeasureStation testedStation= new MeasureStation();
        testedStation.setId(1);
        AirIndex tested= features.getAirIndex(testedStation);
        assertEquals("Testowy",tested.getStIndexLevel().getIndexLevelName());
        assertNotEquals("Bardzo dobry",tested.getStIndexLevel().getIndexLevelName());

    }

    @Test
    public void getSensors() throws Exception {
        Features features=new Features();
        Gson gson= new Gson();
        String json="[{\"id\":282,\"stationId\":52,\"param\":{\"paramName\":\"benzen\",\"paramFormula\":\"C6H6\",\"paramCode\"" +
                ":\"C6H6\",\"idParam\":10}},{\"id\":285,\"stationId\":52,\"param\":{\"paramName\":\"tlenek węgla\",\"paramFormula\"" +
                ":\"CO\",\"paramCode\":\"CO\",\"idParam\":8}},{\"id\":291,\"stationId\":52,\"param\":{\"paramName\":\"dwutlenek azotu\"" +
                ",\"paramFormula\":\"NO2\",\"paramCode\":\"NO2\",\"idParam\":6}},{\"id\":293,\"stationId\":52,\"param\":{\"paramName\"" +
                ":\"ozon\",\"paramFormula\":\"O3\",\"paramCode\":\"O3\",\"idParam\":5}},{\"id\":297,\"stationId\":52,\"param\":{\"paramName\"" +
                ":\"dwutlenek siarki\",\"paramFormula\":\"SO2\",\"paramCode\":\"SO2\",\"idParam\":1}},{\"id\":14397,\"stationId\":52,\"param\"" +
                ":{\"paramName\":\"pył zawieszony PM10\",\"paramFormula\":\"PM10\",\"paramCode\":\"PM10\",\"idParam\":3}}]";
        Sensor [] sensorsExpected= gson.fromJson(json,Sensor[].class);
        Sensor [] sensorsActual= features.getSensors(52);
        Sensor [] sensorsFalse= features.getSensors(142);

        assertEquals(sensorsExpected,sensorsActual);
        assertNotEquals(sensorsExpected,sensorsFalse);
    }

    @Test
    public void WritingFile() throws Exception {


        try{
            InputStream is= new URL("http://time.jsontest.com/").openStream();
            InputStreamReader isr= new InputStreamReader(is, StandardCharsets.UTF_8);
            Gson gson=new Gson();
            Timetest time= gson.fromJson(isr,Timetest.class);
            String outp=gson.toJson(time);
            FileWriter fileWriter = new FileWriter("files\\test.json");
            fileWriter.write(outp);
            fileWriter.close();
            System.out.println(time);
            assertNotNull(time);
        }catch(IOException ex2){
            ex2.printStackTrace();
            throw new Exception("cos tam list cannot be found!");
        }
    }

    @Test
    public void sumDeltas() throws Exception {
        Features features=new Features();
        MeasureData data=new MeasureData();
        ArrayList<MeasureValues> list = new ArrayList<>();
        for(int i=0;i<12;i++){
            MeasureValues tmp= new MeasureValues();
            tmp.setValue(2*i);
            tmp.setDate("2017-03-28 11:00:00");
            list.add(tmp);
        }
        list.get(3).setDate("2017-03-28 13:00:00");
        data.setValues(list);
        double sum= features.sumDeltas(data,"2017-03-28 8:00:00");
        assertTrue(sum==(2*(list.size()-1)) );
    }

}