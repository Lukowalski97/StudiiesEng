/**
 * This class stores information about single measure and date when it was made.
 */
public class MeasureValues {

    private String date;
    private double value;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeasureValues that = (MeasureValues) o;

        if (Double.compare(that.getValue(), getValue()) != 0) return false;
        return getDate() != null ? getDate().equals(that.getDate()) : that.getDate() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getDate() != null ? getDate().hashCode() : 0;
        temp = Double.doubleToLongBits(getValue());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public double getValue() {
       // if(value==null)
         //   return 0;
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return " ,Wartość: "+ getValue() + " zmierzona dnia: " +getDate();
    }
}
