import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Creates a table with pairs of words and the number of times each word has
 * appeared in a given file.
 *
 * @author Tyler Schock.27
 *
 */
public final class WordCounter {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private WordCounter() {
    }

    /**
     * Create the output header for HTML page created from file.
     *
     * @param out
     *            output to HTML page
     * @param text
     *            name of file for header
     * @param words
     *            words in alphabetical order for table
     * @param wordCount
     *            number of times each word appeared in given file
     */
    private static void createHeader(SimpleWriter out, String text, Queue<String> words,
            Map<String, Integer> wordCount) {
        out.println("<html>");
        out.println("<style>");
        out.println("table, th, td { border:1px solid black; }");
        out.println("</style>");
        out.println("<body>");

        out.println("<h2>Words counted in " + text + "</h2>");
        out.println("<table style=\"width:50%\">");
        out.println("<tr>");
        out.println("<th>Words</th>");
        out.println("<th>Count</th>");
        out.println("</tr>");

        int i = 0;
        int size = words.length();
        while (i < size) {
            int count = wordCount.value(words.front());
            out.println("<tr>");
            out.println("<td>" + words.dequeue() + "</td>");
            out.println("<td>" + count + "</td>");
            out.println("</tr>");
            i++;
        }

        // Call to close HTML tags
        createFooter(out);
    }

    /**
     * Create output footer for HTML page.
     *
     * @param out
     *            output to HTML page
     */
    private static void createFooter(SimpleWriter out) {

        // Closing Statements for HTML page
        out.println("</table>");
        out.println("</body>");
        out.println("</HTML>");
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

        // Loop to add separators from string into set
        sep.clear();
        for (int i = 0; i < sepratrs.length(); i++) {
            if (!sep.contains(sepratrs.charAt(i))) {
                sep.add(sepratrs.charAt(i));
            }
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
     * @return wordsToSort queue of words to sort in alphabetical order
     */
    private static Queue<String> gatherWordsNCounts(SimpleReader inputFile,
            Set<Character> separators, Map<String, Integer> wordsNCount) {
        // Initialize Queue to sort words later
        Queue<String> wordsToSort = new Queue1L<String>();
        // Initialize position in string
        int position = 0;
        while (!inputFile.atEOS()) {

            // Gather next line in text file and set position back to 0 for new string
            String text = inputFile.nextLine();
            position = 0;

            /*
             * Loop through string until first word appears before white space
             * or separator character
             */
            while (position < text.length()) {
                String word = nextWordOrSeparator(text, position, separators);

                /*
                 * If a separator is not shown in the first character, a word is
                 * there to be put into a Map pair. If the word hasn't been
                 * seen, enter first if statement to add to wordsNCount. Else,
                 * the word has been seen and the value for how many times the
                 * word has appeared increases by one.
                 */
                if (!separators.contains(word.charAt(0))) {
                    if (!wordsNCount.hasKey(word)) {
                        wordsNCount.add(word, 1);
                        wordsToSort.enqueue(word);
                    } else {
                        int val = wordsNCount.value(word);
                        val++;
                        wordsNCount.replaceValue(word, val);
                    }
                }
                // Update position by length of word/character to loop new characters
                position += word.length();
            }
        }

        // Return the Queue of words to alphabetize
        return wordsToSort;
    }

    /**
     * Alphabetize words for table.
     *
     * @param sorted
     *            Queue of words in alphabetical order
     */
    private static void alphabetize(Queue<String> sorted) {
        String[] sort = new String[sorted.length()];

        // Add words to array to sort
        for (int i = 0; i < sort.length; i++) {
            sort[i] = sorted.dequeue();
        }

        // Used Bubble sort (Didn't know if we could use comparators fully or not)
        for (int i = 0; i < sort.length - 1; i++) {
            for (int j = 0; j < sort.length - i - 1; j++) {
                if (sort[j].compareTo(sort[j + 1]) > 0) {
                    String temp = sort[j];
                    sort[j] = sort[j + 1];
                    sort[j + 1] = temp;
                }
            }
        }

        // Add sorted words from array back to Queue
        for (int i = 0; i < sort.length; i++) {
            sorted.enqueue(sort[i]);
        }

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        Set<Character> separators = new Set1L<Character>();
        Queue<String> words = new Queue1L<String>();
        Map<String, Integer> wordsNCounts = new Map1L<String, Integer>();

        out.println("Enter the name of an intput file:");
        String input = in.nextLine();
        SimpleReader inFile = new SimpleReader1L(input);

        out.println("Enter the name of an output file:");
        String output = in.nextLine();
        SimpleWriter outHTML = new SimpleWriter1L(output);

        // Create string of separators to put in set
        String separatorStr = " \t\n\r,-.!?[]';:/()`";

        // Create separator Set
        createSeparators(separators, separatorStr);

        // Gather words from file to create words and their counts
        words = gatherWordsNCounts(inFile, separators, wordsNCounts);
        // Put words in alphabetical order
        alphabetize(words);
        // Display on HTML page
        createHeader(outHTML, input, words, wordsNCounts);

        in.close();
        out.close();
        inFile.close();
        outHTML.close();
    }

}
