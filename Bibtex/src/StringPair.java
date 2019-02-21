/**
 * This class represents pair of two Strings, first one called 'name', and second one called 'value'
 * @author lukow
 * @version %I% , %G%
 * @since 1.1
 */

public class StringPair {

    private String name;
    private String value;

    /**
     * This constructor creates new String pair from two Strings
     * @param name String which contains information about pair's name
     * @param value String which contains information about pair's value
     */
    public StringPair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public StringPair() {

    }

    /**
     * This method returns 'name' field's String
     * @return value of 'name' String
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method returns 'value' field's String
     * @return value of 'value' String
     */
    public String getValue() {
        return this.value;
    }

    /**
     * This method sets 'value' field's String
     * @param value new 'value' String
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * This method sets 'name' field's String
     * @param name new 'name' String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method checks whether StringPair is equal to object o
     * @param o object compared with StringPair
     * @return True if o is equal to StringPair, otherwise returns False
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringPair that = (StringPair) o;

        if (!getName().equals(that.getName())) return false;
        return getValue().equals(that.getValue());
    }

    /**
     * This method generate hashCode of StringPair object
     * @return hashCode
     */
    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getValue().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "[" +this.name + "," + this.value + "]";

    }
}
