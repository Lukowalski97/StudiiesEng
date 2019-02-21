/**
 * This class contains information about Air index in station, which ID is stored in field 'id'. It contains
 * Date when index was calculated and object of {@link StIndexLevel} class, with index's details.
 */
public class AirIndex {
    private long id;
    private String stCalcDate;
    private StIndexLevel stIndexLevel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStCalcDate() {
        return stCalcDate;
    }

    public void setStCalcDate(String stCalcDate) {
        this.stCalcDate = stCalcDate;
    }

    public StIndexLevel getStIndexLevel() {
        return stIndexLevel;
    }

    public void setStIndexLevel(StIndexLevel stIndexLevel) {
        this.stIndexLevel = stIndexLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AirIndex index = (AirIndex) o;

        if (getId() != index.getId()) return false;
        if (!getStCalcDate().equals(index.getStCalcDate())) return false;
        return getStIndexLevel().equals(index.getStIndexLevel());
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getStCalcDate().hashCode();
        result = 31 * result + getStIndexLevel().hashCode();
        return result;
    }

    @Override
    public String toString() {
        if(stIndexLevel!=null) {
            return stIndexLevel.getIndexLevelName();
        }else  {
            return "Brak obliczonego indeksu dla podanej stacji";
        }

    }
    public boolean isNull(){
        return ( id==0 && stIndexLevel==null);
    }
}
