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
        // empty tree
        if(this.value == null){
            this.value = item;
            this.leftNode = null;
            this.rightNode = null;

            return true;
        }

        int cmp = item.compareTo(this.value);

        //duplicate
        if(cmp == 0){
            return false;
        } 
        //item smaller 
        else if(cmp < 0){
            if(this.leftNode == null){
                this.leftNode = new BinarySearchTree<>(item, null, null);
                return true;
            }
            return this.leftNode.add(item);
        }
        //item larger
        else{
            if(this.rightNode == null){
                this.rightNode = new BinarySearchTree<>(item, null, null);
                return true;
            }
            return this.rightNode.add(item);
        }

    }

    @Override
    public boolean addAll(Collection<? extends E> items) {
        boolean didAdd = false;
        for(E item:items){
            if(this.add(item)){
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
        if (value == null && leftNode == null && rightNode == null) return false;
        BinarySearchTree<E> node = this;
        int comparison = 0;

        do {
            comparison = item.compareTo(node.value);
            if (comparison < 0) node = node.leftNode;
            else if (comparison > 0) node = node.rightNode;

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
        ArrayList<E> list = new ArrayList<E>((int) Math.pow(2, getDepth()));

        this.addValuesToArrayList(list, 0, 0);

        return list;
    }

    private void addValuesToArrayList(ArrayList<E> list, int level, int position){
        list.set((int) (Math.pow(2, level) + position - 1), value);
        leftNode.addValuesToArrayList(list, level + 1, position * 2);
        rightNode.addValuesToArrayList(list, level + 1, position * 2 + 1);
    }

    private int getDepth(){
        if (leftNode == null && rightNode == null) return 1;

        int leftDepth = 0, rightDepth = 0;
        if (this.leftNode != null) leftDepth = leftNode.getDepth();
        if (this.rightNode != null) rightDepth = rightNode.getDepth();

        return Math.max(leftDepth, rightDepth) + 1;
    }
}
