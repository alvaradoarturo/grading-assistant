package grader.labs.AdditionPattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

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

    private Object newAdditionPattern(int start, int step) throws Exception {
        Class<?> cls = Class.forName("AdditionPatter");
        Constructor<?> ctor = cls.getConstructor(int.class, int.class);
        return ctor.newInstance(start, step);
    }

    private int currentNumber(Object ap) throws Exception {
        Method m = ap.getClass().getMethod("currentNumber");
        return (int) m.invoke(ap);
    }

    private void next(Object ap) throws Exception {
        Method m = ap.getClass().getMethod("next");
        m.invoke(ap);
    }

    private void prev(Object ap) throws Exception {
        Method m = ap.getClass().getMethod("prev");
        m.invoke(ap);
    }

    @Test
    @DisplayName("[+1] currentNumber returns starting value")
    void currentNumberEqualsStartInitially() throws Exception {
        Object plus3 = newAdditionPattern(2, 3);
        assertEquals(2, currentNumber(plus3));
    }

    @Test
    @DisplayName("[+1] next and prev update by step")
    void nextAndPrev_updateByStep() throws Exception {
        Object plus3 = newAdditionPattern(2, 3);

        // next: 2 -> 5 -> 8 -> 11
        next(plus3);
        assertEquals(5, currentNumber(plus3), "After 1 next, current should be start + step");
        next(plus3);
        assertEquals(8, currentNumber(plus3), "After 2 next, current should be start + 2 * step");
        next(plus3);
        assertEquals(11, currentNumber(plus3), "After 3 next, current should be start + 3 * step");

        // prev: 11 -> 8
        prev(plus3);
        assertEquals(8, currentNumber(plus3), "prev should subtract one step");
    }

    @Test
    @DisplayName("[+1] prev does not move below start")
    void prev_doesNotGoBelowStart() throws Exception {
        Object plus3 = newAdditionPattern(2, 3);

        // no change since 2 cant be moved back 3
        prev(plus3);
        assertEquals(2, currentNumber(plus3), "prev at start, should be no movement");

        next(plus3); // 5
        next(plus3); // 8
        prev(plus3); // 5
        prev(plus3); // 2
        assertEquals(2, currentNumber(plus3), "current should be at start");

        prev(plus3);
        prev(plus3);
        assertEquals(2, currentNumber(plus3), "prev doesn't move below start");
    }
}
