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

    /**Double the backing array's size */
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

    @Override
    public boolean add(E item) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public boolean addAll(Collection<? extends E> items) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addAll'");
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

    @Override
    public boolean contains(E item) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'contains'");
    }

    @Override
    public boolean containsAll(Collection<? extends E> items) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsAll'");
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }

    @Override
    public E min() throws NoSuchElementException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'min'");
    }

    @Override
    public E max() throws NoSuchElementException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'max'");
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'size'");
    }

    @Override
    public ArrayList<E> toArrayList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArrayList'");
    }
    
}
