/**
 * This interface and corresponding method are used in Strategy pattern for finnding locale files
 */
public interface jsonPathStrategy {
    public String getPath(long id);

}
//D:\projekty\StudiiesEng\obiektowe2\
class jsonPathAll implements  jsonPathStrategy{
    @Override
    public String getPath(long id) {
        return "files\\findAll.json";
    }
}
class jsonPathSp implements  jsonPathStrategy{
    @Override
    public String getPath(long id) {
        return "files\\"+id+"sp.json";
    }
}

class jsonPathInd implements  jsonPathStrategy{
    @Override
    public String getPath(long id) {
        return "files\\"+id+"ind.json";
    }
}

class jsonPathDt implements  jsonPathStrategy{
    @Override
    public String getPath(long id) {
        return "files\\"+id+"dt.json";
    }
}