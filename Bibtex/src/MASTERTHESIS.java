import java.util.LinkedHashMap;

public class MASTERTHESIS extends Entry {
    public MASTERTHESIS(String key){
        super(key);


        super.fields= new LinkedHashMap<>();


        super.optional= new String[]{"type","address","month","note","key"};
        super.required= new String[]{"author", "title", "school", "year"};

    }
}
