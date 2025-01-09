import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class CircularBufferTest {

    @Test
    public void shouldBeAbleToCreateBuffer() throws Exception {
        new CircularBuffer(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfInvalidSize() throws Exception {
        new CircularBuffer(0);
    }

    @Test
    public void shouldHaveCorrectMaxSize() throws Exception {
        assertEquals(5, new CircularBuffer(5).maxSize());
    }

    @Test
    public void shouldBeCreatedEmpty() throws Exception {
        assertEquals(0, new CircularBuffer(6).size());
    }

    @Test
    public void shouldHaveCorrectSizeWhenAddingElements() throws Exception {
        CircularBuffer buffer = new CircularBuffer(5);
        for (int i = 0; i < 5; i++) {
            assertEquals(i, buffer.size());
            buffer.put(i);
        }
    }

    @Test
    public void shouldHaveCorrectSizeWhenRemovingElements() throws Exception {
        CircularBuffer buffer = new CircularBuffer(5);
        for (int i = 0; i < 5; i++) {
            buffer.put(i);
        }
        for (int i = 5; i > 0; i--) {
            assertEquals(i, buffer.size());
            buffer.take();
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionIfTakingFromEmptyBuffer() throws Exception {
        CircularBuffer buffer = new CircularBuffer(5);
        for (int i = 0; i < 3; i++) {
            buffer.put(i);
        }
        for (int i = 5; i >= 0; i--) {
            buffer.take();
        }
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionIfFull() throws Exception {
        CircularBuffer buffer = new CircularBuffer(5);
        for (int i = 0; i < 6; i++) {
            buffer.put(i);
        }
    }

    @Test
    public void shouldHaveCorrectSizeWhenWrapping() throws Exception {
        CircularBuffer buffer = new CircularBuffer(5);
        for (int i = 0; i < 3; i++) {
            buffer.put(i);
        }
        for (int i = 0; i < 3; i++) {
            buffer.take();
        }
        for (int i = 0; i < 3; i++) {
            assertEquals(i, buffer.size());
            buffer.put(i);
        }
    }

    @Test
    public void shouldReturnCorrectElement() throws Exception {
        CircularBuffer buffer = new CircularBuffer(5);
        for (int i = 0; i < 5; i++) {
            buffer.put(i);
        }
        for (int i = 0; i < 5; i++) {
            assertEquals(i, buffer.take());
        }
    }

    @Test
    public void shouldReturnCorrectElementWhenWrapping() throws Exception {
        CircularBuffer buffer = new CircularBuffer(5);
        for (int i = 0; i < 4; i++) {
            buffer.put(i);
        }
        for (int i = 0; i < 4; i++) {
            buffer.take();
        }
        for (int i = 0; i < 4; i++) {
            buffer.put(i);
        }
        for (int i = 0; i < 4; i++) {
            assertEquals(i, buffer.take());
        }
    }
}
