package assign07;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ArraySortedSetTest {
    private Integer[] arr1 = {5, 6, -61, 2, 0, 10, 2, null, 8, -10, 19, 4, 5, 23, -2, null, 46, 8, 16, null};
    private Integer[] arr1SetSorted = {-61, -10, -2, 0, 2, 4, 5, 6, 8, 10, 16, 19, 23, 46};

    @Test
    void testEmptySet(){
        ArraySortedSet<Integer> set = new ArraySortedSet<>();
        ArrayList<Integer> array = set.toArrayList();

        assertNotNull(array);
        assertEquals(0, array.size());
    }

    @Test
    void testAdd(){
        ArraySortedSet<Integer> set = new ArraySortedSet<>();
        for (int i = 0; i < 1000; i++) set.add((int) (Math.random() * 100));
        ArrayList<Integer> array = set.toArrayList();
        
        assertNotNull(array);
        assertTrue(array.size() != 0);
        for (int index = 1; index < array.size(); index++){
            assertTrue(array.get(index - 1) < array.get(index));
        }
    }

    @Test
    void testAddWithNull(){
        ArraySortedSet<Integer> set = new ArraySortedSet<>();
        for (int i = 0; i < 1000; i++){
            if (Math.random() * 10 > 5) set.add(null);
            else set.add((int) (Math.random() * 100));
        }
        ArrayList<Integer> array = set.toArrayList();
        
        assertNotNull(array);
        assertTrue(array.size() != 0);
        for (int index = 1; index < array.size(); index++){
            assertTrue(array.get(index - 1) < array.get(index));
        }
    }

    @Test
    void testAddWithNegatives(){
        ArraySortedSet<Integer> set = new ArraySortedSet<>();
        for (int i = 0; i < 1000; i++){
            set.add(((int) (Math.random() * 100)) - 50);
        }
        ArrayList<Integer> array = set.toArrayList();
        
        assertNotNull(array);
        assertTrue(array.size() != 0);
        for (int index = 1; index < array.size(); index++){
            assertTrue(array.get(index - 1) < array.get(index));
        }
    }

    @Test
    void testAddAll(){
        ArraySortedSet<Integer> set = new ArraySortedSet<>();
        set.addAll(Arrays.asList(arr1));
        ArrayList<Integer> array = set.toArrayList();

        assertNotNull(array);
        assertEquals(14, array.size());
        for (int index = 0; index < arr1SetSorted.length; index++){
            assertEquals(array.get(index), arr1SetSorted[index]);
        }
    }
}
