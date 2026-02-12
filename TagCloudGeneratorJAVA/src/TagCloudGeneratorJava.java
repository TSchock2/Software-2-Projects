import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Takes a .txt file and generates the number of words the user wants in
 * alphabetical order and displays the words in a font sized based on the number
 * of times the word has been seen.
 *
 * @author Tyler Schock
 *
 */
public final class TagCloudGeneratorJava {

    /**
     * Minimum font size.
     */
    private static final int MIN_FONT = 11;

    /**
     * Maximum font size.
     */
    private static final int MAX_FONT = 48;

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TagCloudGeneratorJava() {
    }

    /**
     * Compare {@code Map.Entry}s in Decreasing order of word count.
     */
    private static class DecreasingOrder
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            return o2.getValue().compareTo(o1.getValue());
        }
    }

    /**
     * Compare {@code Map.Entry}s in Alphabetical order.
     */
    private static class AlphabeticalOrder
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            return o1.getKey().compareToIgnoreCase(o2.getKey());
        }
    }

    /**
     *
     * @param minCount
     *            The minimum number of times a word is seen
     * @param maxCount
     *            The maximum number of times a word is seen
     * @param numCount
     *            Number of times this word has been seen
     * @return Returns the font value with "f" prefixed for convenience
     */
    private static String sizeOfFont(int minCount, int maxCount, int numCount) {

        int font = 0;

        if (maxCount != minCount) {
            font = MIN_FONT + (numCount - minCount) * (MAX_FONT - MIN_FONT)
                    / (maxCount - minCount);
        } else {
            font = MIN_FONT + (numCount - minCount) * (MAX_FONT - MIN_FONT) / 5;
        }

        return "f" + font;
    }

    /**
     * Create the output header for HTML page created from file.
     *
     * @param out
     *            output to HTML page
     * @param inputFile
     *            name of file for header
     * @param numWords
     *            number of words to output
     */
    private static void createHeader(int numWords, String inputFile, PrintWriter out) {
        assert out != null : "Violation of: out is not null";

        // Start of header
        out.println("<html>");
        out.println("<head>");

        out.println("<title> Top " + numWords + " words in " + inputFile + "</title>");

        // Links for Tag Cloud CSS
        out.println("<link href=\"https://cse22x1.engineering.osu.edu/2231/web-sw2/"
                + "assignments/projects/tag-cloud-generator/"
                + "data/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println("<link href=\"tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");

        // Close head
        out.println("</head>");

        // Open Body
        out.println("<body>");
        out.println("<h2>" + "Top " + numWords + " words in " + inputFile + "</h2>");
        out.println("<hr>");
        out.println("<div class=\"cdiv\">");
        out.println("<p class=\"cbox\">");

    }

    /**
     * Create output footer for HTML page.
     *
     * @param out
     *            output to HTML page
     */
    private static void createFooter(PrintWriter out) {
        assert out != null : "Violation of: out is not null";

        // Closing Statements for HTML page
        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Sort words into decreasing order from word count as well alphabetical
     * order.
     *
     * @param numWords
     *            number of words to output
     * @param wordsNCounts
     *            words to output and their counts
     * @param outHTML
     *            output to HTML page
     *
     */
    private static void sortWords(int numWords, Map<String, Integer> wordsNCounts,
            PrintWriter outHTML) {

        // Create Comparators decreasing word count and alphabetizing words
        Comparator<Map.Entry<String, Integer>> decrease = new DecreasingOrder();
        Comparator<Map.Entry<String, Integer>> alphabetize = new AlphabeticalOrder();

        //Create list arrays to iterate over
        List<Map.Entry<String, Integer>> decCount = new ArrayList<>();
        List<Map.Entry<String, Integer>> alphabetical = new ArrayList<>();

        // For all entries in map, add to decCount list to sort words in decreasing order
        for (Map.Entry<String, Integer> entry : wordsNCounts.entrySet()) {
            decCount.add(entry);
        }
        // Sort
        decCount.sort(decrease);

        /*
         * Once sorted based on word count, alphabetize numWords to output. If
         * numWords is less than decCount size, get the number of words equal to
         * numWords. Else, get the size of the decCount.
         */
        if (numWords < decCount.size()) {
            int i = 0;
            while (i < numWords) {
                alphabetical.add(decCount.remove(0));
                i++;
            }
        } else {
            while (decCount.size() > 0) {
                alphabetical.add(decCount.remove(0));
            }
        }
        // Sort
        alphabetical.sort(alphabetize);

        // Find min and max word counts for font sizing
        int min = Integer.MAX_VALUE;
        int max = 0;
        for (Map.Entry<String, Integer> entry : alphabetical) {
            if (entry.getValue() > max) {
                max = entry.getValue();
            }
            if (entry.getValue() < min) {
                min = entry.getValue();
            }
        }

        // Get alphabetized pairs and change the size of their font based on word count
        for (Map.Entry<String, Integer> entry : alphabetical) {
            String fontSize = sizeOfFont(min, max, entry.getValue());
            outHTML.println("<span style=\"cursor:default\" class=\"" + fontSize
                    + "\" title=\"count:" + entry.getValue() + "\">" + entry.getKey()
                    + "</span>");
        }

    }

    /**
     * Create pairs of words and their counts.
     *
     * @param inputFile
     *            output to HTML page
     * @param separators
     *            set of characters that are known separators
     * @param wordsNCount
     *            pairs of words and the number of times they have appeared
     */
    private static void gatherWordsNCounts(BufferedReader inputFile,
            Set<Character> separators, Map<String, Integer> wordsNCount) {

        // Initialize position in string
        int position = 0;

        try {
            // Gather next line in text file and set position back to 0 for new string
            String text = inputFile.readLine();
            while (text != null) {
                position = 0;

                /*
                 * Loop through string until first word appears before white
                 * space or separator character
                 */
                while (position < text.length()) {
                    String word = nextWordOrSeparator(text, position, separators);

                    /*
                     * If a separator is not shown in the first character, a
                     * word is there to be put into a Map Entry. If the word
                     * hasn't been seen, enter first if statement to add to
                     * wordsNCount. Else, the word has been seen and the value
                     * for how many times the word has appeared increases by
                     * one.
                     */
                    if (!separators.contains(word.charAt(0))) {
                        if (!wordsNCount.containsKey(word)) {
                            wordsNCount.put(word, 1);
                        } else {
                            int count = wordsNCount.get(word);
                            count++;
                            wordsNCount.replace(word, count);
                        }
                    }
                    // Update position by length of word/character to loop new characters
                    position += word.length();
                }
                text = inputFile.readLine();
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file");
            return;
        }

    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        // Will fix this function later - too repetitive and can be simplified
        String str = "";
        if (separators.contains(text.charAt(position))) {
            for (int i = 0; i < text.substring(position, text.length()).length(); i++) {
                char strPart = text.charAt(position + i);
                if (separators.contains(text.charAt(position + i))) {
                    str = str + strPart;
                } else {
                    i = text.substring(position, text.length()).length();
                }
            }
        } else {
            for (int i = 0; i < text.substring(position, text.length()).length(); i++) {
                char strPart = text.charAt(position + i);
                if (!separators.contains(text.charAt(position + i))) {
                    str = str + strPart;
                } else {
                    i = text.substring(position, text.length()).length();
                }
            }
        }

        return str.toLowerCase();
    }

    /**
     * Creates a Set of characters of separators from string.
     *
     * @param sep
     *            Set containing separators
     *
     * @param sepratrs
     *            String containing characters of separators to add to Set
     *
     */
    private static void createSeparators(Set<Character> sep, String sepratrs) {
        assert sepratrs != null : "Violation of: sepratrs is not null";
        assert sep != null : "Violation of: sep is not null";

        // Loop to add separators from string into set
        sep.clear();
        for (int i = 0; i < sepratrs.length(); i++) {
            if (!sep.contains(sepratrs.charAt(i))) {
                sep.add(sepratrs.charAt(i));
            }
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        BufferedReader input = null;
        PrintWriter output = null;

        // Ask user for input, output files and the number of words to output
        System.out.println("Enter the pathname of the input file: ");
        String inputFile = in.nextLine();

        System.out.println("Enter the pathname of the output file: ");
        String outFile = in.nextLine();

        try {
            input = new BufferedReader(new FileReader(inputFile));
            output = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
        } catch (IOException e) {
            System.out.println("Unable to read files");
            in.close();
            return;
        }

        System.out.println("Enter the number of words to generate: ");
        int numWords = in.nextInt();
        while (numWords < 0) {
            System.out.println("Please Enter a positive number: ");
            numWords = in.nextInt();
        }

        // Initialize separator str and set
        final String separatorStr = " \t\n\r,-.!?[]';:/()`";
        Set<Character> separatorSet = new HashSet<>();

        // Create separator set
        createSeparators(separatorSet, separatorStr);

        // Initialize Map
        Map<String, Integer> wordsNCounts = new HashMap<>();

        // Create Map of words and their counts
        gatherWordsNCounts(input, separatorSet, wordsNCounts);

        // Create Header for HTML page
        createHeader(numWords, inputFile, output);

        /*
         * Sort words in decreasing order of word count and alphabetical order
         * as well as output them to HTML page
         */
        sortWords(numWords, wordsNCounts, output);

        // Call to close HTML tags
        createFooter(output);

        try {
            in.close();
            input.close();
            output.close();
        } catch (IOException e) {
            System.out.println("Unable to close files");
        }
    }

}
