package assign07;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class BinarySearchTreeTest {
    private Integer[] arr1 = {5, 1, 6, 9, 10, 2, 4, 23, 19, -1, -7, 2, 5, 7};
    private Integer[] arr1SetSorted = {-7, -1, 1, 2, 4, 5, 6, 7, 9, 10, 19, 23};

    @Test
    void testSize(){
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();

        for (Integer num : arr1){
            tree.add(num);
        }

        assertNotNull(tree);
        assertEquals(12, tree.size());
    }

    @Test 
    void testContains(){
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        for (Integer num : arr1){
            tree.add(num);
        }

        assertNotNull(tree);
        assertEquals(12, tree.size());
        for (Integer num : arr1){
            assertTrue(tree.contains(num));
        }
    }

    @Test
    void testContainsAll(){
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        for (Integer num : arr1){
            tree.add(num);
        }

        assertNotNull(tree);
        assertEquals(12, tree.size());
        assertTrue(tree.containsAll(Arrays.asList(arr1)));
    }

    @Test
    void testMin(){
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        for (Integer num : arr1){
            tree.add(num);
        }

        assertNotNull(tree);
        assertEquals(12, tree.size());
        assertEquals(-7, tree.min());
    }

    @Test
    void testMax(){
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        for (Integer num : arr1){
            tree.add(num);
        }

        assertNotNull(tree);
        assertEquals(12, tree.size());
        assertEquals(23, tree.max());
    }

    @Test
    void testToArrayList(){
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        for (Integer num : arr1){
            tree.add(num);
        }
        ArrayList<Integer> arrayList = tree.toArrayList();

        assertNotNull(tree);
        assertNotNull(arrayList);
        assertEquals(12, tree.size());
        assertEquals(12, arrayList.size());
        assertEquals(tree.size(), arrayList.size());

        for (int index = 0; index < arr1SetSorted.length; index++){
            assertEquals(arr1SetSorted[index], arrayList.get(index));
        }
    }
}
