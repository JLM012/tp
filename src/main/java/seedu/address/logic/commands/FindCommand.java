package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose attribute matches any of "
            + "the specified keywords (case-insensitive) and displays them as a list.\n"
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

    private final Predicate<Person> predicate;

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
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
