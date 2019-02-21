import java.util.LinkedHashMap;

public class MANUAL extends Entry {

    public MANUAL(String key){
        super(key);


        super.fields= new LinkedHashMap<>();


        super.optional= new String[]{"author","organization","address","edition","month","year","note","key"};
        super.required= new String[]{"title"};

    }
}
