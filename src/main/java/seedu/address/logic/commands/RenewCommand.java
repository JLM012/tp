package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDate;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.MembershipExpiryDate;
import seedu.address.model.person.MembershipId;
import seedu.address.model.person.Person;

/**
 * Renews a member's membership by adding the specified number of days to expiry.
 */
public class RenewCommand extends Command {

    public static final String COMMAND_WORD = "renew";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Renews a member's membership by Membership ID.\n"
            + "Parameters: id/MEMBERSHIP_ID d/DAYS_TO_ADD\n"
            + "(MEMBERSHIP_ID must be 4-digit integers from 1000 to 9999)\n"
            + "Example: " + COMMAND_WORD + " id/1001 d/7";

    public static final String MESSAGE_RENEW_PERSON_SUCCESS =
            "Renewed membership for: %1$s\nOld expiry date: %2$s\nNew expiry date: %3$s";

    public static final String MESSAGE_INVALID_DAYS =
            "Days to add must be a positive integer.";

    private final MembershipId membershipId;
    private final int daysToAdd;

    /**
     * Creates a RenewCommand to renew a member with the given {@code membershipId}
     * by {@code daysToAdd} days.
     */
    public RenewCommand(MembershipId membershipId, int daysToAdd) {
        requireNonNull(membershipId);
        this.membershipId = membershipId;
        this.daysToAdd = daysToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> allPersons = model.getAddressBook().getPersonList();
        Person personToRenew = null;

        for (Person person : allPersons) {
            if (person.getMembershipId().equals(membershipId)) {
                personToRenew = person;
                break;
            }
        }

        if (personToRenew == null) {
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, membershipId));
        }

        LocalDate today = LocalDate.now();
        LocalDate currentExpiry = personToRenew.getMembershipExpiryDate().value;

        // Renewal starts from whichever is later: current expiry date or today.
        LocalDate baseDate = currentExpiry.isAfter(today) ? currentExpiry : today;
        LocalDate newExpiryDate = baseDate.plusDays(daysToAdd);

        MembershipExpiryDate renewedExpiry = new MembershipExpiryDate(newExpiryDate);

        Person renewedPerson = new Person(
                personToRenew.getName(),
                personToRenew.getPhone(),
                personToRenew.getEmail(),
                personToRenew.getAddress(),
                personToRenew.getMembershipId(),
                renewedExpiry
        );

        model.setPerson(personToRenew, renewedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_RENEW_PERSON_SUCCESS, Messages.format(renewedPerson),
                personToRenew.getMembershipExpiryDate(), renewedExpiry));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof RenewCommand)) {
            return false;
        }
        RenewCommand otherRenewCommand = (RenewCommand) other;
        return membershipId.equals(otherRenewCommand.membershipId)
                && daysToAdd == otherRenewCommand.daysToAdd;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("membershipId", membershipId)
                .add("daysToAdd", daysToAdd)
                .toString();
    }
}
