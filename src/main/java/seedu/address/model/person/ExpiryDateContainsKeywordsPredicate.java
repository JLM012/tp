package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code MembershipExpiryDate} matches any of the keywords given.
 */
public class ExpiryDateContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ExpiryDateContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getMembershipExpiryDate().toString().equals(keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ExpiryDateContainsKeywordsPredicate)) {
            return false;
        }

        ExpiryDateContainsKeywordsPredicate otherPredicate = (ExpiryDateContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
