package assign07;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * A Binary Search Tree data structure where instances also represent tree
 * nodes.
 * 
 * @author Joshua Varughese and Carson Angell
 * @version 23/10/25
 */
public class BinarySearchTree<E extends Comparable<? super E>> implements SortedSet<E> {
    private E value;
    private BinarySearchTree<E> leftNode;
    private BinarySearchTree<E> rightNode;

    public BinarySearchTree(E value, BinarySearchTree<E> leftNode, BinarySearchTree<E> rightNode) {
        this.value = value;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    /** {@inheritDoc} */
    @Override
    public boolean add(E item) {
        // empty tree
        if (this.value == null) {
            this.value = item;
            this.leftNode = null;
            this.rightNode = null;

            return true;
        }

        int cmp = item.compareTo(this.value);

        // duplicate
        if (cmp == 0) {
            return false;
        }
        // item smaller
        else if (cmp < 0) {
            if (this.leftNode == null) {
                this.leftNode = new BinarySearchTree<>(item, null, null);
                return true;
            }
            return this.leftNode.add(item);
        }
        // item larger
        else {
            if (this.rightNode == null) {
                this.rightNode = new BinarySearchTree<>(item, null, null);
                return true;
            }
            return this.rightNode.add(item);
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean addAll(Collection<? extends E> items) {
        // Iterates through the collection and calls .add() on all the items.
        boolean didAdd = false;
        for (E item : items) {
            if (this.add(item)) {
                didAdd = true;
            }
        }
        return didAdd;
    }

    /** {@inheritDoc} */
    @Override
    public void clear() {
        this.value = null;
        this.leftNode = null;
        this.rightNode = null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean contains(E item) {
        // Check for an empty tree
        if (value == null && leftNode == null && rightNode == null)
            return false;
        BinarySearchTree<E> node = this;
        int comparison = 0;

        // Traverse the tree using pre-order traversal (non-recursively)
        // If it reaches the end, return false;
        do {
            comparison = item.compareTo(node.value);
            if (comparison < 0)
                node = node.leftNode;
            else if (comparison > 0)
                node = node.rightNode;

            if (node == null)
                return false;
        } while (comparison != 0);

        // While loop breaks when it finds a matching element, so it returns true here.
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsAll(Collection<? extends E> items) {
        for (E item : items) {
            if (!contains(item))
                return false;
        }
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isEmpty() {
        return (this.value == null && this.leftNode == null && this.rightNode == null);
    }

    /** {@inheritDoc} */
    @Override
    public E min() throws NoSuchElementException {
        if (this.value == null)
            throw new NoSuchElementException("The tree is empty.");

        // Traverses the tree using post-order to get the smallest element at the
        // bottom-left of the tree
        BinarySearchTree<E> node = this;
        while (node.leftNode != null)
            node = node.leftNode;
        return node.value;
    }

    /** {@inheritDoc} */
    @Override
    public E max() throws NoSuchElementException {
        if (this.value == null)
            throw new NoSuchElementException("The tree is empty.");

        // Traverses the tree to the bottom-right element and returns it
        BinarySearchTree<E> node = this;
        while (node.rightNode != null)
            node = node.rightNode;
        return node.value;
    }

    /** {@inheritDoc} */
    @Override
    public int size() {
        return size(this);
    }

    /**
     * Private recursive method for size
     * 
     * @param node
     * @return
     */
    private int size(BinarySearchTree<E> node) {
        if (node == null || node.value == null)
            return 0;
        return 1 + size(node.leftNode) + size(node.rightNode);
    }

    /** {@inheritDoc} */
    public boolean remove(E item) {
        // empty
        if (this.value == null)
            return false;

        int cmp = item.compareTo(this.value);

        // left node
        if (cmp < 0) {
            // If left child doest exist item is not in treee
            if (this.leftNode == null)
                return false;
            // recurse in left subtree
            boolean removed = this.leftNode.remove(item);

            // Checks if we have found the item in the else statement and then unlinks the
            // node and returns true
            if (isEmptyNode(this.leftNode))
                this.leftNode = null;
            return removed;

        }
        // right node
        else if (cmp > 0) {
            // if right child doesnt exsist item is not in tree
            if (this.rightNode == null)
                return false;
            // recurse in right subtree
            boolean removed = this.rightNode.remove(item);

            if (isEmptyNode(this.rightNode))
                this.rightNode = null;
            return removed;
        }
        // found item
        else {
            // no children
            if (this.leftNode == null && this.rightNode == null) {
                // dont need to handle children and this node is set to be unlinked
                this.value = null;
                return true;
            }

            // 1 child
            // has right child
            if (this.leftNode == null) {
                transplant(this.rightNode);
                return true;
            }
            // has left child
            if (this.rightNode == null) {
                transplant(this.leftNode);
                return true;
            }
            // has two children
            // gets the smallest item in the right subtree
            BinarySearchTree<E> successor = this.rightNode;
            while (successor.leftNode != null) {
                successor = successor.leftNode;
            }

            // copy value from the successor which is replacing this node
            this.value = successor.value;

            // recurse to remove the duplicate from where the successor was found
            // removal will be 0 or 1 child
            boolean removed = this.rightNode.remove(this.value);

            // unlink node
            if (isEmptyNode(this.rightNode))
                this.rightNode = null;
            return removed;

        }
    }

    /**
     * Moves the values from childnode to this node
     * 
     * @param child
     */
    private void transplant(BinarySearchTree<E> child) {
        this.value = child.value;
        this.leftNode = child.leftNode;
        this.rightNode = child.rightNode;
    }

    /**
     * Checks if the node is ready to be unlinked
     * 
     * @param node
     * @return
     */
    private boolean isEmptyNode(BinarySearchTree<E> node) {
        return node != null && node.value == null && node.leftNode == null && node.rightNode == null;
    }

    /** {@inheritDoc} */
    public boolean removeAll(Collection<? extends E> items) {
        // Iterates through the collection and calls .remove() on all items.
        boolean didRemove = false;
        for (E item : items) {
            if (this.remove(item)) {
                didRemove = true;
            }
        }
        return didRemove;
    }

    // This method took so long because we didnt read the java doc and were trying
    // to do it in the format shown in class :/
    /** {@inheritDoc} */
    @Override
    public ArrayList<E> toArrayList() {
        ArrayList<E> out = new ArrayList<>();
        inorder(this, out);
        return out;
    }

    /**
     * A helper method that traverses the tree with in-order traversal and adds each
     * element it encounters
     * to 'out' ArrayList.
     * 
     * @param node The node to recursively traverse through
     * @param out  The output ArrayList of what element it visited in order.
     */
    private void inorder(BinarySearchTree<E> node, ArrayList<E> out) {
        if (node == null || node.value == null)
            return;
        inorder(node.leftNode, out);
        out.add(node.value);
        inorder(node.rightNode, out);
    }
}
