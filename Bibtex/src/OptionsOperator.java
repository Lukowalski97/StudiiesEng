import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * This class is used to parse options passed to program via console
 * It checks how many arguments are passed and then
 * execute program based on number of args
 */
public class OptionsOperator {

    private HashMap<String, Entry> entries;

    /**
     * This method checks length of Array passed, and execute 4 in four different ways
     *
     * @param args Array of arguments passed to program via console
     * @throws FileNotFoundException if file passed as first argument cannot be found
     */
    public void parseOptions(String[] args) throws FileNotFoundException {
        if (args.length == 0) {
            System.out.println("You called Parser without arguments: You have 4 avalaible options calling this program:\n" +
                    "1. With one argument being .bib file path -> program will display every Entry found.\n" +
                    "2.a With two arguments, first being .bib file path and second in form '@type' where type is type of Entry.\n" +
                    "Program will display only Entries of type passed as argument.\n" +
                    "3.b With two arguments, first being .bib file path and second without '@' which is searched author name.\n" +
                    "Program will display only Entries where author passed as argument is contained in Entry's 'author' or 'editor' field.\n" +
                    "4. With two arguments, first being .bib file path, second being '@type' and third being author searched for.\n" +
                    "Proram will display only Entries where author and type match.\n");

            //displayAllEntries();
        } else {
            args[0] = args[0].replaceAll("\"", "");
            parse(args[0]);
            if (args.length == 1) {

                displayAllEntries();
            } else if (args.length == 2) {
                args[1] = args[1].replaceAll("\"", "");
                if (args[1].contains("@")) {
                    displayTypeEntries(args[1].substring(1).trim());
                } else {
                    displayAuthorEntries(args[1]);
                }
            } else {
                args[1] = args[1].replaceAll("\"", "");
                args[2] = args[2].replaceAll("\"", "");
                displayAuthorTypeEntries(args[1].substring(1).trim(), args[2]);
            }
        }
    }

    private void parse(String filePath) throws FileNotFoundException {
        Parser parser = new Parser();
        this.entries = parser.parseBib(filePath);
    }

    private void displayAllEntries() {
        System.out.println("Displaying all entries:\n");
        for (HashMap.Entry<String, Entry> tmp : entries.entrySet()) {
            System.out.println(tmp.getValue());
        }
    }

    private void displayTypeEntries(String entryType) {
        System.out.println("Displaying every " + entryType + " entries:\n");
        for (HashMap.Entry<String, Entry> tmp : entries.entrySet()) {
            if (tmp.getValue().getClass().getSimpleName().equalsIgnoreCase(entryType)) {
                System.out.println(tmp.getValue());
            }
        }
    }

    private void displayAuthorEntries(String author) {
        System.out.println("Displaying every entry containing " + author + " as author or editor:");
        for (HashMap.Entry<String, Entry> tmp : entries.entrySet()) {
            if (tmp.getValue().containsAuthor(author.replaceAll("\"", ""))) {
                System.out.println(tmp.getValue());
            }
        }
    }

    private void displayAuthorTypeEntries(String entryType, String author) {
        System.out.println("Displaying every " + entryType + " entries, containing " + author + " as author or editor:");
        for (HashMap.Entry<String, Entry> tmp : entries.entrySet()) {
            if (tmp.getValue().getClass().getSimpleName().equalsIgnoreCase(entryType) &&
                    tmp.getValue().containsAuthor(author)) {
                System.out.println(tmp.getValue());
            }
        }
    }

}
