package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all members";
    public static final String MESSAGE_NO_PERSONS = "No member to list";

    private static final Logger logger = LogsCenter.getLogger(ListCommand.class);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        logger.info("Executing ListCommand");
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (model.getFilteredPersonList().isEmpty()) {
            logger.warning("No member to list");
            return new CommandResult(MESSAGE_NO_PERSONS);
        }

        logger.info("Successfully listed all members");
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
