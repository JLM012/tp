package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedResult = new CommandResult(ClearCommand.MESSAGE_NO_DATA);

        assertEquals(expectedResult, new ClearCommand().execute(model));
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        CommandResult expectedResult = new CommandResult(
                ClearCommand.MESSAGE_CONFIRMATION,
                false,
                false,
                true,
                false,
                "Warning!\n"
                        + "This command will clear all contacts.\n"
                        + "Press Y/N keys to confirm.\n\n"
                        + "Alternatively, click the buttons below.\n"
        );

        assertEquals(expectedResult, new ClearCommand().execute(model));
        assertEquals(expectedModel, model);
    }
}
