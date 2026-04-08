package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code MembershipId} matches any of the keywords given.
 */
public class MembershipIdContainsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public MembershipIdContainsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
            .anyMatch(keyword -> person.getMembershipId().toString().equals(keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MembershipIdContainsPredicate)) {
            return false;
        }

        MembershipIdContainsPredicate otherMembershipIdContainsPredicate = (MembershipIdContainsPredicate) other;
        return keywords.equals(otherMembershipIdContainsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
