import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumberSecondary;

/**
 * {@code NaturalNumber} represented as a {@code String} with implementations of
 * primary methods.
 *
 * @convention <pre>
 * [all characters of $this.rep are '0' through '9']  and
 * [$this.rep does not start with '0']
 * </pre>
 * @correspondence <pre>
 * this = [if $this.rep = "" then 0
 *         else the decimal number whose ordinary depiction is $this.rep]
 * </pre>
 *
 * @author Tyler Schock and Bowen Liu
 *
 */
public class NaturalNumber3 extends NaturalNumberSecondary {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Representation of {@code this}.
     */
    private String rep;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {

        this.rep = "";

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public NaturalNumber3() {

        this.createNewRep();

    }

    /**
     * Constructor from {@code int}.
     *
     * @param i
     *            {@code int} to initialize from
     */
    public NaturalNumber3(int i) {
        assert i >= 0 : "Violation of: i >= 0";

        // If i is 0, createNewRep or empty String
        if (i == 0) {
            this.createNewRep();
        } else {
            // i greater than 0, convert to string and add to this.rep
            this.rep = Integer.toString(i);
        }
    }

    /**
     * Constructor from {@code String}.
     *
     * @param s
     *            {@code String} to initialize from
     */
    public NaturalNumber3(String s) {
        assert s != null : "Violation of: s is not null";
        assert s.matches("0|[1-9]\\d*")
                : "" + "Violation of: there exists n: NATURAL (s = TO_STRING(n))";

        // If s is "0" or empty, createNewRep or empty String to make the number 0
        if (s.equals("0")) {
            this.createNewRep();
        } else {
            // If s is not empty, this.rep is assigned what is in s
            this.rep = s;
        }

    }

    /**
     * Constructor from {@code NaturalNumber}.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     */
    public NaturalNumber3(NaturalNumber n) {
        assert n != null : "Violation of: n is not null";

        // If n is not zero, convert the number to a string and assign to this.rep
        if (!n.isZero()) {
            this.rep = n.toString();
        } else {
            // If n is zero, createNewRep or empty String making the number 0
            this.createNewRep();
        }
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @Override
    public final NaturalNumber newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(NaturalNumber source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof NaturalNumber3
                : "" + "Violation of: source is of dynamic type NaturalNumberExample";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case.
         */
        NaturalNumber3 localSource = (NaturalNumber3) source;
        this.rep = localSource.rep;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void multiplyBy10(int k) {
        assert 0 <= k : "Violation of: 0 <= k";
        assert k < RADIX : "Violation of: k < 10";

        /*
         * If this.rep is empty and integer k is 0, createNewRep or empty String
         * for number 0
         */
        if (this.rep.isEmpty() && k == 0) {
            this.createNewRep();
        } else {
            /*
             * this.rep is empty or integer k is greater than 0 and less than
             * 10, convert k to a String and add to the end of this.rep
             */
            this.rep = this.rep.concat(Integer.toString(k));
        }
    }

    @Override
    public final int divideBy10() {

        // Set initial value to 0 in case of number being equal to 0
        int div = 0;

        /*
         * If this.rep is not empty, remove the last digit as a substring and
         * convert it to an integer to return for divideBy10. Then, update
         * this.rep to be the substring that leaves the last digit off.
         */
        if (!this.rep.isEmpty()) {
            String lastDigit = this.rep.substring(this.rep.length() - 1);
            div = Integer.parseInt(lastDigit);
            this.rep = this.rep.substring(0, this.rep.length() - 1);
        }
        return div;
    }

    @Override
    public final boolean isZero() {

        // If this.rep is empty or "0", returns true, so the number is 0, false otherwise
        return this.rep.isEmpty();
    }

}
