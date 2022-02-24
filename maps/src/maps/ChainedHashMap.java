package maps;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
// import java.util.Objects;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ChainedHashMap<K, V> extends AbstractIterableMap<K, V> {
    private static final double DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD = 0.5;
    private static final int DEFAULT_INITIAL_CHAIN_COUNT = 10;
    private static final int DEFAULT_INITIAL_CHAIN_CAPACITY = 10;

    private double resizingLoadFactorThreshold;
    private int chainCount;
    private int chainCapacity;
    private int size = 0;

    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    AbstractIterableMap<K, V>[] chains;
    // You're encouraged to add extra fields (and helper methods) though!

    /**
     * Constructs a new ChainedHashMap with default resizing load factor threshold,
     * default initial chain count, and default initial chain capacity.
     */
    public ChainedHashMap() {
        this(DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD, DEFAULT_INITIAL_CHAIN_COUNT, DEFAULT_INITIAL_CHAIN_CAPACITY);
    }

    /**
     * Constructs a new ChainedHashMap with the given parameters.
     *
     * @param resizingLoadFactorThreshold the load factor threshold for resizing. When the load factor
     *                                    exceeds this value, the hash table resizes. Must be > 0.
     * @param initialChainCount the initial number of chains for your hash table. Must be > 0.
     * @param chainInitialCapacity the initial capacity of each ArrayMap chain created by the map.
     *                             Must be > 0.
     */
    public ChainedHashMap(double resizingLoadFactorThreshold, int initialChainCount, int chainInitialCapacity) {
        if (resizingLoadFactorThreshold > 0) {
            this.resizingLoadFactorThreshold = resizingLoadFactorThreshold;
        }
        if (initialChainCount > 0) {
            chainCount = initialChainCount;
        }
        if (chainInitialCapacity > 0) {
            chainCapacity = chainInitialCapacity;
        }

        this.chains = createArrayOfChains(initialChainCount);
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code AbstractIterableMap<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     * @see ArrayMap createArrayOfEntries method for more background on why we need this method
     */
    @SuppressWarnings("unchecked")
    private AbstractIterableMap<K, V>[] createArrayOfChains(int arraySize) {
        return (AbstractIterableMap<K, V>[]) new AbstractIterableMap[arraySize];
    }

    /**
     * Returns a new chain.
     *
     * This method will be overridden by the grader so that your ChainedHashMap implementation
     * is graded using our solution ArrayMaps.
     *
     * Note: You do not need to modify this method.
     */
    protected AbstractIterableMap<K, V> createChain(int initialSize) {
        return new ArrayMap<>(initialSize);
    }

    @Override
    public V get(Object key) {
        //calls the containskey method of this class
        if (containsKey(key)) {
            //calculates the hashcode and index of the given key
            int hashcode = 0;
            if (key != null) {
                hashcode = Math.abs(key.hashCode());
            }
            int chainsIndex = hashcode % chains.length;
            //calls the get() method (on the arraymap) at the index of the given key
            //returns the corresponding value
            return chains[chainsIndex].get(key);
        }
        //returns null if the given key is not in the chained hash map
        return null;
    }

    @Override
    public V put(K key, V value) {
        //calculates the load factor and checks if it exceeds the threshold
        double currentLoadFactor = ((double) size) / chains.length;
        if (currentLoadFactor >= resizingLoadFactorThreshold) {
            //if it exceeds the threshold, resize to twice as many chains
            resize(chains.length * 2);
        }

        //calculates the hashcode and index of the given key
        int hashcode = 0;
        if (key != null) {
            hashcode = Math.abs(key.hashCode());
        }
        int chainsIndex = hashcode % chains.length;

        //if there is no array map at the index, i.e. there are no entries at this index
        if (chains[chainsIndex] == null) {
            //create a new empty array map at the index
            AbstractIterableMap<K, V> newMap = createChain(chainCapacity);
            chains[chainsIndex] = newMap;
        }
        //puts the given key value pair at the index
        //since put() method in array map returns the previous value or null if there was no previous value
        //prev stores the previous value or null if there was no previous value
        V prev = chains[chainsIndex].put(key, value);
        //increments size by 1 if prev is null, meaning the given key was newly added
        if (prev == null) {
            size++;
        }
        return prev;
    }

    @Override
    public V remove(Object key) {
        V prev = null;
        //calculates the hashcode and index
        int hashcode = 0;
        if (key != null) {
            hashcode = Math.abs(key.hashCode());
        }
        int chainsIndex = hashcode % chains.length;

        //if there is a non-null array map at the index
        //meaning the given key exits at this index
        if (chains[chainsIndex] != null) {
            //remove the entry with the given key and store its corresponding value
            prev = chains[chainsIndex].remove(key);
            //decrement since an entry was removed
            if (prev != null) {
                size--;
            }
        }
        return prev;
    }

    @Override
    public void clear() {
        //fill the chained hash map with null values
        Arrays.fill(chains, null);
        size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        //iterates through the entire chained hash map
        for (int i = 0; i < chains.length; i++) {
            //if the array map at the index is not null AND they array map contains an entry with the given key
            if (chains[i] != null && chains[i].containsKey(key)) {
                return true;
            }
        }
        //if no entry with the given key was found after iterating through the whole map
        //return false
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ChainedHashMapIterator<>(this.chains);
    }


    /*
    See the assignment webpage for tips and restrictions on implementing this iterator.
     */
    private static class ChainedHashMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final AbstractIterableMap<K, V>[] chains;
        //keeps track of the iterator of the current non-null entry
        private Iterator<Entry<K, V>> currentIterator;
        private int curr;
        //keeps track of the current non-null entry
        private AbstractIterableMap<K, V> currentChain;


        public ChainedHashMapIterator(AbstractIterableMap<K, V>[] chains) {
            this.chains = chains;
            this.curr = -1;
            findBucket();
        }

        //finds the next non-empty chain
        public void findBucket() {
            if (chains != null) {
                //iterate though the chained hash map, starting at the current index
                for (int i = curr + 1; i < chains.length; i++) {
                    //sets the current chain to the array map at the current index
                    currentChain = chains[i];
                    //if the array map at the current chain is not null, but its size is zero
                    //meaning it is an empty array map
                    if (currentChain != null && currentChain.size() == 0) {
                        //set the current chain to null
                        //if there are no entries in the array map, it should be null
                        currentChain = null;
                    }
                    //if the array map at the current chain is not null
                    if (currentChain != null) {
                        //set the current iterator to the iterator of the current chain
                        this.currentIterator = chains[i].iterator();
                        //current index keeps track of which index (in the chained hash map) I'm currently on
                        //i.e. the index of the current chain
                        curr = i;
                        break;
                    }
                }
            }

        }


        @Override
        public boolean hasNext() {
            //returns true if the current  iterator is not null, and has next
            return currentIterator != null &&  currentIterator.hasNext();
        }

        @Override
        public Map.Entry<K, V> next() {
            //throws exception if hasNext is false
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            //this is  the current entry returned by the next() call
            //this is the entry to be returned by this method
            Entry<K, V> temp = currentIterator.next();

            //if the current iterator doesn't have a next
            //meaning I'm on the last chain in the array map
            if (!currentIterator.hasNext()) {
                //set the current chain to null since all elements have been exhaused
                //looks for the next non-empty chain
                currentChain = null;
                findBucket();
            }

            //retuens the current entry
            return temp;
        }
    }


    private void resize(int newCount) {
        //makes a new chained hash map with twice as many chains
        AbstractIterableMap<K, V>[] largerMap = createArrayOfChains(newCount);

        //for every entry in this chained hashmap
        //uses the iterator of this class
        for (Entry<K, V> currEntry : this) {
            //stores its key and value
            K tempKey = currEntry.getKey();
            V tempValue = currEntry.getValue();

            //calculates the hashcode and index in the larger map
            int hashcode = 0;
            if (tempKey != null) {
                hashcode = Math.abs(tempKey.hashCode());
            }
            int chainsIndex = hashcode % largerMap.length;

            //if there is no array map at the index
            //create a new empty array map at the index
            if (largerMap[chainsIndex] == null) {
                largerMap[chainsIndex] = createChain(chainCapacity);
            }
            //put the key value pair
            largerMap[chainsIndex].put(tempKey, tempValue);
        }
        //sets the chained hash map to the larger map
        this.chains = largerMap;

    }
}
