/**
 * This class contains information about air index, in field 'indexLevelName' is stored grade of index calculated
 * on station, for instance index may be : good, very bad etc.
 */
public class StIndexLevel {

    private long id;
    private String indexLevelName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StIndexLevel that = (StIndexLevel) o;

        if (getId() != that.getId()) return false;
        return getIndexLevelName() != null ? getIndexLevelName().equals(that.getIndexLevelName()) : that.getIndexLevelName() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getIndexLevelName() != null ? getIndexLevelName().hashCode() : 0);
        return result;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIndexLevelName() {
        return indexLevelName;
    }

    public void setIndexLevelName(String indexLevelName) {
        this.indexLevelName = indexLevelName;
    }
}
