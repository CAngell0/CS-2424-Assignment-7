package assign07;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Interface that represents a sorted set of generically-typed comparable items.
 * By definition, a set contains no duplicate items. The items are ordered using
 * their natural ordering (i.e., each item must be Comparable). Note that this
 * interface is similar Java's SortedSet, but simpler and with a few of the
 * methods named differently.
 *
 * @author CS 2420 course staff
 * @version 2025-10-16
 */
public interface SortedSet<Type extends Comparable<? super Type>> {
  /**
   * Ensure that this set contains the specified item.
   *
   * @param item - the item whose presence is ensured in this set
   * @return true if this set changed as a result of this method call (that is, if
   *         the input item was actually inserted); otherwise, returns false
   */
  public boolean add(Type item);

  /**
   * Ensure that this set contains all items in the specified collection.
   *
   * @param items - the collection of items whose presence is ensured in this set
   * @return true if this set changed as a result of this method call (that is, if
   *         any item in the input collection was actually inserted); otherwise,
   *         returns false
   */
  public boolean addAll(Collection<? extends Type> items);

  /**
   * Remove all items from this set. The set will be empty after this method
   * call.
   */
  public void clear();

  /**
   * Determine if there is an item in this set that is equal to the specified
   * item.
   *
   * @param item - the item sought in this set
   * @return true if there is an item in this set that is equal to the input item;
   *         otherwise, returns false
   */
  public boolean contains(Type item);

  /**
   * Determine if for each item in the specified collection, there is an item in
   * this set that is equal to it.
   *
   * @param items - the collection of items sought in this set
   * @return true if for each item in the specified collection, there is an item
   *         in this set that is equal to it; otherwise, returns false
   */
  public boolean containsAll(Collection<? extends Type> items);

  /**
   * @return true if this set contains no items; otherwise, false
   */
  public boolean isEmpty();

  /**
   * Return the smallest item in this set.
   *
   * @throws NoSuchElementException if the set is empty
   */
  public Type min() throws NoSuchElementException;

  /**
   * Return the largest item in this set.
   *
   * @throws NoSuchElementException if the set is empty
   */
  public Type max() throws NoSuchElementException;

  /**
   * Ensure that this set does not contain the specified item.
   *
   * @param item - the item whose absence is ensured in this set
   * @return true if this set changed as a result of this method call (that is,
   *         if the input item was actually removed); otherwise, returns false
   * @throws UnsupportedOperationException if the {@code remove} operation is
   *         not supported by the derived class
   */
  public default boolean remove(Type item) {
    throw new UnsupportedOperationException("remove");
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
   *         not supported by the derived class
   */
  public default boolean removeAll(Collection<? extends Type> items) {
    throw new UnsupportedOperationException("removeAll");
  }

  /**
   * @return the number of items in this set.
   */
  public int size();

  /**
   * @return an ArrayList containing all the items in this set, in sorted
   * order.
   */
  public ArrayList<Type> toArrayList();
}
