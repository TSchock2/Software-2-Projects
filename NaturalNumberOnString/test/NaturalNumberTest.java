import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Tyler Schock and Bowen Liu
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    // TODO - add test cases for four constructors, multiplyBy10, divideBy10, isZero

    /*
     * Test cases for Constructors
     */

    /**
     * Test case for No Argument Constructor.
     */
    @Test
    public void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * Test case for Integer No Argument Constructor.
     */
    @Test
    public void testIntConstructorNoArgument() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest();
        int nExpected = n.toInt();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(0, nExpected);
    }

    /**
     * Test case for Single Digit Integer Constructor.
     */
    @Test
    public void testIntConstructorArgumentSingleDigit() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(2);
        int nExpected = n.toInt();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(2, nExpected);
    }

    /**
     * Test case for Double Digit Integer Constructor.
     */
    @Test
    public void testIntConstructorArgumentDoubleDigits() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(22);
        int nExpected = n.toInt();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(22, nExpected);
    }

    /**
     * Test case for Triple Digit Integer Constructor.
     */
    @Test
    public void testIntConstructorArgumentTripleDigits() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(222);
        int nExpected = n.toInt();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(222, nExpected);
    }

    /**
     * Test case for Empty String Constructor.
     */
    @Test
    public void testConstructorWithEmptyStringArgument() {
        /*
         * Set up variables and call method under test
         */

        NaturalNumber n = this.constructorTest();
        String nExpected = n.toString();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("0", nExpected);
    }

    /**
     * Test case for "0" Argument String Constructor.
     */
    @Test
    public void testConstructorWithZeroStringArgument() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("0");
        String nExpected = n.toString();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("0", nExpected);
    }

    /**
     * Test case for Single Digit String Constructor.
     */
    @Test
    public void testConstructorStringArgumentSingleDigit() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("6");
        String nExpected = n.toString();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("6", nExpected);
    }

    /**
     * Test case for Double Digit String Constructor.
     */
    @Test
    public void testConstructorStringArgumentDoubleDigits() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("45");
        String nExpected = n.toString();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("45", nExpected);
    }

    /**
     * Test case for Triple Digit String Constructor.
     */
    @Test
    public void testConstructorStringArgumentTripleDigits() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("999");
        String nExpected = n.toString();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("999", nExpected);
    }

    /**
     * Test case for No Argument NaturalNumber Constructor.
     */
    @Test
    public void testConstructorWithEmptyNNArgument() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef(n);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(0, nExpected.toInt());
    }

    /**
     * Test case for 0 Argument NaturalNumber Constructor.
     */
    @Test
    public void testConstructorWithZeroNNArgument() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("0");
        NaturalNumber nExpected = this.constructorRef(n);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(0, nExpected.toInt());
    }

    /**
     * Test case for Single Digit NaturalNumber Constructor.
     */
    @Test
    public void testConstructorNNArgumentSingleDigit() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("9");
        NaturalNumber nExpected = this.constructorRef(n);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(9, nExpected.toInt());
    }

    /**
     * Test case for Double Digit NaturalNumber Constructor.
     */
    @Test
    public void testConstructorNNArgumentDoubleDigits() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("72");
        NaturalNumber nExpected = this.constructorRef(n);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(72, nExpected.toInt());
    }

    /**
     * Test case for Triple Digit NaturalNumber Constructor.
     */
    @Test
    public void testConstructorNNArgumentTripleDigits() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("500");
        NaturalNumber nExpected = this.constructorRef(n);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(500, nExpected.toInt());
    }

    /**
     * Test case for MultiplyBy10 on 0 by 0.
     */
    @Test
    public void testMultitplyBy10on0() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();
        /*
         * Call Method under test
         */
        n.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * Test case for MultiplyBy10 on 0 to make Single Digit [1-9].
     */
    @Test
    public void testMultitplyBy10SingleDigit() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef(5);
        /*
         * Call Method under test
         */
        n.multiplyBy10(5);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * Test case for MultiplyBy10 on Single Digit.
     */
    @Test
    public void testMultitplyBy10onSingleDigit() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(1);
        NaturalNumber nExpected = this.constructorRef(10);
        /*
         * Call Method under test
         */
        n.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * Test case for MultiplyBy10 on Double Digit.
     */
    @Test
    public void testMultitplyBy10onDoubleDigit() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(15);
        NaturalNumber nExpected = this.constructorRef(157);
        /*
         * Call Method under test
         */
        n.multiplyBy10(7);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * Test case for MultiplyBy10 on Triple Digit.
     */
    @Test
    public void testMultitplyBy10onTripleDigit() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(100);
        NaturalNumber nExpected = this.constructorRef(1001);
        /*
         * Call Method under test
         */
        n.multiplyBy10(1);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * Test case for DivideBy10 on 0.
     */
    @Test
    public void testDivBy10on0() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();
        /*
         * Call Method under test
         */
        int remainder = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(0, remainder);
    }

    /**
     * Test case for DivideBy10 on Single Digit.
     */
    @Test
    public void testDivBy10onSingleDigit() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(2);
        NaturalNumber nExpected = this.constructorRef();
        /*
         * Call Method under test
         */
        int remainder = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(2, remainder);
    }

    /**
     * Test case for DivideBy10 on Double Digit.
     */
    @Test
    public void testDivBy10onDoubleDigit() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(25);
        NaturalNumber nExpected = this.constructorRef(2);
        /*
         * Call Method under test
         */
        int remainder = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(5, remainder);
    }

    /**
     * Test case for DivideBy10 on triple digit.
     */
    @Test
    public void testDivBy10onTripleDigit() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(256);
        NaturalNumber nExpected = this.constructorRef(25);
        /*
         * Call Method under test
         */
        int remainder = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(6, remainder);
    }

    /**
     * Test for IsZero on No Argument Constructor.
     */
    public void testIsZeroNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest();
        /*
         * Call Method under test
         */
        boolean nExpected = n.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, nExpected);

    }

    /**
     * Test for IsZero on Integer Argument Constructor.
     */
    public void testIsZeroIntArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(0);
        /*
         * Call Method under test
         */
        boolean nExpected = n.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, nExpected);

    }

    /**
     * Test for False IsZero on Integer Argument Constructor.
     */
    public void testFalseIsZeroIntArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(8);
        /*
         * Call Method under test
         */
        boolean nExpected = n.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, nExpected);

    }

    /**
     * Test for IsZero on String Argument Constructor.
     */
    public void testIsZeroStringArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("");
        /*
         * Call Method under test
         */
        boolean nExpected = n.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, nExpected);

    }

    /**
     * Test for False IsZero on String Argument Constructor.
     */
    public void testFalseIsZeroStringArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("9");
        /*
         * Call Method under test
         */
        boolean nExpected = n.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, nExpected);

    }

    /**
     * Test for IsZero on NaturalNumber Argument Constructor.
     */
    public void testIsZeroNNArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest();
        /*
         * Call Method under test
         */
        boolean nExpected = n.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, nExpected);

    }

    /**
     * Test for False IsZero on NaturalNumber Argument Constructor.
     */
    public void testFalseIsZeroNNArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(3);
        /*
         * Call Method under test
         */
        boolean nExpected = n.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, nExpected);

    }
}
