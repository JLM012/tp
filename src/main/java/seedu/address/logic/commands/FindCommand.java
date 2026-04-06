package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ListUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all member(s) in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all member(s) by the specified prefix, "
            + "whose field matches any of "
            + "the specified keywords and displays them as a list.\n"
            + "Parameters: PREFIX/KEYWORD [MORE_KEYWORDS]\n"
            + "Exactly one prefix must be used.\n"
            + "At least one keyword must provided.\n"
            + "Prefixes:\n"
            + "  id/ID\n"
            + "  n/NAME\n"
            + "  p/PHONE\n"
            + "  e/EMAIL\n"
            + "  a/ADDRESS(Postal Code)\n"
            + "  m/EXPIRY_DATE\n"
            + "Example: " + COMMAND_WORD + " n/alice bob charlie\n"
            + "Example: " + COMMAND_WORD + " p/91234567 98765432";

    private static final Logger logger = LogsCenter.getLogger(FindCommand.class);

    private final Predicate<Person> predicate;

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        logger.info("Executing FindCommand: ");

        List<Person> beforeFind = ListUtil.copyDisplayedList(model);
        model.updateFilteredPersonList(predicate);
        List<Person> afterFind = ListUtil.copyDisplayedList(model);

        if (ListUtil.isSameList(beforeFind, afterFind)) {
            logger.warning("Found the same member(s). No change in displayed list");
            return new CommandResult(Messages.MESSAGE_NO_CHANGE_IN_DISPLAYED_LIST);
        }

        logger.info("Successfully found member(s) matching criteria");

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
