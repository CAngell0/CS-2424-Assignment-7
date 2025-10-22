package assign07;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

public class BinarySearchTree<E extends Comparable<? super E>> implements SortedSet<E>{
    private E value;
    private BinarySearchTree<E> leftNode;
    private BinarySearchTree<E> rightNode;

    public BinarySearchTree(E value, BinarySearchTree<E> leftNode, BinarySearchTree<E> rightNode){
        this.value = value;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    @Override
    public boolean add(E item) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> items) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addAll'");
    }

    @Override
    public void clear() {
        this.value = null;
        this.leftNode = null;
        this.rightNode = null;
    }

    @Override
    public boolean contains(E item) {
        if (value == null && leftNode == null && rightNode == null) return false;
        BinarySearchTree<E> node = this;
        int comparison = 0;

        do {
            comparison = node.value.compareTo(item);
            if (comparison < 0) node = leftNode;
            else if (comparison > 0) node = rightNode;

            if (node == null) return false;
        }
        while (comparison != 0);

        return true;
    }

    @Override
    public boolean containsAll(Collection<? extends E> items) {
        for (E item : items){
            if (!contains(item)) return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return (this.size() == 0);
    }

    @Override
    public E min() throws NoSuchElementException {
        BinarySearchTree<E> node = this;
        while (node.leftNode != null) node = node.leftNode;
        return node.value;
    }

    @Override
    public E max() throws NoSuchElementException {
        BinarySearchTree<E> node = this;
        while (node.rightNode != null) node = node.rightNode;
        return node.value;
    }

    @Override
    public int size() {
        return size(this);    
    }

    private int size(BinarySearchTree<E> node){
        if(node == null || node.value == null) return 0;
        return 1+ size(node.leftNode) + size(node.rightNode);
    }



    @Override
    public ArrayList<E> toArrayList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArrayList'");
    }
}
