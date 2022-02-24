package priorityqueues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @see ExtrinsicMinPQ
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    static final int START_INDEX = 0;
    List<PriorityNode<T>> items;
    HashMap<T, Integer> table; // hashmap used to allow fast access of the data stored in the array list
    private int size;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        table = new HashMap<>();
        size = 0;
    }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.

    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int beingSwapped, int swappedWith) {
        PriorityNode<T> temp = items.get(beingSwapped);
        table.put(items.get(beingSwapped).getItem(), swappedWith);
        table.put(items.get(swappedWith).getItem(), beingSwapped);
        items.set(beingSwapped, items.get(swappedWith));
        items.set(swappedWith, temp);
    }

    private void percolateDown(int index) {
        //the first if statement checks if the size of the array is greater than (2 * index + 2)
        //without this check, we might call items.get() on an index which is greater than size
        //which throws an index out of bounds exception.
        if (items.size() > (2 * index + 2)) {
            if (items.get(index).getPriority() > items.get(2 * index + 1).getPriority() ||
                items.get(index).getPriority() > items.get(2 * index + 2).getPriority()) {
                if (items.get(2 * index + 1).getPriority() < items.get(2 * index + 2).getPriority()) {
                    swap(2 * index + 1, index);
                    percolateDown(2 * index + 1);
                } else {
                    swap(2 * index + 2, index);
                    percolateDown(2 * index + 2);
                }
            }
            //the first if statement checks if the size of the array is greater than (2 * index + 1)
            //without this check, we might call items.get() on an index which is greater than size
            //which throws an index out of bounds exception.
        } else if (items.size() > (2 * index + 1)) {
            if (items.get(index).getPriority() > items.get(2 * index + 1).getPriority()) {
                swap(2 * index + 1, index);
                percolateDown(2 * index + 1);
            }
        }
    }

    private void percolateUp(int index) {
        if (index != 0 && items.get((index - 1) / 2).getPriority() > items.get(index).getPriority()) {
            swap((index - 1) / 2, index);
            percolateUp((index - 1) / 2);
        }
    }

    @Override
    public void add(T item, double priority) {
        //since only unique keys are allowed, if the arraylist already contains the key
        //throw an illegal argument exception

        if (contains(item)) {
            throw new IllegalArgumentException();
        }

        PriorityNode<T> newNode = new PriorityNode<>(item, priority);
        items.add(newNode);
        size++;
        table.put(item, items.size() - 1);

        //adds the element to the hashmap
        //key = item, value = index of item in the array list
        //size++;
        percolateUp(size - 1);

    }

    @Override
    public boolean contains(T item) {
        return table != null && table.containsKey(item);
    }

    @Override
    public T peekMin() {
        if (items.isEmpty()) {
            //return null;
            throw new NoSuchElementException();
        } else {
            return items.get(0).getItem();
        }
    }

    @Override
    public T removeMin() {
        if (items.isEmpty()) {
            //return null;
            throw new NoSuchElementException();
        } else {
            T value = items.get(0).getItem(); // store min value to be returned
            items.set(0, items.get(items.size() - 1)); // last value put at front of arraylist
            items.remove(size() - 1); // remove last node
            table.remove(value); //remove value from the hashmap
            size--;
            //if there is at least one child
            if (size != 1) {
                percolateDown(0);
            }
            return value;
        }
    }

    @Override
    public void changePriority(T item, double priority) {
        if (contains(item)) {
            int index = table.get(item);
            PriorityNode<T> node = items.get(index);
            if (priority > node.getPriority()) {
                node.setPriority(priority);
                percolateDown(index);
            } else {
                node.setPriority(priority);
                percolateUp(index);
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int size() {
        return size;
    }
}
