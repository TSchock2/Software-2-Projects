import java.util.Iterator;

import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;
import components.set.Set;
import components.set.SetSecondary;

/**
 * {@code Set} represented as a {@code BinaryTree} (maintained as a binary
 * search tree) of elements with implementations of primary methods.
 *
 * @param <T>
 *            type of {@code Set} elements
 * @mathdefinitions <pre>
 * IS_BST(
 *   tree: binary tree of T
 *  ): boolean satisfies
 *  [tree satisfies the binary search tree properties as described in the
 *   slides with the ordering reported by compareTo for T, including that
 *   it has no duplicate labels]
 * </pre>
 * @convention IS_BST($this.tree)
 * @correspondence this = labels($this.tree)
 *
 * @author Tyler Schock and Bowen Liu
 *
 */
public class Set3a<T extends Comparable<T>> extends SetSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Elements included in {@code this}.
     */
    private BinaryTree<T> tree;

    /**
     * Returns whether {@code x} is in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be searched for
     * @return true if t contains x, false otherwise
     * @requires IS_BST(t)
     * @ensures isInTree = (x is in labels(t))
     */
    private static <T extends Comparable<T>> boolean isInTree(BinaryTree<T> t, T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        // Initalize values
        boolean isIn = false;
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();

        // If tree size is greater than zero, go to if statement
        if (t.size() > 0) {
            // Disassemble tree into root, left, and right
            T root = t.disassemble(left, right);

            // Compare root to x, if equal (0), x is in tree
            if (x.compareTo(root) == 0) {
                isIn = true;
                /*
                 * If x is less than root, call isInTree to see if x is in the
                 * left tree
                 */
            } else if (x.compareTo(root) < 0) {
                isIn = isInTree(left, x);
                /*
                 * Else x is greater than root, call isInTree to see if x is in
                 * right tree
                 */
            } else {
                isIn = isInTree(right, x);
            }

            // Reassemble tree
            t.assemble(root, left, right);
        }

        // Return true or false if x is in the tree
        return isIn;
    }

    /**
     * Inserts {@code x} in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be inserted
     * @aliases reference {@code x}
     * @updates t
     * @requires IS_BST(t) and x is not in labels(t)
     * @ensures IS_BST(t) and labels(t) = labels(#t) union {x}
     */
    private static <T extends Comparable<T>> void insertInTree(BinaryTree<T> t, T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        //Initialize values
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();

        // If tree size is greater than zero, go to if statement
        if (t.size() > 0) {
            // Disassemble tree into root, left, and right
            T root = t.disassemble(left, right);

            /*
             * If x is less than root, call insertInTree and insert x into the
             * left tree
             */
            if (x.compareTo(root) < 0) {
                insertInTree(left, x);

                /*
                 * Else, x is greater than root, call insertInTree and insert x
                 * into the right tree
                 */
            } else {
                insertInTree(right, x);
            }

            // Reassemble tree
            t.assemble(root, left, right);

            // Else, tree size is zero, insert x as root and reassemble tree
        } else {
            t.assemble(x, left, right);
        }
    }

    /**
     * Removes and returns the smallest (left-most) label in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove the label
     * @return the smallest label in the given {@code BinaryTree}
     * @updates t
     * @requires IS_BST(t) and |t| > 0
     * @ensures <pre>
     * IS_BST(t)  and  removeSmallest = [the smallest label in #t]  and
     *  labels(t) = labels(#t) \ {removeSmallest}
     * </pre>
     */
    private static <T> T removeSmallest(BinaryTree<T> t) {
        assert t != null : "Violation of: t is not null";
        assert t.size() > 0 : "Violation of: |t| > 0";

        // Initialize values
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        T smallest = null;

        // Disassemble tree into root, left, and right
        T root = t.disassemble(left, right);

        /*
         * If left subtree size is greater than zero, remove smallest value from
         * left subtree and reassemble original tree without smallest
         */
        if (left.size() > 0) {
            smallest = removeSmallest(left);
            t.assemble(root, left, right);
            /*
             * Else, left subtree size is zero and root is the smallest in tree.
             * Make tree t the right subtree
             */
        } else {
            smallest = root;
            t.transferFrom(right);
        }

        // Return smallest value in tree
        return smallest;
    }

    /**
     * Finds label {@code x} in {@code t}, removes it from {@code t}, and
     * returns it.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove label {@code x}
     * @param x
     *            the label to be removed
     * @return the removed label
     * @updates t
     * @requires IS_BST(t) and x is in labels(t)
     * @ensures <pre>
     * IS_BST(t)  and  removeFromTree = x  and
     *  labels(t) = labels(#t) \ {x}
     * </pre>
     */
    private static <T extends Comparable<T>> T removeFromTree(BinaryTree<T> t, T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";
        assert t.size() > 0 : "Violation of: x is in labels(t)";

        // Initialize values
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        T removed = null;

        // Disassemble tree into root, left, and right
        T root = t.disassemble(left, right);

        // If x is not equal to root, go to if statement
        if (x.compareTo(root) != 0) {

            /*
             * If x is less than root, call removeFromTree and remove x from the
             * left subtree
             */
            if (x.compareTo(root) < 0) {
                removed = removeFromTree(left, x);

                /*
                 * Else x is greater than root, call removeFromTree and remove x
                 * from the right subtree
                 */
            } else if (x.compareTo(root) > 0) {
                removed = removeFromTree(right, x);
            }
            // Reassemble original tree without removed value
            t.assemble(root, left, right);

            // Go to else statement if x equals root
        } else {

            // Remove root from tree
            removed = root;

            /*
             * If right subtree size is greater than zero, the new root is the
             * smallest in right subtree to follow BST and reassemble tree with
             * new root value
             */
            if (right.size() > 0) {
                root = removeSmallest(right);
                t.assemble(root, left, right);

                /*
                 * Else, right subtree size is zero and tree t is assembled as
                 * the left subtree
                 */
            } else {
                t.transferFrom(left);
            }
        }

        // Return the removed value from tree t
        return removed;
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {

        // Creates new BinaryTree to represent a Set
        this.tree = new BinaryTree1<T>();

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Set3a() {

        this.createNewRep();

    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Set<T> newInstance() {
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
    public final void transferFrom(Set<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Set3a<?>
                : "" + "Violation of: source is of dynamic type Set3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Set3a<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Set3a<T> localSource = (Set3a<T>) source;
        this.tree = localSource.tree;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(T x) {
        assert x != null : "Violation of: x is not null";
        assert !this.contains(x) : "Violation of: x is not in this";

        /*
         * Inserts the specified value x into the tree
         */
        insertInTree(this.tree, x);
    }

    @Override
    public final T remove(T x) {
        assert x != null : "Violation of: x is not null";
        assert this.contains(x) : "Violation of: x is in this";

        /*
         * Removes and returns specified value x in tree
         */
        return removeFromTree(this.tree, x);
    }

    @Override
    public final T removeAny() {
        assert this.size() > 0 : "Violation of: this /= empty_set";

        /*
         * Removes and returns any value in tree; in this case, the smallest
         */
        return removeSmallest(this.tree);
    }

    @Override
    public final boolean contains(T x) {
        assert x != null : "Violation of: x is not null";

        /*
         * Returns true or false if x is in the tree
         */
        return isInTree(this.tree, x);
    }

    @Override
    public final int size() {

        // Returns the size of the tree
        return this.tree.size();
    }

    @Override
    public final Iterator<T> iterator() {
        return this.tree.iterator();
    }

}
