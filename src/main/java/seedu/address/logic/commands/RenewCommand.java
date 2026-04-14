package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
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

    public static final int MIN_RENEW_DAYS = 1;
    public static final int MAX_RENEW_DAYS = 730;


    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Renews a member's membership by Membership ID.\n"
            + "Parameters: " + PREFIX_ID + "MEMBERSHIP_ID " + PREFIX_DAYS + "DAYS\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_ID + "1001 " + PREFIX_DAYS + "7";

    public static final String MESSAGE_RENEW_PERSON_SUCCESS =
            "Renewed membership ID: %1$s\nOld expiry date: %2$s\nNew expiry date: %3$s";

    public static final String MESSAGE_INVALID_DAYS =
            "Days to add must be a number from " + MIN_RENEW_DAYS + " to " + MAX_RENEW_DAYS + " (2 years)";

    private static final Logger logger = LogsCenter.getLogger(RenewCommand.class);

    private final MembershipId membershipId;
    private final int daysToAdd;

    /**
     * Creates a RenewCommand to renew a member with the given {@code membershipId}
     * by {@code daysToAdd} days.
     */
    public RenewCommand(MembershipId membershipId, int daysToAdd) {
        requireNonNull(membershipId);
        if (!isValidDaysToAdd(daysToAdd)) {
            throw new IllegalArgumentException(MESSAGE_INVALID_DAYS);
        }
        this.membershipId = membershipId;
        this.daysToAdd = daysToAdd;
    }

    /**
     * Returns true if {@code daysToAdd} is within the allowed renewal range.
     */
    public static boolean isValidDaysToAdd(int daysToAdd) {
        return daysToAdd >= MIN_RENEW_DAYS && daysToAdd <= MAX_RENEW_DAYS;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        logger.info("Executing RenewCommand for Membership ID: " + membershipId
                + ", daysToAdd: " + daysToAdd);

        List<Person> allPersons = model.getAddressBook().getPersonList();
        Person personToRenew = null;

        for (Person person : allPersons) {
            if (person.getMembershipId().equals(membershipId)) {
                personToRenew = person;
                break;
            }
        }

        if (personToRenew == null) {
            logger.warning("No member found with Membership ID: " + membershipId);
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, membershipId));
        }

        LocalDate today = LocalDate.now();
        LocalDate currentExpiry = personToRenew.getMembershipExpiryDate().value;

        LocalDate newExpiryDate;
        if (currentExpiry.isBefore(today)) {
            // Expired: count today as day 1, so +1 => today
            newExpiryDate = today.plusDays(daysToAdd - 1);
        } else {
            newExpiryDate = currentExpiry.plusDays(daysToAdd);
        }


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

        logger.info("Successfully renewed Membership ID: " + membershipId
                + " from " + personToRenew.getMembershipExpiryDate()
                + " to " + renewedExpiry);

        return new CommandResult(String.format(MESSAGE_RENEW_PERSON_SUCCESS, personToRenew.getMembershipId(),
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
