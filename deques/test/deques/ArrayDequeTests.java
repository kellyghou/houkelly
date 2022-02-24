package deques;

import org.junit.jupiter.api.Test;

public class ArrayDequeTests extends BaseDequeTests {
    @Override
    protected <T> Deque<T> createDeque() {
        return new ArrayDeque<>();
    }

    @Override
    protected <T> void checkInvariants(Deque<T> deque) {
        // do nothing
    }

    // You can write additional tests here if you only want them to run for ArrayDequeTests and not LinkedDequeTests
    // @Test
    // void resizeUsingAddFirst_onFullArray_printsCorrectLargerArray() {
    //     Deque<Integer> temp = createDeque();
    //     temp.addFirst(5);
    //     temp.addFirst(3);
    //     temp.addFirst(45);
    //     temp.addFirst(300);
    //     temp.addFirst(2);
    //     temp.addFirst(0);
    //     temp.addFirst(13);
    //     temp.addFirst(80);
    //     temp.addFirst(-1);// resizes
    //     temp.addFirst(-2);
    //     temp.addFirst(-4);
    //     temp.addFirst(-6);
    //     temp.addFirst(-8);
    //     temp.addFirst(-10);
    //     temp.addFirst(-12);
    //     temp.addFirst(-14);
    //     temp.addFirst(5);
    //     temp.addFirst(3);
    //     temp.addFirst(45);
    //     temp.addFirst(300);
    //     temp.addFirst(2);
    //     temp.addFirst(0);
    //     temp.addFirst(13);
    //     temp.addFirst(80);
    //     temp.addFirst(-1);// resizes
    //     temp.addFirst(-2);
    //     temp.addFirst(-4);
    //     temp.addFirst(-6);
    //     temp.addFirst(-8);
    //     temp.addFirst(-10);
    //     temp.addFirst(-12);
    //     temp.addFirst(-14);
    //     System.out.println(temp);
    //     System.out.println(temp.size());
    // }

    @Test
    void confusingTest() {
        Deque<Integer> deque = createDeque();
        deque.addFirst(0);
        deque.get(0);
        deque.addLast(1);
        deque.get(1);
        deque.addFirst(-1);
        deque.addLast(2);
        deque.get(3);
        deque.addLast(3);
        deque.addLast(4);
        deque.removeFirst();
        deque.addFirst(-1);
        deque.get(0);
        deque.addLast(5);
        deque.addFirst(-2);
        deque.addFirst(-3);
        deque.removeFirst();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        int actual = deque.removeLast();
        System.out.println(actual);
    }

    @Test
    void resizeUsingAddFirst_onFullArray_printsCorrectLargerArray() {
        //i changed the testing numbers to numbers in order
        //so its easier to spot where the mistake happened
        Deque<Integer> temp = createDeque();
        temp.addFirst(0);
        temp.addFirst(1);
        temp.addFirst(2);
        temp.addFirst(3);
        temp.addFirst(4);
        temp.addFirst(5);
        temp.addFirst(6);
        temp.addFirst(7);
        temp.addFirst(8);// resizes
        temp.addFirst(9);
        temp.addFirst(10);
        temp.addFirst(11);
        temp.addFirst(12);
        temp.addFirst(13);
        temp.addFirst(14);
        temp.addFirst(15);
        temp.addFirst(16);
        temp.addFirst(17);
        temp.addFirst(18);// resizes
        temp.addFirst(19);
        temp.removeLast();
        temp.removeLast();
        temp.removeLast();
        temp.removeLast();
        temp.removeLast();
        temp.removeLast();
        temp.removeLast();
        temp.removeLast();
        temp.removeLast();
        temp.removeLast();
        temp.removeLast();
        temp.removeLast();
        temp.removeLast();
        temp.removeLast();
        temp.removeLast();
        temp.removeLast();
        temp.removeLast();
        temp.removeLast();
        temp.removeLast();
        temp.removeLast();
    }

    // @Test
    // void getEach_afterAddManyToSameSide_returnsCorrectItems() {
    //     Deque<Integer> temp = createDeque();
    //     temp.addFirst(5);
    //     temp.addFirst(3);
    //     temp.addFirst(45);
    //     temp.addFirst(300);
    //     temp.addFirst(2);
    //     temp.addFirst(0);
    //     temp.addFirst(13);
    //     temp.addFirst(80);
    //     temp.addFirst(-1);// resizes
    //     temp.addFirst(-2);
    //     temp.get(0);
    //     temp.get(1);
    //     temp.get(2);
    //     temp.get(3);
    //     temp.get(4);
    //     temp.get(5);
    //     temp.get(6);
    //     temp.get(7);
    //     temp.get(8);
    //     temp.get(9);
    //     temp.get(10);
    // }

    @Test
    void getEach_afterAddManyToSameSide_returnsCorrectItems() {
        Deque<Integer> temp = createDeque();
        temp.addFirst(0);
        temp.addFirst(1);
        temp.addFirst(2);
        temp.addFirst(3);
        temp.addFirst(4);
        temp.addFirst(5);
        temp.addFirst(6);
        temp.addFirst(7);
        temp.addFirst(8);
        temp.addFirst(9);
        temp.addFirst(10);
        temp.addFirst(11);
        temp.addFirst(12);
        temp.addFirst(13);
        temp.addFirst(14);
        temp.addFirst(15);
        temp.addFirst(16);
        temp.addFirst(17);
        temp.addFirst(18);
        temp.addFirst(19);
        System.out.print(temp.get(0));
        System.out.print(temp.get(1));
        System.out.print(temp.get(2));
        System.out.print(temp.get(3));
        System.out.print(temp.get(4));
        System.out.print(temp.get(5));
        System.out.print(temp.get(6));
        System.out.print(temp.get(7));
        System.out.print(temp.get(8));
        System.out.print(temp.get(9));
        System.out.print(temp.get(10));
        System.out.print(temp.get(11));
        System.out.print(temp.get(12));
        System.out.print(temp.get(13));
        System.out.print(temp.get(14));
        System.out.print(temp.get(15));
        System.out.print(temp.get(16));
        System.out.print(temp.get(17));
        System.out.print(temp.get(18));
        System.out.print(temp.get(19));
    }
}
