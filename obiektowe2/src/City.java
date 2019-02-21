/**
 * This class contains information about City of station. It contains detailed commune informations in
 * {@link Commune} object
 */
public class City {
    private long id;
    private  String name;
    private Commune commune;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (getId() != city.getId()) return false;
        if (getName() != null ? !getName().equals(city.getName()) : city.getName() != null) return false;
        return getCommune() != null ? getCommune().equals(city.getCommune()) : city.getCommune() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCommune() != null ? getCommune().hashCode() : 0);
        return result;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Commune getCommune() {
        return commune;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }
}
