/**
 * This class contains information about sensor which measure paramateres value once per hour. Every sensor
 * has it's own unique id and is connected to station where it is used. It has {@link Param} object about
 * parameter which is measured with this sensor.
 */
public class Sensor {

    private long id;
    private long stationId;
    private Param param;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId = stationId;
    }

    public Param getParam() {
        return param;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sensor sensor = (Sensor) o;

        if (getId() != sensor.getId()) return false;
        if (getStationId() != sensor.getStationId()) return false;
        return getParam() != null ? getParam().equals(sensor.getParam()) : sensor.getParam() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (int) (getStationId() ^ (getStationId() >>> 32));
        result = 31 * result + (getParam() != null ? getParam().hashCode() : 0);
        return result;
    }

    public void setParam(Param param) {
        this.param = param;
    }
}
