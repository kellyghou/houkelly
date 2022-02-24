package deques;

/**
 * @see Deque
 */
public class LinkedDeque<T> extends AbstractDeque<T> {
    private int size;
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    Node<T> front;
    Node<T> back;


    // Feel free to add any additional fields you may need, though.

    public LinkedDeque() {
        size = 0;
        Node<T> frontNode = new Node<>(null);
        Node<T> backNode = new Node<>(null);
        front = frontNode;
        back = backNode;
        front.next = backNode; //in  an empty deque, front.next needs to reference the back node
        back.prev = frontNode; // in an empty deque, back.prev needs to reference the front node



    }

    public void addFirst(T item) {
        size += 1;
        Node<T> newNode = new Node<>(item);
        if (front.next == null) { //empty deque
            addToEmptyDeque(newNode);
        } else {
            newNode.next = front.next; // puts new node into deque
            newNode.prev = front;
            front.next.prev = newNode; // rearranges original first node
            front.next = newNode; // rearranges front sentinel node
        }
    }

    public void addLast(T item) {
        size += 1;
        Node<T> newNode = new Node<>(item);
        if (front.next == null) { //empty deque
            addToEmptyDeque(newNode);
        } else {
            newNode.prev = back.prev; // puts new node into deque
            newNode.next = back;
            back.prev.next = newNode; // rearranges original last node
            back.prev = newNode; // rearranges back sentinel node
        }
    }

    // adds a node to empty deque
    private void addToEmptyDeque(Node<T> newNode) {
        size+= 1; //increment the size
        front.next = newNode;
        newNode.prev = front;
        newNode.next = back;
        back.prev = newNode;
    }

    // NOT TESTED YET
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        // size -= 1;
        if (size == 1) {
            size -= 1; //I changed it to decrement the size only after entering the conditinal
            return removeOnlyNode();
        } else {
            size -= 1;
            Node<T> removedNode = front.next; // stores first node in deque
            front.next = front.next.next; // rearranges front sentinel node
            front.next.prev = front; // designates another node to be the first node in deque
            return removedNode.value; // return item in removed node
        }

    }

    // NOT TESTED YET
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        //size -= 1;
        if (size == 1) {
            size -= 1;
            return removeOnlyNode();
        } else {
            size -= 1;
            Node<T> removedNode = back.prev; // stores last node in deque
            back.prev = back.prev.prev; // rearranges back sentinel node
            back.prev.next = back; // designates another node to be the last node in deque
            return removedNode.value; // return item in removed node
        }

    }

    // remove the last node left in a one node linked deque
    private T removeOnlyNode() {
        Node<T> removedNode = front.next; // store node to be removed
        front.next = back;
        back.prev = front; // empty deque now
        //size -= 1; //decrement size
        return removedNode.value; // return item in removed node
    }

    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }
        Node<T> curr;
        if (index < size / 2) {
            curr = front.next;
            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }
        } else {
            curr = back.prev;
            for (int i = size - 1; i > index; i--) {
                curr = curr.prev;
            }
        }
        return curr.value;
    }
    public int size() { return size; }
}
