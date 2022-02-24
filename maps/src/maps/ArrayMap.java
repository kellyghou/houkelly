package maps;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ArrayMap<K, V> extends AbstractIterableMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 100;
    //Anju : I set the initial capacity to 20, a random choice
    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    SimpleEntry<K, V>[] entries;
    private int size;

    // You may add extra fields or helper methods though!

    /**
     * Constructs a new ArrayMap with default initial capacity.
     */
    public ArrayMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Constructs a new ArrayMap with the given initial capacity (i.e., the initial
     * size of the internal array).
     *
     * @param initialCapacity the initial capacity of the ArrayMap. Must be > 0.
     */
    public ArrayMap(int initialCapacity) {
        this.entries = this.createArrayOfEntries(initialCapacity);
        this.size = 0;
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code Entry<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     */
    @SuppressWarnings("unchecked")
    private SimpleEntry<K, V>[] createArrayOfEntries(int arraySize) {
        /*
        It turns out that creating arrays of generic objects in Java is complicated due to something
        known as "type erasure."

        We've given you this helper method to help simplify this part of your assignment. Use this
        helper method as appropriate when implementing the rest of this class.

        You are not required to understand how this method works, what type erasure is, or how
        arrays and generics interact.
        */
        return (SimpleEntry<K, V>[]) (new SimpleEntry[arraySize]);
    }

    @Override
    public V get(Object key) {
        //checks if the array contains the given key
        //returns null if it doesn't exist
        if (!containsKey(key)) {
            return null;
        }

        //num is used to track the current index in the array map
        int num = 0;

        while (!Objects.equals(entries[num].getKey(), key)) {
            num++;
        }
        return entries[num].getValue();
    }

    @Override
    public V put(K key, V value) {
        //Creates a new simpleEntry object with the given key and value
        SimpleEntry<K, V> newEntry = new SimpleEntry<>(key, value);
        //runs if the array map already contains this key
        if (containsKey(key)) {
            //num is used to track the current index in the array map
            int num = 0;
            while (!Objects.equals(entries[num].getKey(), key)) {
                num++;
            }
            //stores the previous value corresponding to the key
            V prev = entries[num].getValue();
            //sets the given new value to the given key
            entries[num].setValue(value);
            //returns the previously stored value for the key
            return prev;

            //runs if the array map does not contain the given key
            //adds a new key value pair to the array map
        } else {
            //checks if the array map is full
            //if size = capacity (meaning the array is full),
            //resize to an array map of double the size
            if (size == entries.length - 1) {
                resize(entries.length * 2);
            }

            //currIndex keeps track of the current index in the array map
            int currIndex = 0;
            //creates a "pointer" to keep track of the current entry in the array map
            SimpleEntry<K, V> current = entries[currIndex];
            //loops until an empty spot in the array is found
            while (current != null) {
                currIndex++;
                current = entries[currIndex];
            }

            //inserts the new entry (with the given key and value) into the empty slot
            entries[currIndex] = newEntry;
            //increments size since a new entry is added
            size++;
            //returns null since there was no previous value associated with the key
            return null;
        }
    }

    @Override
    public V remove(Object key) {
        //checks if the array map contains the given key
        //returns null if the array map doesn't contain the given key.
        if (!containsKey(key)) {
            return null;
        } else {
            //decrements the size since an entry is being removed
            size--;

            //stores the key and value pair of the last entry in the array map
            //this is the entry at index = size
            SimpleEntry<K, V> temp  = new SimpleEntry<>(entries[size].getKey(),
                entries[size].getValue());

            //num keeps track of the current index in the array map
            int num = 0;
            //loops until the entry with the given key is found
            while (!Objects.equals(entries[num].getKey(), key)) {
                num++;
            }
            //stores the value that was previously paired with the given key (to be removed)
            V var =  entries[num].getValue();
            //replace the removed entry with the last entry in the array map
            entries[num] = temp;

            entries[size] =  null;

            return var;
        }
    }

    @Override
    public void clear() {
        // for (int i = 0; i < entries.length; i++) {
        //     entries[i] = null;
        // }

        //clears the array map by filling it with null entries
        //and setting the size to zero
        Arrays.fill(entries, null);
        size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        //returns false if the array map is empty
        if (size == 0) {
            return false;
        }

        //num keeps track of the current index of the map
        int num = 0;

        //runs while the given key is not found
        //i.e. loops until the given key is found
        while (!Objects.equals(entries[num].getKey(), key)) {
            num++;
            //if the index goes over the size, the key did not exist
            if (num > size - 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    private void resize(int capacity) {
        //creates a new array map with double the capacity
        SimpleEntry<K, V>[] newEntries = this.createArrayOfEntries(capacity);
        // for (int i = 0; i < entries.length; i++) {
        //     newEntries[i] = entries[i];
        // }

        //copy the contents of the previous array map to the new array map
        System.arraycopy(entries, 0, newEntries, 0, entries.length);
        this.entries = newEntries;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: You may or may not need to change this method, depending on whether you
        // add any parameters to the ArrayMapIterator constructor.
        return new ArrayMapIterator<>(this.entries);
    }


    private static class ArrayMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final SimpleEntry<K, V>[] entries;
        //keeps track of the current index of the array map
        private int curr;

        // You may add more fields and constructor parameters

        public ArrayMapIterator(SimpleEntry<K, V>[] entries) {
            this.entries = entries;
            this.curr = 0;
        }

        @Override
        public boolean hasNext() {
            //returns true if the current entry is not null
            return (entries[curr] != null);
        }

        @Override
        public Map.Entry<K, V> next() {
            //throws an exception if the array map has no next entry
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            //otherwise return the current entry
            SimpleEntry<K, V> entry = entries[curr];
            curr++;
            return entry;
        }
    }
}
