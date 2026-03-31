package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "All the data has been deleted successfully.";
    public static final String MESSAGE_CONFIRMATION = "Opened warning window.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_CONFIRMATION, false, false, true, false,
                "Warning!\n"
                + "This command will clear all contacts.\n"
                + "Press Y/N keys to confirm.\n\n"
                + "Alternatively, click the buttons below.\n");
    }
}
