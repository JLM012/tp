package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ListUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;


/**
 * Sorts the currently displayed person list by a specified prefix and order.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Toggles a sorting order on all displayed lists by the specified prefix and order.\n"
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

    private static final Logger logger = LogsCenter.getLogger(SortCommand.class);

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

        assert this.order.equals("asc") || this.order.equals("desc") || this.order.equals("none")
                : "order must be one of: asc, desc, none";
        assert this.order.equals("none") || this.comparator != null
                : "comparator must be non-null when order is asc/desc";
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        logger.info("Executing SortCommand: ");

        // Handle "none" order with before/after check
        if (order.equals("none")) {
            List<Person> beforeRestore = ListUtil.copyDisplayedList(model);
            model.sortFilteredPersonList(null);
            List<Person> afterRestore = ListUtil.copyDisplayedList(model);
            if (ListUtil.isSameList(beforeRestore, afterRestore)) {
                logger.info("No change in displayed list as order requested and current order are the same");
                return new CommandResult(Messages.MESSAGE_NO_CHANGE_IN_DISPLAYED_LIST);
            }

            logger.info("Successfully restored to original order");
            return new CommandResult(MESSAGE_RESTORED);
        }


        assert comparator != null : "comparator must be non-null for asc/desc sorting";

        // Handle "asc" or "desc" with before/after check
        List<Person> beforeSort = ListUtil.copyDisplayedList(model);
        model.sortFilteredPersonList(comparator);
        List<Person> afterSort = ListUtil.copyDisplayedList(model);
        if (ListUtil.isSameList(beforeSort, afterSort)) {
            logger.info("No change in displayed list as order requested and current order are the same");
            return new CommandResult(Messages.MESSAGE_NO_CHANGE_IN_DISPLAYED_LIST);
        }

        logger.info("Displayed list is now sorted by " + prefix + " in " + order + " order");
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
