package seedu.address.logic.commands;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Clears the address book after confirmation.
 */
public class ConfirmClearCommand extends Command {

    public static final String COMMAND_WORD = "confirmclear";

    public final String MESSAGE_SUCCESS = "All the data has been deleted successfully.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(
                MESSAGE_SUCCESS,
                false,
                false,
                false,
                true,
                MESSAGE_SUCCESS
        );
    }
}
