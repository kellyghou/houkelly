package deques;

import org.junit.jupiter.api.Test;

public class LinkedDequeTests extends BaseDequeTests {
    public static <T> LinkedDequeAssert<T> assertThat(LinkedDeque<T> deque) {
        return new LinkedDequeAssert<>(deque);
    }

    @Override
    protected <T> Deque<T> createDeque() {
        return new LinkedDeque<>();
    }

    @Override
    protected <T> void checkInvariants(Deque<T> deque) {
        // cast so we can use the LinkedDeque-specific version of assertThat defined above
        assertThat((LinkedDeque<T>) deque).isValid();
    }

    // You can write additional tests here if you only want them to run for LinkedDequeTests and not ArrayDequeTests
    @Test
    void addFirst_onEmptyDeque_hasCorrectInvariants() {
        Deque<Integer> temp = createDeque();
        temp.addFirst(3);
        checkInvariants(temp);
    }

    @Test
    void addLast_onEmptyDeque_hasCorrectInvariants() {
        Deque<Integer> temp = createDeque();
        temp.addLast(5);
        checkInvariants(temp);
    }

    @Test
    void addFirstAndLast_severalMethodCalls_hasCorrectInvariants() {
        Deque<Integer> temp = createDeque();
        temp.addFirst(4);
        temp.addFirst(5);
        temp.addLast(3);
        temp.addLast(60);
        temp.addLast(45);
        temp.addLast(-1);
        temp.addFirst(-101);
        temp.addLast(505);
        temp.addFirst(409);
        temp.addLast(-75);
        checkInvariants(temp);
    }

    @Test
    void removeFirst_onMultipleNodeDeque_returnsCorrectItem() {
        Deque<Integer> temp = createDeque();
        temp.addFirst(4);
        temp.addFirst(5);
        temp.addFirst(6);
        temp.addLast(3);
        assertThat(temp.removeFirst()).isEqualTo(6);
    }

    // NOT FINISHED TEST
    @Test
    void removeFirst_onMultipleNodeDeque_successfullyRemovesFromDeque() {
        Deque<Integer> temp = createDeque();
        temp.addFirst(4);
        temp.addFirst(5);
        temp.addFirst(6);
        temp.addLast(3);
        temp.removeFirst();
        Deque<Integer> expectedResult = createDeque();
        expectedResult.addFirst(3);
        expectedResult.addFirst(4);
        expectedResult.addFirst(5);
        // assertThat(expectedResult).isEqualTo(temp); this doesn't work since they contain same values but not same object. ask TA
    }

    @Test
    void get_onNonexistantIndex_returnsNull() {
        Deque<Integer> temp = createDeque();
        temp.addFirst(4);
        temp.addFirst(5);
        temp.addFirst(6);
        temp.addLast(3);
        temp.addFirst(-1);
        assertThat(temp.get(-1)).isEqualTo(null);
    }

    @Test
    void get_nearBack_returnsCorrectItem() {
        Deque<Integer> temp = createDeque();
        temp.addFirst(4);
        temp.addFirst(5);
        temp.addLast(3);
        temp.addLast(60);
        temp.addLast(45);
        temp.addLast(-1);
        temp.addFirst(-101);
        temp.addLast(505);
        temp.addFirst(409);
        temp.addLast(-75);
        assertThat(temp.get(7)).isEqualTo(-1);
    }

    @Test
    void get_fromVeryBack_returnsCorrectItem() {
        Deque<Integer> temp = createDeque();
        temp.addFirst(4);
        temp.addFirst(5);
        temp.addLast(3);
        temp.addLast(60);
        temp.addLast(45);
        temp.addLast(-1);
        temp.addFirst(-101);
        temp.addLast(505);
        temp.addFirst(409);
        temp.addLast(-75);
        assertThat(temp.get(9)).isEqualTo(-75);
    }

    @Test
    void get_fromMiddleNearBack_returnsCorrectItem() {
        Deque<Integer> temp = createDeque();
        temp.addFirst(0);
        temp.addLast(1);
        temp.addLast(2);
        temp.addLast(3);
        assertThat(temp.get(2)).isEqualTo(2);
    }
}
