package assign07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class ArraySortedSet<E extends Comparable<? super E>> implements SortedSet<E> {
    private Object[] arr;
    private int size;
    private int defaultSize;

    public ArraySortedSet(){
        this.arr = new Object[10];
        this.defaultSize = 10;
        this.size = 0;
    }

    private E get(int i){
        return (E) arr[i];
    }

    /**
     * Double the backing array's size 
     * @note Method was copied from SortedArrayList in assignment 3
     */
    private void doubleArraySize(){
        //Creates a copy of the original array and resets the old array with a doubled size
        Object[] originalCopy = Arrays.copyOf(arr, arr.length);
        arr = new Object[arr.length * 2];

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
            int c = get(mid).compareTo(element);

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
        for(int i = size; i > place; i--) this.arr[i] = get(i-1);

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
        boolean didAdd = false;
        for(E item : items){    
            if(this.add(item)){
                didAdd = true;
            }
        }
        return didAdd;
    }

    @Override
    public void clear() {
        arr = new Object[defaultSize];
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
        return get(0);
    }

    @Override
    public E max() throws NoSuchElementException {
        if (size == 0) throw new NoSuchElementException("Set is empty.");
        return get(size-1);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public ArrayList<E> toArrayList() {
        ArrayList<E> returnList = new ArrayList<>(size);
        for(int i = 0; i < this.size; i++) returnList.add(get(i));
        return returnList;
    }
}
