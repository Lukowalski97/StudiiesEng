import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents a single Entry from BibTex
 *
 * @author lukow
 * @version %I% , %G%
 * @since 1.1
 */
public abstract class Entry {
    protected HashMap<String, StringPair> fields;

    protected String key;

    protected String[] optional;
    protected String[] required;

    /**
     * This constructor creates Entry without info about its type or key
     *
     * @param key String representing unique key representing Entry
     */
    public Entry(String key) {
        this.key = key;
    }

    /**
     * This method adds pair of strings ({@link StringPair}) to fields array with information about field type and its value
     *
     * @param field is a pair of strings which contains field name and its value
     */

    public void addField(StringPair field) {
        if (isNotOmitted(field.getName().trim())) {
            field.setName(refactorName(field.getName()));
            field.setValue(refactorValue(field.getValue()));
            fields.put(field.getName(), field);
        }
    }

    /**
     * This method checks whether Entry is made by author passed as argument
     * It checks 'author' and 'editor' fields, if Entry have any of them
     *
     * @param author is the author or editor that method is searching for
     * @return True if author was found in Entry, otherwise returns False
     */
    protected boolean containsAuthor(String author) {
        if (this.fields.get("author") != null && this.fields.get("author").getValue().contains(author)) {
            return true;
        }
        if (this.fields.get("editor") != null && this.fields.get("editor").getValue().contains(author)) {
            return true;
        }
        return false;
    }

    /**
     * This method checks if field of type passed as argument is used in Entry
     *
     * @param string name of field checked
     * @return True if Entry has such named field in required or optional fields, otherwise returns False
     */
    protected boolean isNotOmitted(String string) {
        for (String tmp : this.optional) {
            if (tmp.toLowerCase().contains(string.toLowerCase()))
                return true;
        }
        for (String tmp : this.required) {
            if (tmp.toLowerCase().contains(string.toLowerCase()))
                return true;
        }
        return false;
    }

    /**
     * This method is used before adding Entry to some Container, it checks if Entry has every required field
     *
     * @return True if Entry has all required fields, otherwise returns False
     */
    protected boolean hasRequiredFields() {
        for (String tmp : this.required) {
            String[] tmp1 = tmp.split("\\|");
            boolean isOK = false;
            for (int i = 0; i < tmp1.length; i++) {
                if (this.fields.get(tmp1[i]) != null) {
                    isOK = true;
                }
            }
            if (!isOK)
                return false;
        }
        return true;
    }

    // adds authors to authors list

    // refactors single author matching to one of 3 styles

    /**
     * This method adds pair of strings ({@link StringPair}) to fields array with information about field type and its value
     * It is similar to addField method, but its used when two pair of quoted values are divide by '#' - concatenation symbol
     *
     * @param field is a pair of strings which contains field name and its value
     */
    public void addFieldConcat(StringPair field) {
        if (isNotOmitted(field.getName().trim())) {
            field.setName(refactorName(field.getName()));
            String[] tmp = field.getValue().split("#");
            tmp[0] = tmp[0].trim().replaceAll("\"", "");
            tmp[1] = tmp[1].trim().replaceAll("\"", "");
            field.setValue(refactorValue(tmp[0] + tmp[1]).trim());
            fields.put(field.getName(), field);

        }
    }

    /**
     * This method format String containing field's name
     *
     * @param name field's name
     * @return formatted field's name
     */
    protected String refactorName(String name) {
        name = name.toLowerCase().trim();

        return name;
    }

    /**
     * This method format String containing field's value
     *
     * @param value field's value
     * @return formatted field's value
     */
    protected String refactorValue(String value) {
        value = value.replaceAll("\"", "").replaceAll(",", "").trim();

        return value;
    }

    /**
     * This method (overriden from {@link Object }class) converts Entry data into table made with ASCII code
     *
     * @return String representing Entry
     */
    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append(border());
        builder.append("|" + this.getClass().getSimpleName() + " (" + this.key + " )" +
                genSpaces(96 - (this.getClass().getSimpleName().length() + this.key.length())) + "|\n");
        builder.append(border());
        for (HashMap.Entry<String, StringPair> field : this.fields.entrySet()) {
            if (field.getValue().getName().contains("author") || field.getValue().getName().contains("editor")) {
                ArrayList<String> authors = addAuthor(field.getValue().getValue());
                builder.append(fieldRow(field.getValue().getName(), authors.get(0)));
                authors.remove(0);
                for (String tmp : authors) {
                    builder.append(fieldRow("", tmp));
                }
            } else {
                builder.append(fieldRow(field.getValue().getName(), field.getValue().getValue()));
            }
            builder.append(border());
        }

        return new String(builder);
    }

    private String border() {
        char[] tmp = new char[103];
        tmp[0] = '|';
        for (int i = 1; i < tmp.length - 1; i++) {
            tmp[i] = '-';
        }
        tmp[tmp.length - 2] = '|';
        tmp[tmp.length - 1] = '\n';
        return new String(tmp);
    }

    private String genSpaces(int amount) {
        char[] tmp = new char[amount];
        for (int i = 0; i < amount; i++) {
            tmp[i] = ' ';
        }
        return new String(tmp);
    }

    private String fieldRow(String name, String value) {
        StringBuilder builder = new StringBuilder();
        builder.append("|" + name + genSpaces(20 - name.length()) + "|");
        builder.append(value + genSpaces(79 - value.length()) + "|\n");
        return new String(builder);

    }

    private ArrayList<String> addAuthor(String author) {

        ArrayList<String> outp = new ArrayList<>();
        String[] tmp = author.split(" and ");
        for (String aut : tmp) {
            outp.add(refactorAuthor(aut));
        }
        return outp;
    }

    private String refactorAuthor(String author) {
        String[] tmp = author.split("\\|");
        if (tmp.length == 2) {
            author = tmp[1] + tmp[0];
        } else if (tmp.length == 3) {
            author = tmp[2] + tmp[0] + tmp[1];
        }
        return author;
    }

    /**
     * This method checks whether Entry is equals to object o
     *
     * @param o object compared with Entry
     * @return True if o is equal to Entry, otherwise returns False
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        if (!this.key.equals(((Entry) o).key))
            return false;

        return this.fields.equals(((Entry) o).fields);
    }

    /*




     */
}
