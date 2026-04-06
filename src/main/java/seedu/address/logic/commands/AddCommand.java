package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBERSHIP_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.MembershipId;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a member to the address book.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_MEMBERSHIP_EXPIRY_DATE + "EXPIRY_DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_MEMBERSHIP_EXPIRY_DATE + "2026-12-31";

    public static final String MESSAGE_SUCCESS = "New member added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This member already exists in the address book";
    public static final String MESSAGE_ADDRESS_BOOK_FULL = "Cannot add more members."
            + "Address book has reached its maximum capacity of "
            + MembershipId.MAX_CAPACITY + " members.";

    private static final Logger logger = LogsCenter.getLogger(AddCommand.class);

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        assert toAdd != null : "Member should not be null before adding";

        logger.info("Executing AddCommand for member: " + toAdd);

        if (model.hasPerson(toAdd)) {
            logger.warning("Duplicate member detected: " + toAdd);
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (!model.canGenerateMembershipId()) {
            logger.warning("Address book is full. Cannot add member: " + toAdd);
            throw new CommandException(MESSAGE_ADDRESS_BOOK_FULL);
        }

        // Generate new membership ID and create person with it
        int newMembershipId = model.getNextMembershipId();
        Person personWithId = new Person(toAdd.getName(), toAdd.getPhone(), toAdd.getEmail(),
                toAdd.getAddress(), new MembershipId(newMembershipId),
                toAdd.getMembershipExpiryDate());

        model.addPerson(personWithId);
        assert model.hasPerson(personWithId) : "Model should, but does not contain the newly added member";
        logger.info("Successfully added member: " + personWithId);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personWithId)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
