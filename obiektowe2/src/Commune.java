/**
 * This class contains detailed information about city's commune address.
 */
public class Commune {
    private String communeName;
    private String districtName;
    private String provinceName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Commune commune = (Commune) o;

        if (getCommuneName() != null ? !getCommuneName().equals(commune.getCommuneName()) : commune.getCommuneName() != null)
            return false;
        if (getDistrictName() != null ? !getDistrictName().equals(commune.getDistrictName()) : commune.getDistrictName() != null)
            return false;
        return getProvinceName() != null ? getProvinceName().equals(commune.getProvinceName()) : commune.getProvinceName() == null;
    }

    @Override
    public int hashCode() {
        int result = getCommuneName() != null ? getCommuneName().hashCode() : 0;
        result = 31 * result + (getDistrictName() != null ? getDistrictName().hashCode() : 0);
        result = 31 * result + (getProvinceName() != null ? getProvinceName().hashCode() : 0);
        return result;
    }

    public String getCommuneName() {

        return communeName;
    }

    public void setCommuneName(String communeName) {
        this.communeName = communeName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
