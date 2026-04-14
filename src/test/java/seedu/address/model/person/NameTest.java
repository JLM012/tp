package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters
        assertFalse(Name.isValidName("12345")); // numbers only
        assertFalse(Name.isValidName("peter the 2nd")); // contains numbers
        assertFalse(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // contains numbers

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("Alice")); // single word
    }

    @Test
    public void isValidName_withPunctuation() {
        // valid names with punctuation (must have letter before and after punctuation)
        assertTrue(Name.isValidName("Mary-Jane")); // hyphen with letters before and after
        assertTrue(Name.isValidName("O'Brien")); // apostrophe with letters before and after
        assertTrue(Name.isValidName("Dr. Lim")); // period with space and letters
        assertTrue(Name.isValidName("Thor s/o Odin")); // forward slash with space and letters
        assertTrue(Name.isValidName("小明")); // international characters

        // invalid punctuation - must have punctuation between letters properly
        assertFalse(Name.isValidName("Mary--Jane")); // double punctuation
        assertFalse(Name.isValidName("'Brien")); // starts with punctuation
        assertFalse(Name.isValidName("Mary'")); // ends with punctuation
        assertFalse(Name.isValidName(".Dr")); // starts with period
        assertFalse(Name.isValidName("Dr.")); // ends with period after no space
    }

    @Test
    public void isValidName_lengthConstraints() {
        // minimum length
        assertTrue(Name.isValidName("a")); // 1 character
        // maximum length
        assertTrue(Name.isValidName("a".repeat(70))); // 70 characters
        // exceeds maximum length
        assertFalse(Name.isValidName("a".repeat(71))); // 71 characters
    }

    @Test
    public void constructor_whitespaceNormalization() {
        // extra spaces are normalized to single space
        Name name = new Name("  John   Doe  ");
        assertEquals("John Doe", name.fullName);
    }

    @Test
    public void getNormalizedFullName() {
        Name name = new Name("John Doe");
        // normalized name should be lowercase
        assertEquals("john doe", name.getNormalizedFullName());

        Name nameWithMixedCase = new Name("Mary-Jane Smith");
        assertEquals("mary-jane smith", nameWithMixedCase.getNormalizedFullName());
    }

    @Test
    public void getNormalizedFullName_preservesStructure() {
        // normalization should preserve punctuation
        Name nameWithApostrophe = new Name("O'Brien");
        assertEquals("o'brien", nameWithApostrophe.getNormalizedFullName());

        Name nameWithHyphen = new Name("Jean-Claude");
        assertEquals("jean-claude", nameWithHyphen.getNormalizedFullName());
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertEquals(new Name("Valid Name"), name);

        // same object -> returns true
        assertEquals(name, name);

        // null -> returns false
        org.junit.jupiter.api.Assertions.assertNotEquals(name, null);

        // different types -> returns false
        org.junit.jupiter.api.Assertions.assertNotEquals(name, 5.0f);

        // different values -> returns false
        org.junit.jupiter.api.Assertions.assertNotEquals(name, new Name("Other Valid Name"));
    }
}
