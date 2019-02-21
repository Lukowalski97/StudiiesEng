import java.util.LinkedHashMap;

public class PHDTHESIS  extends Entry{

    public PHDTHESIS(String key){
        super(key);


        super.fields= new LinkedHashMap<>();


        super.optional= new String[]{"type","address","month","note","key"};
        super.required= new String[]{"author", "title", "school", "year"};

    }
}
