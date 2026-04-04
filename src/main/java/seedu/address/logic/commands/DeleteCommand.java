package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
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
 * Deletes one or more persons identified by their Membership IDs from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes one or more persons identified by their Membership IDs.\n"
        + "Parameters: id/MEMBERSHIP_ID [MORE_MEMBERSHIP_IDs]...\n"
        + "(must be 4-digit integers from 1000 to 9999, space-separated after id/)\n"
        + "Example: " + COMMAND_WORD + " id/1042 1043 1044";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted person(s):\n%1$s";

    private static final Logger logger = LogsCenter.getLogger(DeleteCommand.class);

    private final List<MembershipId> targetIds;

    public DeleteCommand(List<MembershipId> targetIds) {
        this.targetIds = targetIds;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert targetIds != null && !targetIds.isEmpty() : "Target IDs should not be null or empty";

        logger.info("Executing DeleteCommand for Membership IDs: " + targetIds);

        // Resolve all persons first before deleting (fail fast if any ID not found)
        List<Person> personsToDelete = new ArrayList<>();
        for (MembershipId targetId : targetIds) {
            Person person = model.getAddressBook().getPersonList().stream()
                .filter(p -> p.getMembershipId().equals(targetId))
                .findFirst()
                .orElseThrow(() -> {
                    logger.warning("No person found with Membership ID: " + targetId);
                    return new CommandException(
                        String.format(Messages.MESSAGE_PERSON_NOT_FOUND, targetId));
                });
            personsToDelete.add(person);
        }

        // Sort by membership ID before deleting
        personsToDelete.sort((a, b) -> Integer.compare(a.getMembershipId().value, b.getMembershipId().value));

        StringBuilder deletedNames = new StringBuilder();
        for (Person person : personsToDelete) {
            model.deletePerson(person);
            logger.info("Successfully deleted person with Membership ID: " + person.getMembershipId());
            deletedNames.append(Messages.format(person)).append("\n");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedNames.toString().trim()));
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
        return targetIds.equals(otherDeleteCommand.targetIds);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetIds", targetIds)
            .toString();
    }
}
