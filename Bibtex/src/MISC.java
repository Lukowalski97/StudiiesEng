import java.util.LinkedHashMap;

public class MISC extends Entry {

    public MISC(String key){
        super(key);


        super.fields= new LinkedHashMap<>();


        super.optional= new String[]{"author","title","howpublished","month","year","note","key"};
        super.required= new String[]{};

    }
}
