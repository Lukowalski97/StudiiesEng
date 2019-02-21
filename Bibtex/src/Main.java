
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        //Parser parser = new Parser();

        //HashMap<String,Entry> result = parser.parseBib("test.bib");

//        for (HashMap.Entry<String,Entry> tmp: result.entrySet()) {
        //        System.out.println(tmp.getValue());
        //      }
        OptionsOperator operator = new OptionsOperator();
        operator.parseOptions(args);


    }
}
