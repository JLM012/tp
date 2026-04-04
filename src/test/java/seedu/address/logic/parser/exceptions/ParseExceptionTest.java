package seedu.address.logic.parser.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for ParseException.
 */
public class ParseExceptionTest {

    private static final String TEST_MESSAGE = "Invalid command format";
    private static final Throwable TEST_CAUSE = new RuntimeException("Root cause");

    @Test
    public void constructor_withMessage_createsParseException() {
        ParseException exception = new ParseException(TEST_MESSAGE);
        assertEquals(TEST_MESSAGE, exception.getMessage());
    }

    @Test
    public void constructor_withMessageAndCause_createsParseException() {
        ParseException exception = new ParseException(TEST_MESSAGE, TEST_CAUSE);
        assertEquals(TEST_MESSAGE, exception.getMessage());
        assertEquals(TEST_CAUSE, exception.getCause());
    }
}


