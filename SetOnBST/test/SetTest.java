import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Tyler Schock and Bowen Liu
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size

    /**
     * Test for constructor.
     */
    @Test
    public void testConstructor() {
        /*
         * Set up variables
         */
        Set<String> s = this.constructorTest();
        Set<String> sExpected = this.constructorRef();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(s, sExpected);
    }

    /**
     * Test for Add to empty set.
     */
    @Test
    public void testAddEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef("2");
        /*
         * Call method under test
         */
        s.add("2");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(s, sExpected);
    }

    /**
     * Test for Add to non empty set.
     */
    @Test
    public void testAddNonEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("1", "2", "3");
        Set<String> sExpected = this.createFromArgsRef("1", "2", "3", "4");
        /*
         * Call method under test
         */
        s.add("4");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(s, sExpected);
    }

    /**
     * Test for Add to multiple non empty set.
     */
    @Test
    public void testAddMultipleNonEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("1", "2");
        Set<String> sExpected = this.createFromArgsRef("1", "2", "3", "4", "5");
        /*
         * Call method under test
         */
        s.add("3");
        s.add("4");
        s.add("5");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(s, sExpected);
    }

    /**
     * Test for Remove to make empty.
     */
    @Test
    public void testRemoveToEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("1");
        Set<String> sExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        s.remove("1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(s, sExpected);
    }

    /**
     * Test for Remove from non empty set.
     */
    @Test
    public void testRemoveNonEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("1", "2", "3", "4", "5");
        Set<String> sExpected = this.createFromArgsRef("1", "2", "4", "5");
        /*
         * Call method under test
         */
        s.remove("3");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(s, sExpected);
    }

    /**
     * Test for Remove multiple.
     */
    @Test
    public void testRemoveMultiple() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("1", "2", "3", "4", "5");
        Set<String> sExpected = this.createFromArgsRef("1", "2");
        /*
         * Call method under test
         */
        s.remove("3");
        s.remove("4");
        s.remove("5");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(s, sExpected);
    }

    /**
     * Test for Remove multiple.
     */
    @Test
    public void testRemoveAll() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("1", "2", "3");
        Set<String> sExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        s.remove("1");
        s.remove("2");
        s.remove("3");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(s, sExpected);
    }

    /**
     * Test for RemoveAny size one to make empty.
     */
    @Test
    public void testRemoveAnySizeOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("1");
        Set<String> sExpected = this.createFromArgsRef("1");
        /*
         * Call method under test
         */
        String removed = s.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, sExpected.contains(removed));
        sExpected.remove(removed);
        assertEquals(sExpected, s);
    }

    /**
     * Test for RemoveAny to make size one.
     */
    @Test
    public void testRemoveAnyMakeSizeOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("1", "2");
        Set<String> sExpected = this.createFromArgsRef("1", "2");
        /*
         * Call method under test
         */
        String removed = s.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, sExpected.contains(removed));
        sExpected.remove(removed);
        assertEquals(sExpected, s);
    }

    /**
     * Test for RemoveAny for size greater than one.
     */
    @Test
    public void testRemoveAnySizeGreaterThanOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("1", "2", "3", "4", "5");
        Set<String> sExpected = this.createFromArgsRef("1", "2", "3", "4", "5");
        /*
         * Call method under test
         */
        String removed = s.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, sExpected.contains(removed));
        sExpected.remove(removed);
        assertEquals(sExpected, s);
    }

    /**
     * Test for Contains for empty set.
     */
    @Test
    public void testContainsEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest();
        String sExpected = "2";
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, s.contains(sExpected));
    }

    /**
     * Test for Contains non empty set false.
     */
    @Test
    public void testContainsNonEmptyFalse() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("1", "3", "4", "6");
        String sExpected = "5";
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, s.contains(sExpected));
    }

    /**
     * Test for Contains true for size one.
     */
    @Test
    public void testContainsTrueSizeOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("6");
        String sExpected = "6";
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, s.contains(sExpected));
    }

    /**
     * Test for Contains true for set size greater than one.
     */
    @Test
    public void testContainsTrueSizeGreaterThanOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("4", "5", "6");
        String sExpected = "4";
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, s.contains(sExpected));
    }

    /**
     * Test for Size, size one.
     */
    @Test
    public void testSizeOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("1");
        int sExpected = 1;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s.size());
    }

    /**
     * Test for Size, size greater than one.
     */
    @Test
    public void testSizeGreaterThanOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("1", "2", "3", "4");
        int sExpected = 4;
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s.size());
    }
}
