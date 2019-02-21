import java.util.LinkedHashMap;

public class INBOOK extends Entry {

    public INBOOK(String key){
        super(key);


        super.fields= new LinkedHashMap<>();


        super.optional= new String[]{"volume|number","series","type","address","edition","month","note","key"};
        super.required= new String[]{"author|editor", "title","chapter|pages", "publisher", "year"};

    }
}
