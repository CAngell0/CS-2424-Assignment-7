package assign07;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class BinarySearchTreeTest {
    private BinarySearchTree<Integer> empty;
    private BinarySearchTree<Integer> tree;

    @BeforeEach
    void setup(){
        empty = new BinarySearchTree<Integer>();
        tree = new BinarySearchTree<Integer>();

        for(int v : new int[] {2,2,4,6,21,1,2,-4,1,5,7,3}){
            tree.add(v);
        }
    }

    @Test
    void testNoParameterConstructor(){
        assertTrue(empty.isEmpty());
    }

    @Test
    void testContains(){
        assertTrue(tree.contains(2));
    }

    @Test
    void testAdd(){
        empty.add(5);
        assertTrue(empty.contains(5));
    }

    @Test
    void testRemove(){
        tree.remove(2);
        assertFalse(tree.contains(2));
    }

}
