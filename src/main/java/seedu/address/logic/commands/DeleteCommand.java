package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.MembershipId;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using their Membership ID from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the person identified by their Membership ID.\n"
        + "Parameters: id/MEMBERSHIP_ID (must be a 4-digit integer from 1000 to 9999)\n"
        + "Example: " + COMMAND_WORD + " id/1042";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted person: %1$s";

    private static final Logger logger = LogsCenter.getLogger(DeleteCommand.class);

    private final MembershipId targetId;

    public DeleteCommand(MembershipId targetId) {
        this.targetId = targetId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert targetId != null : "Target membership ID should not be null";

        logger.info("Executing DeleteCommand for Membership ID: " + targetId);

        List<Person> allPersons = model.getAddressBook().getPersonList();

        Person personToDelete = null;
        for (Person person : allPersons) {
            if (person.getMembershipId().equals(targetId)) {
                personToDelete = person;
                break;
            }
        }

        if (personToDelete == null) {
            logger.warning("No person found with Membership ID: " + targetId);
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, targetId));
        }

        assert model.getAddressBook().getPersonList().contains(personToDelete)
            : "Person to delete must exist in address book";

        model.deletePerson(personToDelete);
        logger.info("Successfully deleted person with Membership ID: " + targetId);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteCommand)) {
            return false;
        }
        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetId.equals(otherDeleteCommand.targetId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetId", targetId)
            .toString();
    }
}
