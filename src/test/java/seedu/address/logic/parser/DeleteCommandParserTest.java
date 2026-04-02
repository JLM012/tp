package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.MembershipId;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_singleValidMembershipId_success() {
        Person personToDelete = model.getAddressBook().getPersonList().get(0);
        MembershipId targetId = personToDelete.getMembershipId();
        DeleteCommand deleteCommand = new DeleteCommand(List.of(targetId));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
            Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleValidMembershipIds_success() {
        Person firstPerson = model.getAddressBook().getPersonList().get(0);
        Person secondPerson = model.getAddressBook().getPersonList().get(1);
        DeleteCommand deleteCommand = new DeleteCommand(
            List.of(firstPerson.getMembershipId(), secondPerson.getMembershipId()));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
            Messages.format(firstPerson) + "\n" + Messages.format(secondPerson));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(firstPerson);
        expectedModel.deletePerson(secondPerson);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidMembershipId_throwsCommandException() {
        MembershipId nonExistentId = new MembershipId(MembershipId.MAX_ID);
        DeleteCommand deleteCommand = new DeleteCommand(List.of(nonExistentId));

        assertCommandFailure(deleteCommand, model,
            String.format(Messages.MESSAGE_PERSON_NOT_FOUND, nonExistentId));
    }

    @Test
    public void execute_oneValidOneInvalidMembershipId_throwsCommandException() {
        Person existingPerson = model.getAddressBook().getPersonList().get(0);
        MembershipId nonExistentId = new MembershipId(MembershipId.MAX_ID);
        DeleteCommand deleteCommand = new DeleteCommand(
            List.of(existingPerson.getMembershipId(), nonExistentId));

        // Should fail fast — no deletions if any ID is invalid
        assertCommandFailure(deleteCommand, model,
            String.format(Messages.MESSAGE_PERSON_NOT_FOUND, nonExistentId));
    }

    @Test
    public void equals() {
        MembershipId firstId = new MembershipId(MembershipId.MIN_ID);
        MembershipId secondId = new MembershipId(MembershipId.MIN_ID + 1);
        DeleteCommand deleteFirstCommand = new DeleteCommand(List.of(firstId));
        DeleteCommand deleteSecondCommand = new DeleteCommand(List.of(secondId));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(List.of(firstId));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different id -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // multiple ids -> returns true if same list
        DeleteCommand deleteMultiCommand1 = new DeleteCommand(List.of(firstId, secondId));
        DeleteCommand deleteMultiCommand2 = new DeleteCommand(List.of(firstId, secondId));
        assertTrue(deleteMultiCommand1.equals(deleteMultiCommand2));
    }

    @Test
    public void toStringMethod() {
        MembershipId targetId = new MembershipId(MembershipId.MIN_ID);
        DeleteCommand deleteCommand = new DeleteCommand(List.of(targetId));
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIds=[" + targetId + "]}";
        assertEquals(expected, deleteCommand.toString());
    }
}
