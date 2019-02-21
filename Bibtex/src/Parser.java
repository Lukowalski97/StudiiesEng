import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * This class is the core of BibTex parser program,
 * it parses simplified BibTex file with .bib extension to
 * ArrayList containing entries made with ASCII code.
 *
 * @author lukow
 * @version %I% , %G%
 * @since 1.1
 */
public class Parser {

    private static final Pattern metaPattern = Pattern.compile("\\s*@[a-zA-Z]+\\s*\\{\\s*[a-zA-Z\\-0-9]+\\s*,");
    private static final Pattern linePattern = Pattern.compile("\\s*[a-zA-Z]+\\s*=\\s*\"[^\",}{=]+\"\\s*,");
    private static final Pattern linePatternConcat = Pattern.compile("\\s*[a-zA-Z]+\\s*=\\s*\"[^\",}{=]+\"\\s*#\\s*\"[^=\",}{]+\"\\s*,");
    private static final String metaSplit = "\\{";
    private static final String fieldSplit = "=";
    private static final Pattern preamblePattern = Pattern.compile("(?i)\\s*@preamble.*");
    private static final Pattern stringPattern = Pattern.compile("(?i)\\s*@string\\s*[{(]\\s*[a-zA-Z]+\\s*=\\s*\"[^,\"{}=]+\"\\s*[})]\\s*");
    //private static final Pattern stringinLinePattern=Pattern.compile("\\w+");
    // private static final String sthInquotes = "\"[^,={}|]*\"";
    private static final Pattern endLinePattern = Pattern.compile("}");
    private ArrayList<StringPair> foundStrings;


    // private String file;
    //public Parser(String file ){
    //    this.file=file;
    //}
    // -- powyzsze pola i konstruktor w razie gdyby sie przydaly pozniej

    /**
     * This method makes HashMap of  {@link Entry} class with Entry as value and Entry's key as Map's key
     * which contains all correctly parsed entries from .bib file
     *
     * @param bibFile .bib filename path
     * @return HashMap filled with Entry objects
     * @throws FileNotFoundException if bibFile name is not found
     */
    public HashMap<String, Entry> parseBib(String bibFile) throws FileNotFoundException {
        foundStrings = new ArrayList<>();
        boolean inEntry = false;
        HashMap<String, Entry> outp = new HashMap<>();
        Entry tmp = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(bibFile)));

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (inEntry) {
                    if (isStringinLine(line)) {
                        line = replaceStrings(line);
                    }
                    if (isCorrectLine(line)) {
                        tmp.addField(splitField(line));
                    } else if (isCorrectLineConcat(line)) {
                        tmp.addFieldConcat(splitField(line));
                    } else if (foundEntryEnd(line)) {
                        if (!tmp.hasRequiredFields()) {
                            throw new RuntimeException("Entry " + tmp.key + " doesn't have required fields");

                        } else {
                            outp.put(tmp.key, tmp);
                            inEntry = false;
                        }
                    }
                } else {
                    if (foundString(line)) {
                        addString(line);
                    } else if (foundNewEntry(line)) {
                        StringPair meta = getMeta(line);
                        inEntry = true;
                        try {
                            tmp = createEntry(meta);
                        } catch (Exception ex) {
                            inEntry = false;
                            tmp = null;
                        }

                    }
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File " + bibFile + " not found ");
            e.printStackTrace();
            throw new FileNotFoundException();
        } catch (IOException e) {
            System.out.println("Input error!");
            e.printStackTrace();
        }

        return outp;
    }

    /*
     * private boolean endingLine(String line){
     * if(endLine.matcher(line).matches() )
     * return true;
     * return false;
     * }
     * in case I should check the end of Entry with '}'
     */


    //check whether line is matching << field= "value", >> type
    private boolean isCorrectLine(String line) {
        if (linePattern.matcher(line).matches())
            return true;
        return false;
    }

    //Checks whether line is matching <<field= "value" # "value", >> type
    private boolean isCorrectLineConcat(String line) {
        if (linePatternConcat.matcher(line).matches())
            return true;
        return false;
    }

    //Checks whether line is matching "@Type{key," type
    private boolean foundNewEntry(String line) {
        if (!preamblePattern.matcher(line).matches() && metaPattern.matcher(line).matches())
            return true;

        return false;
    }

    // checks outside of Entries if in this line is declared @String
    private boolean foundString(String line) {
        if (stringPattern.matcher(line).matches())
            return true;
        return false;
    }

    private boolean foundEntryEnd(String line) {
        if (endLinePattern.matcher(line).find())
            return true;
        return false;
    }

    // checks whether in line of Entry may be @Strings
    private boolean isStringinLine(String line) {
        return (!(isCorrectLineConcat(line) || isCorrectLine(line)));
    }

    //replaces Strings in line from their name without quotes to from with quotes such as "string"
    private String replaceStrings(String line) {
        for (StringPair tmp : foundStrings) {
            line = line.replaceAll(" " + tmp.getName() + " ", tmp.getValue());
            line = line.replaceAll(" " + tmp.getName() + ",", tmp.getValue() + ",");
            line = line.replaceAll(" " + tmp.getName() + "#", tmp.getValue() + "#");
            line = line.replaceAll("#" + tmp.getName() + " ", "#" + tmp.getValue());
            line = line.replaceAll("#" + tmp.getName() + "#", "#" + tmp.getValue() + "#");
            line = line.replaceAll("=" + tmp.getName() + " ", "=" + tmp.getValue());
        }
        return line;
    }

    //adds new PairString to list of @Strings
    private void addString(String line) {
        String[] tmp = line.split("=");
        String[] tmp2 = tmp[0].split("[{(]");
        tmp[0] = tmp2[1].trim();
        tmp[1] = tmp[1].substring(0, tmp[1].trim().length() - 1).trim();
        if (tmp[1].matches("\"[\\w\\s]*")) {
            tmp[1] = tmp[1] + "\"";
        }
        if (tmp[1].matches("[\\w\\s]*\"")) {
            tmp[1] = "\"" + tmp[1];
        }
        foundStrings.add(new StringPair((tmp[0]), tmp[1]));
    }

    //splits line getting Entry's first line @type{key,
    private StringPair getMeta(String line) {
        String[] tmp = line.split(metaSplit);
        tmp[0] = tmp[0].replaceAll("[@]", "").trim().toUpperCase();
        tmp[1] = tmp[1].replaceAll("[,]", "").trim();
        return new StringPair(tmp[0], tmp[1]);
    }

    private Entry createEntry(StringPair meta) throws ClassNotFoundException,
            NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        if (meta.getName().equalsIgnoreCase("inproceedings")) {
            meta.setName("CONFERENCE");
        }
        Class outpClass = Class.forName(meta.getName());
        Constructor constructor = outpClass.getConstructor(String.class);

        Entry outp = (Entry) constructor.newInstance(meta.getValue());

        return outp;
    }

    //splits line getting field's type and value, not formatted
    private StringPair splitField(String line) {
        String[] tmp = line.split(fieldSplit);

        return new StringPair(tmp[0], tmp[1]);
    }

}
