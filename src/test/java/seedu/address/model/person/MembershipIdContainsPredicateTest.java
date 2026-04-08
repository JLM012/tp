package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class MembershipIdContainsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("1000");
        List<String> secondPredicateKeywordList = Arrays.asList("1000", "1001");

        MembershipIdContainsPredicate firstPredicate =
                new MembershipIdContainsPredicate(firstPredicateKeywordList);
        MembershipIdContainsPredicate secondPredicate =
                new MembershipIdContainsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MembershipIdContainsPredicate firstPredicateCopy =
                new MembershipIdContainsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_membershipIdContainsKeywords_returnsTrue() {
        // One keyword
        MembershipIdContainsPredicate predicate =
                new MembershipIdContainsPredicate(Collections.singletonList("1000"));
        assertTrue(predicate.test(new PersonBuilder().withMembershipId(1000).build()));

        // Multiple keywords
        predicate = new MembershipIdContainsPredicate(Arrays.asList("1000", "1001"));
        assertTrue(predicate.test(new PersonBuilder().withMembershipId(1001).build()));

        // Only one matching keyword
        predicate = new MembershipIdContainsPredicate(Arrays.asList("1001", "1002"));
        assertTrue(predicate.test(new PersonBuilder().withMembershipId(1002).build()));
    }

    @Test
    public void test_membershipIdDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MembershipIdContainsPredicate predicate = new MembershipIdContainsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withMembershipId(1000).build()));

        // Non-matching keyword
        predicate = new MembershipIdContainsPredicate(Arrays.asList("9999"));
        assertFalse(predicate.test(new PersonBuilder().withMembershipId(1000).build()));

        // Keywords match other fields but does not match membership id
        predicate = new MembershipIdContainsPredicate(Arrays.asList("Alice", "91234567", "alice@email.com"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withMembershipId(1000).build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("1000", "1001");
        MembershipIdContainsPredicate predicate = new MembershipIdContainsPredicate(keywords);

        String expected = MembershipIdContainsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
