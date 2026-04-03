package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.commons.util.ListUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.Comparator;
import java.util.List;

/**
 * Sorts the currently displayed person list by a specified prefix and order.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Toggles a sorting direction on all displayed person list by the specified prefix and order.\n"
            + "Parameters: PREFIX/ORDER or 'none'\n"
            + "Exactly one prefix must be used, unless 'none' is specified.\n"
            + "Prefixes:\n"
            + "  id/ID\n"
            + "  n/NAME\n"
            + "  p/PHONE\n"
            + "  e/EMAIL\n"
            + "  a/ADDRESS(Postal Code)\n"
            + "  m/EXPIRY_DATE\n"
            + "Order can be 'asc' for ascending, 'desc' for descending.\n"
            + "Use 'none' as parameter to turn off sorting and restore original order.\n"
            + "Example: " + COMMAND_WORD + " n/desc\n"
            + "Example: " + COMMAND_WORD + " none";
    public static final String MESSAGE_SUCCESS = "Sorted by %s in %s order.";
    public static final String MESSAGE_RESTORED = "Restored to original order.";

    private final Comparator<Person> comparator;
    private final String prefix;
    private final String order;

    /**
     * @param comparator Comparator to use for sorting, or null to restore original order
     * @param prefix Prefix being sorted (for message)
     * @param order Sort order (asc, desc, or none)
     */
    public SortCommand(Comparator<Person> comparator, String prefix, String order) {
        this.comparator = comparator;
        this.prefix = prefix;
        this.order = order.toLowerCase();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Handle "none" order with before/after check
        if (order.equals("none")) {
            List<Person> beforeRestore = ListUtil.copyDisplayedList(model);
            model.sortFilteredPersonList(null);
            List<Person> afterRestore = ListUtil.copyDisplayedList(model);
            if (ListUtil.isSameList(beforeRestore, afterRestore)) {
                return new CommandResult(Messages.MESSAGE_NO_CHANGE_IN_DISPLAYED_LIST);
            }
            return new CommandResult(MESSAGE_RESTORED);
        }

        // Handle "asc" or "desc" with before/after check
        List<Person> beforeSort = ListUtil.copyDisplayedList(model);
        model.sortFilteredPersonList(comparator);
        List<Person> afterSort = ListUtil.copyDisplayedList(model);
        if (ListUtil.isSameList(beforeSort, afterSort)) {
            return new CommandResult(Messages.MESSAGE_NO_CHANGE_IN_DISPLAYED_LIST);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, prefix, order));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherCommand = (SortCommand) other;
        return prefix.equals(otherCommand.prefix)
                && order.equals(otherCommand.order);
    }

    @Override
    public String toString() {
        // Use ToStringBuilder for consistency with other commands
        return new ToStringBuilder(this)
                .add("prefix", prefix)
                .add("order", order)
                .toString();
    }
}
