package assign07;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

public class BinarySearchTree<E extends Comparable<? super E>> implements SortedSet<E> {
    private E value;
    private BinarySearchTree<E> leftNode;
    private BinarySearchTree<E> rightNode;

    public BinarySearchTree(E value, BinarySearchTree<E> leftNode, BinarySearchTree<E> rightNode) {
        this.value = value;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

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

    @Override
    public boolean addAll(Collection<? extends E> items) {
        boolean didAdd = false;
        for (E item : items) {
            if (this.add(item)) {
                didAdd = true;
            }
        }
        return didAdd;
    }

    @Override
    public void clear() {
        this.value = null;
        this.leftNode = null;
        this.rightNode = null;
    }

    @Override
    public boolean contains(E item) {
        if (value == null && leftNode == null && rightNode == null)
            return false;
        BinarySearchTree<E> node = this;
        int comparison = 0;

        do {
            comparison = item.compareTo(node.value);
            if (comparison < 0)
                node = node.leftNode;
            else if (comparison > 0)
                node = node.rightNode;

            if (node == null)
                return false;
        } while (comparison != 0);

        return true;
    }

    @Override
    public boolean containsAll(Collection<? extends E> items) {
        for (E item : items) {
            if (!contains(item))
                return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return (this.value == null && this.leftNode == null && this.rightNode == null);
    }

    @Override
    public E min() throws NoSuchElementException {
        if (this.value == null)
            throw new NoSuchElementException("The tree is empty.");
        BinarySearchTree<E> node = this;
        while (node.leftNode != null)
            node = node.leftNode;
        return node.value;
    }

    @Override
    public E max() throws NoSuchElementException {
        if (this.value == null)
            throw new NoSuchElementException("The tree is empty.");
        BinarySearchTree<E> node = this;
        while (node.rightNode != null)
            node = node.rightNode;
        return node.value;
    }

    @Override
    public int size() {
        return size(this);
    }

    private int size(BinarySearchTree<E> node) {
        if (node == null || node.value == null)
            return 0;
        return 1 + size(node.leftNode) + size(node.rightNode);
    }

    /**
     * Ensure that this set does not contain the specified item.
     *
     * @param item - the item whose absence is ensured in this set
     * @return true if this set changed as a result of this method call (that is,
     *         if the input item was actually removed); otherwise, returns false
     * @throws UnsupportedOperationException if the {@code remove} operation is
     *                                       not supported by the derived class
     */
    public boolean remove(E item) {
        // empty
        if (this.value == null)
            return false;

        int cmp = item.compareTo(this.value);

        // left node
        if (cmp < 0) {
            // If left child doesnt exist item is not in treee
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

    private void transplant(BinarySearchTree<E> child) {
        this.value = child.value;
        this.leftNode = child.leftNode;
        this.rightNode = child.rightNode;
    }

    private boolean isEmptyNode(BinarySearchTree<E> node) {
        return node != null && node.value == null && node.leftNode == null && node.rightNode == null;
    }

    /**
     * Ensure that this set does not contain any of the items in the specified
     * collection.
     *
     * @param items - the collection of items whose absence is ensured in this set
     * @return true if this set changed as a result of this method call (that is,
     *         if any item in the input collection was actually removed); otherwise,
     *         returns false
     * @throws UnsupportedOperationException if the {@code removeAll} operation is
     *                                       not supported by the derived class
     */
    public boolean removeAll(Collection<? extends E> items) {
        boolean didRemove = false;
        for (E item : items) {
            if (this.remove(item)) {
                didRemove = true;
            }
        }
        return didRemove;
    }

    @Override
    public ArrayList<E> toArrayList() {
        ArrayList<E> out = new ArrayList<>();
        inorder(this, out);
        return out;
    }

    private void inorder(BinarySearchTree<E> node, ArrayList<E> out) {
        if (node == null || node.value == null)
            return;
        inorder(node.leftNode, out);
        out.add(node.value);
        inorder(node.rightNode, out);
    }
}
