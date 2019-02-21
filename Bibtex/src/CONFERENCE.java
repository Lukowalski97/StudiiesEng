import java.util.LinkedHashMap;

public class CONFERENCE extends Entry {

    public CONFERENCE(String key){
        super(key);


        super.fields= new LinkedHashMap<>();


        super.optional= new String[]{"editor", "volume|number","series","pages","address","edition","month","organization"
                ,"publisher","note","key"};
        super.required= new String[]{"author", "title", "booktitle", "year"};

    }
}
