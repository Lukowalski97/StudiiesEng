import java.util.LinkedHashMap;

public class UNPUBLISHED extends Entry {

    public UNPUBLISHED(String key){
        super(key);


        super.fields= new LinkedHashMap<>();


        super.optional= new String[]{"month","year","key"};
        super.required= new String[]{"author", "title","note"};

    }
}
