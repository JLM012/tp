package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class ExpiryDateContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        ExpiryDateContainsKeywordsPredicate firstPredicate =
                new ExpiryDateContainsKeywordsPredicate(Collections.singletonList("2026-12-12"));
        ExpiryDateContainsKeywordsPredicate secondPredicate =
                new ExpiryDateContainsKeywordsPredicate(Collections.singletonList("2026-12-13"));

        // same object -> true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> true
        ExpiryDateContainsKeywordsPredicate firstPredicateCopy =
                new ExpiryDateContainsKeywordsPredicate(Collections.singletonList("2026-12-12"));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different type -> false
        assertFalse(firstPredicate.equals(1));

        // null -> false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_expiryDateContainsKeywords_returnsTrue() {
        // one keyword matches
        ExpiryDateContainsKeywordsPredicate predicate =
                new ExpiryDateContainsKeywordsPredicate(Collections.singletonList("2026-12-12"));
        assertTrue(predicate.test(new PersonBuilder()
                .withMembershipExpiryDate("2026-12-12")
                .build()));

        // multiple keywords, one matches
        predicate = new ExpiryDateContainsKeywordsPredicate(
                Arrays.asList("2026-12-13", "2026-12-12"));
        assertTrue(predicate.test(new PersonBuilder()
                .withMembershipExpiryDate("2026-12-12")
                .build()));
    }

    @Test
    public void test_expiryDateDoesNotContainKeywords_returnsFalse() {
        // empty keyword list
        ExpiryDateContainsKeywordsPredicate predicate =
                new ExpiryDateContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withMembershipExpiryDate("2026-12-12")
                .build()));

        // non-matching keyword
        predicate = new ExpiryDateContainsKeywordsPredicate(Collections.singletonList("2026-12-13"));
        assertFalse(predicate.test(new PersonBuilder()
                .withMembershipExpiryDate("2026-12-12")
                .build()));

        // partial match should fail
        predicate = new ExpiryDateContainsKeywordsPredicate(Collections.singletonList("12-12"));
        assertFalse(predicate.test(new PersonBuilder()
                .withMembershipExpiryDate("2026-12-12")
                .build()));
    }

    @Test
    public void toStringMethod() {
        ExpiryDateContainsKeywordsPredicate predicate =
                new ExpiryDateContainsKeywordsPredicate(Arrays.asList("2026-12-12", "2026-12-13"));

        String expected = ExpiryDateContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=[2026-12-12, 2026-12-13]}";
        assertEquals(expected, predicate.toString());
    }
}
