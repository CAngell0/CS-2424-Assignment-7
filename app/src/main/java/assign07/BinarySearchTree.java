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
/*
 * Short answer: No, not as-is. Your toArrayList will break and it also doesn’t match what a SortedSet’s toArrayList() usually means (sorted contents). Here’s what’s wrong and how to fix it—both for a heap-style array layout and for the sorted list most graders expect.

What’s broken in your current toArrayList

Capacity ≠ size:
new ArrayList<>(k) sets capacity, not size. list.set(idx, …) will throw IndexOutOfBoundsException because the list’s size is 0.
→ You must prefill with null up to the needed size.

Wrong length:
For a tree of depth d (levels counted as 1 at the root), the full array size is 2^d - 1, not 2^d.

Null-child recursion:
You call leftNode.addValuesToArrayList(...) and rightNode... without null checks → NullPointerException on leaves.

Empty tree case:
If value == null, your code still tries to write/root and recurse → NPE again.

Semantics mismatch:
A SortedSet’s toArrayList() usually means sorted ascending with no holes. Your method creates a level-order, heap-style array with null gaps. That’s fine for a tree representation method, but it’s not the usual meaning of set→array.

Fix A — Return a sorted list (recommended for SortedSet)

Use in-order traversal. This is simple, linear time, and matches expectations.

@Override
public ArrayList<E> toArrayList() {
    ArrayList<E> out = new ArrayList<>();
    inorder(this, out);
    return out;
}

private void inorder(BinarySearchTree<E> node, ArrayList<E> out) {
    if (node == null || node.value == null) return;
    inorder(node.leftNode, out);
    out.add(node.value);
    inorder(node.rightNode, out);
}

Fix B — If you really want heap-style array layout

Rename it to something like toLevelArray() to avoid confusion, and fix the bugs:

public ArrayList<E> toLevelArray() {
    int depth = getDepth();                 // depth = 0 for empty, 1 for single node
    if (depth == 0) return new ArrayList<>();

    int n = (1 << depth) - 1;               // total slots in a complete tree of this depth
    ArrayList<E> list = new ArrayList<>(n);
    for (int i = 0; i < n; i++) list.add(null);

    fillLevelArray(this, 0, 0, list);       // level=0, position=0 at that level
    return list;
}

private void fillLevelArray(BinarySearchTree<E> node, int level, int pos, ArrayList<E> out) {
    if (node == null || node.value == null) return;
    int idx = (1 << level) + pos - 1;       // 0-based index for (level, pos)
    out.set(idx, node.value);
    fillLevelArray(node.leftNode,  level + 1, pos * 2,     out);
    fillLevelArray(node.rightNode, level + 1, pos * 2 + 1, out);
}

private int getDepth() {
    if (this.value == null) return 0;
    int l = (leftNode  == null) ? 0 : leftNode.getDepth();
    int r = (rightNode == null) ? 0 : rightNode.getDepth();
    return 1 + Math.max(l, r);
}

Notes on this version

Root at (level=0, pos=0) maps to idx = 0.

For a node at (level=L, pos=P), children are (L+1, 2P) and (L+1, 2P+1).

This builds a complete-tree array with null for missing nodes. It does not sort.

Which one should you use?

If your grader expects set semantics: Fix A (in-order).

If you need a structural, heap-style representation for a diagram or assignment note: Fix B, but don’t call it toArrayList()—use a distinct name to avoid failing semantic tests.
 */