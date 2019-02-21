import java.util.LinkedHashMap;

public class INCOLLECTION extends  Entry {

    public INCOLLECTION(String key){
        super(key);


        super.fields= new LinkedHashMap<>();


        super.optional= new String[]{"editor","volume|number","series","type","chapter","pages"
                ,"address","edition","month","note","key"};
        super.required= new String[]{"author", "title","booktitle", "publisher", "year"};

    }
}
