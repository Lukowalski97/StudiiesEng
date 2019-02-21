import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This class contains list of {@link MeasureValues} objects, containing measures from single sensor. Type of measured
 * parameter is stored in key field.
 */
public class MeasureData {

    private String key;
    private ArrayList<MeasureValues> values;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<MeasureValues> getValues() {
        return values;
    }

    public void setValues(ArrayList<MeasureValues> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeasureData that = (MeasureData) o;

        if (!getKey().equals(that.getKey())) return false;
        return getValues().equals(that.getValues());
    }

    @Override
    public int hashCode() {
        int result = getKey().hashCode();
        result = 31 * result + getValues().hashCode();
        return result;
    }
    public boolean isNull(){
        return (this.key==null && this.values == null);
    }
}
