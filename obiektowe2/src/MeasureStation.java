import java.util.HashMap;

public class MeasureStation  {

    private long id;
    private String stationName;
    private Double gegrLat;
    private Double gegrLon;
    private City city;
    private String addressStreet;

    private AirIndex index;

    public AirIndex getIndex(){
        return index;
    }

    public void setIndex(AirIndex index){
        this.index=index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeasureStation that = (MeasureStation) o;

        if (getId() != that.getId()) return false;
        if (!getStationName().equals(that.getStationName())) return false;
        if (!getGegrLat().equals(that.getGegrLat())) return false;
        if (!getGegrLon().equals(that.getGegrLon())) return false;
        if (!getCity().equals(that.getCity())) return false;
        if (!getAddressStreet().equals(that.getAddressStreet())) return false;
        return getIndex() != null ? getIndex().equals(that.getIndex()) : that.getIndex() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getStationName().hashCode();
        result = 31 * result + getGegrLat().hashCode();
        result = 31 * result + getGegrLon().hashCode();
        result = 31 * result + getCity().hashCode();
        result = 31 * result + getAddressStreet().hashCode();
        result = 31 * result + (getIndex() != null ? getIndex().hashCode() : 0);
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Double getGegrLat() {
        return gegrLat;
    }

    public void setGegrLat(Double gegrLat) {
        this.gegrLat = gegrLat;
    }

    public Double getGegrLon() {
        return gegrLon;
    }

    public void setGegrLon(Double gegrLon) {
        this.gegrLon = gegrLon;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }
}
