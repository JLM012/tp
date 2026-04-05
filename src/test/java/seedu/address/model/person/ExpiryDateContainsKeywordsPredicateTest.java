package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class ExpiryDateContainsKeywordsPredicateTest {

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
