package assign07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;

public class ArraySortedSet<E extends Comparable<? super E>> implements SortedSet<E> {
    private E[] arr;
    private int size;
    private int defaultSize;

    @SuppressWarnings("unchecked")
    public ArraySortedSet(){
        this.arr = (E[]) new Object[10];
        this.defaultSize = 10;
        this.size = 0;
    }

    /**
     * Double the backing array's size 
     * @note Method was copied from SortedArrayList in assignment 3
     */
    @SuppressWarnings("unchecked")
    private void doubleArraySize(){
        //Creates a copy of the original array and resets the old array with a doubled size
        E[] originalCopy = Arrays.copyOf(arr, arr.length);
        arr = (E[]) new Object[arr.length * 2];

        //Puts all the data into the newly sized array
        for (int i = 0; i < originalCopy.length; i++){
            arr[i] = originalCopy[i];
        }
    }

    /**
     * A helper binary search method to be used throughout the class
     * @param element The element to search for
     * @return Returns the index if the element was found (or an index of where it should be)
     * @note Method was copied from SortedArrayList in assignment 3
     */
    private int binarySearch(E element){
        int low = 0;
        int high = this.size - 1;
        
        while (low <= high){
            int mid = (low + high) / 2;
            int c = arr[mid].compareTo(element);

            if(c < 0) low = mid + 1;
            else if(c > 0) high = mid -1;
            else return mid;
        }

        return -(low + 1);
    }

    /**
     * Inserts the element into the backing array.
     * @param element The element to insert
     * @note Method was copied from SortedArrayList in assignment 3
     */
    private void insert(E element) {
        //If the array is full, double its size
        if (this.size == arr.length) doubleArraySize();

        //Finds where the element should go using binary search
        //The ternary operator changes the searchResult value to the right index if the element is new to the array
        int searchResult = this.binarySearch(element);
        int place = searchResult >= 0 ? searchResult: -(searchResult + 1);
        
        //Shifts elements to the right
        for(int i = size; i > place; i--) this.arr[i] = this.arr[i-1];

        this.arr[place] = element;
        size++;
    }

    @Override
    public boolean add(E item) {
        if (contains(item)) return false;

        insert(item);
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> items) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addAll'");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        arr = (E[]) new Object[defaultSize];
        this.size = 0;
    }

    @Override
    public boolean contains(E item) {
        return this.binarySearch(item) >= 0;
    }

    @Override
    public boolean containsAll(Collection<? extends E> items) {
        for (E item : items){
            if (this.binarySearch(item) < 0) return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public E min() throws NoSuchElementException {
        if (size == 0) throw new NoSuchElementException("Set is empty.");
        return arr[0];
    }

    @Override
    public E max() throws NoSuchElementException {
        if (size == 0) throw new NoSuchElementException("Set is empty.");
        return arr[size - 1];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public ArrayList<E> toArrayList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArrayList'");
    }
}
