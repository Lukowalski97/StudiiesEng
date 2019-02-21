import java.util.LinkedHashMap;

public class TECHREPORT extends Entry {

    public TECHREPORT(String key){
        super(key);


        super.fields= new LinkedHashMap<>();


        super.optional= new String[]{"editor","volume|number","series","address","month","organization"
                ,"publisher","note","key"};
        super.required= new String[]{"author", "title", "institution", "year"};

    }
}
