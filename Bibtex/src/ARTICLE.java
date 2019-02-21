import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ARTICLE extends Entry {



    public ARTICLE(String key){
        super(key);


        super.fields= new LinkedHashMap<>();


        super.optional= new String[]{"volume","number","pages","month","note","key"};
        super.required= new String[]{"author", "title", "journal", "year"};

    }


}
