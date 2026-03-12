package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

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

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final MembershipId targetId;

    public DeleteCommand(MembershipId targetId) {
        this.targetId = targetId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> allPersons = model.getAddressBook().getPersonList();

        Person personToDelete = null;
        for (Person person : allPersons) {
            if (person.getMembershipId().equals(targetId)) {
                personToDelete = person;
                break;
            }
        }

        if (personToDelete == null) {
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, targetId));
        }

        model.deletePerson(personToDelete);
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