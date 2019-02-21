import java.util.LinkedHashMap;

public class BOOKLET extends Entry{

    public BOOKLET(String key){
        super(key);


        super.fields= new LinkedHashMap<>();


        super.optional= new String[]{"author","howpublished","address","month","year","note","key"};
        super.required= new String[]{"title"};

    }
}
