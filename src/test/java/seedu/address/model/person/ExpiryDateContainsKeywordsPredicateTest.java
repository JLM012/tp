package seedu.address.model.person;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

import static org.junit.jupiter.api.Assertions.*;

public class ExpiryDateContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        ExpiryDateContainsKeywordsPredicate firstPredicate =
                new ExpiryDateContainsKeywordsPredicate(Collections.singletonList("12-12-2026"));
        ExpiryDateContainsKeywordsPredicate secondPredicate =
                new ExpiryDateContainsKeywordsPredicate(Collections.singletonList("13-12-2026"));

        // same object -> true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> true
        ExpiryDateContainsKeywordsPredicate firstPredicateCopy =
                new ExpiryDateContainsKeywordsPredicate(Collections.singletonList("12-12-2026"));
        assertEquals(firstPredicate, firstPredicateCopy);

        // different type -> false
        assertNotEquals(1, firstPredicate);

        // null -> false
        assertNotEquals(null, firstPredicate);

        // different keywords -> false
        assertNotEquals(firstPredicate, secondPredicate);
    }

    @Test
    public void test_expiryDateContainsKeywords_returnsTrue() {
        // one keyword matches
        ExpiryDateContainsKeywordsPredicate predicate =
                new ExpiryDateContainsKeywordsPredicate(Collections.singletonList("12-12-2026"));
        assertTrue(predicate.test(new PersonBuilder()
                .withMembershipExpiryDate("12-12-2026")
                .build()));

        // multiple keywords, one matches
        predicate = new ExpiryDateContainsKeywordsPredicate(
                Arrays.asList("13-12-2026", "12-12-2026"));
        assertTrue(predicate.test(new PersonBuilder()
                .withMembershipExpiryDate("12-12-2026")
                .build()));
    }

    @Test
    public void test_expiryDateDoesNotContainKeywords_returnsFalse() {
        // empty keyword list
        ExpiryDateContainsKeywordsPredicate predicate =
                new ExpiryDateContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withMembershipExpiryDate("12-12-2026")
                .build()));

        // non-matching keyword
        predicate = new ExpiryDateContainsKeywordsPredicate(Collections.singletonList("13-12-2026"));
        assertFalse(predicate.test(new PersonBuilder()
                .withMembershipExpiryDate("12-12-2026")
                .build()));

        // partial match should fail
        predicate = new ExpiryDateContainsKeywordsPredicate(Collections.singletonList("12-12"));
        assertFalse(predicate.test(new PersonBuilder()
                .withMembershipExpiryDate("12-12-2026")
                .build()));
    }
}
