import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Grading Tests for frq: AdditionPattern
 * 
 * Assumptions:
 *  - Class name: AdditionPattern
 *  - Constructor : AdditionPattern(int start , int step )
 *  - Methods: 
 *      int currentNumber()
 *      void next()
 *      void prev() // nothing if moving below start  
 */

public class AdditionPatternTest {
    @Test
    @DisplayName("[+1] currentNumber returns starting value")
    void currentNumberEqualsStartInitially() {
        AdditionPattern plus3 = new AdditionPattern(2, 3);
        assertTrue(2, plus3.currentNumber);
    }

    @Test
    @DisplayName("[+1] next and prev update by step")
    void nextAndPrev_updateByStep() {
        AdditionPattern plus3 = new AdditionPattern(2, 3);

        // next: 2 -> 5 -> 8 -> 11
        plus3.next();
        assertEquals(5, plus3.currentNumber(), "After 1 next, current should be start + step");
        plus3.next();
        assertEquals(8, plus3.currentNumber(), "After 2 next, current should be start + 2 * step");
        plus3.next();
        assertEquals(11, plus3.currentNumber(), "After 3 next, current should be start + 3 * step");

        // prev: 11 -> 8
        plus3.prev();
        assertEquals(8, plus3.currentNumber(), "prev should subtract one step");
    }

    @Test
    @DisplayName("[+1] prev does not move below start")
    void prev_doesNotGoBelowStart() {
        AdditionPattern plus3 = new AdditionPattern(2, 3);

        // no change since 2 cant be moved back 3
        plus3.prev();
        assertEquals(2, plus3.currentNumber(), "prev at start, should be no movement");

        plus3.next(); // 5
        plus3.next(); // 8
        plus3.prev(); // 5
        plus3.prev(); // 2
        assertEquals(2, plus3.currentNumber(), "current should be at start");

        plus3.prev();
        plus3.prev();
        assertEquals(2, plus3.currentNumber(), "prev doesn't move below start");
    }
}
