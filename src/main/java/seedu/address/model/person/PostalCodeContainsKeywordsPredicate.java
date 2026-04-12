package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s Postal Code (extracted from Address) matches any of the keywords given.
 */
public class PostalCodeContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PostalCodeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        String normalizedAddress = person.getAddress().getNormalizedValue();
        String postalCode = normalizedAddress.substring(normalizedAddress.length() - 6);

        return keywords.stream()
                .anyMatch(keyword -> postalCode.equals(keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PostalCodeContainsKeywordsPredicate)) {
            return false;
        }

        PostalCodeContainsKeywordsPredicate otherPredicate = (PostalCodeContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
