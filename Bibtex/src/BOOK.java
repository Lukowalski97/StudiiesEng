import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class BOOK extends Entry {


    public BOOK(String key){
        super(key);


        super.fields= new LinkedHashMap<>();


        super.optional= new String[]{"volume","series","address","edition","month","note","key"};
        super.required= new String[]{"author|editor", "title", "publisher", "year"};

    }


}
