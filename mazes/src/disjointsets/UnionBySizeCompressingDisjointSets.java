package disjointsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A quick-union-by-size data structure with path compression.
 * @see DisjointSets for more documentation.
 */
public class UnionBySizeCompressingDisjointSets<T> implements DisjointSets<T> {
    // Do NOT rename or delete this field. We will be inspecting it directly in our private tests.
    List<Integer> pointers;
    Map<T, Integer> map = new HashMap<>();
    /*
    However, feel free to add more fields and private helper methods. You will probably need to
    add one or two more fields in order to successfully implement this class.
    */

    public UnionBySizeCompressingDisjointSets() {
        pointers = new ArrayList<>();
    }

    @Override
    public void makeSet(T item) {
        if (map.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        map.put(item, pointers.size());
        pointers.add(-1);
    }

    @Override
    // public int findSet(T item) {
    //     int index = map.get(item);
    //     while (pointers.get(index) > 0) {
    //         index = pointers.get(index);
    //     }
    //
    //     return index;
    // }

    public int findSet(T item) {
        if (!map.containsKey(item)) {
            throw new IllegalArgumentException();
        } else {
            int index = map.get(item);
            return pathCompression(index);
        }
    }

    private int pathCompression(int index) {
        if (pointers.get(index) < 0) {
            return index;
        } else {
            int temp = pathCompression(pointers.get(index));
            pointers.set(index, temp);
            return temp;
        }
    }
    // private int pathCompression(int index) {
    //     if (pointers.get(index) > 0) {
    //         pointers.set(index, pathCompression(pointers.get(index)));
    //     }
    //     return index;
    // }

    @Override
    public boolean union(T item1, T item2) {
        int set1 = findSet(item1);
        int set2 = findSet(item2);
        int size1 = (-1) * pointers.get(set1);
        int size2 = (-1) * pointers.get(set2);
        if (set1 == set2) {
            return false;
        } else {
            int newSize = size1 + size2;
            if (size1 > size2) {
                pointers.set(set2, set1);
                pointers.set(set1, -1 * newSize);
            } else {
                pointers.set(set1, set2);
                pointers.set(set2, -1 * newSize);
            }
            return true;
        }
    }
}
