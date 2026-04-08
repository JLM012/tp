package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

class PostalCodeContainsKeywordsPredicateTest {

    @Test
    void equals() {
        PostalCodeContainsKeywordsPredicate firstPredicate =
                new PostalCodeContainsKeywordsPredicate(Collections.singletonList("123456"));
        PostalCodeContainsKeywordsPredicate secondPredicate =
                new PostalCodeContainsKeywordsPredicate(Arrays.asList("654321", "111111"));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PostalCodeContainsKeywordsPredicate firstPredicateCopy =
                new PostalCodeContainsKeywordsPredicate(Collections.singletonList("123456"));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    void test_postalCodeMatchesKeyword_returnsTrue() {
        PostalCodeContainsKeywordsPredicate predicate =
                new PostalCodeContainsKeywordsPredicate(Collections.singletonList("123456"));

        Person person = new PersonBuilder()
                .withAddress("Blk 12 Jimai Street 28, #12-28, Singapore 123456")
                .build();

        assertTrue(predicate.test(person));
    }

    @Test
    void test_postalCodeMatchesOneOfMultipleKeywords_returnsTrue() {
        PostalCodeContainsKeywordsPredicate predicate =
                new PostalCodeContainsKeywordsPredicate(Arrays.asList("999999", "123456"));

        Person person = new PersonBuilder()
                .withAddress("Blk 12 Jimai Street 28, #12-28, Singapore 123456")
                .build();

        assertTrue(predicate.test(person));
    }

    @Test
    void test_postalCodeDoesNotMatchKeyword_returnsFalse() {
        PostalCodeContainsKeywordsPredicate predicate =
                new PostalCodeContainsKeywordsPredicate(Collections.singletonList("654321"));

        Person person = new PersonBuilder()
                .withAddress("Blk 12 Jimai Street 28, #12-28, Singapore 123456")
                .build();

        assertFalse(predicate.test(person));
    }

    @Test
    void test_emptyKeywordList_returnsFalse() {
        PostalCodeContainsKeywordsPredicate predicate =
                new PostalCodeContainsKeywordsPredicate(Collections.emptyList());

        Person person = new PersonBuilder()
                .withAddress("Blk 12 Jimai Street 28, #12-28, Singapore 123456")
                .build();

        assertFalse(predicate.test(person));
    }
}
