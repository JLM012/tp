package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.util.PersonComparators;

class SortCommandTest {
    private Model model;
    private List<Person> originalOrder;

    @BeforeEach
    void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        originalOrder = new ArrayList<>(model.getFilteredPersonList());
    }

    @Test
    void execute_sortByNameAsc_sortsCorrectly() {
        SortCommand command = new SortCommand(PersonComparators.NAME_ASC, "n/", "asc");
        command.execute(model);
        List<Person> sorted = new ArrayList<>(model.getFilteredPersonList());
        originalOrder.sort(PersonComparators.NAME_ASC);
        assertEquals(originalOrder, sorted);
    }

    @Test
    void execute_sortByNameDesc_sortsCorrectly() {
        SortCommand command = new SortCommand(PersonComparators.NAME_DESC, "n/", "desc");
        command.execute(model);
        List<Person> sorted = new ArrayList<>(model.getFilteredPersonList());
        originalOrder.sort(PersonComparators.NAME_DESC);
        assertEquals(originalOrder, sorted);
    }

    @Test
    void execute_sortNone_restoresOrder() {
        // Sort first
        SortCommand sortAsc = new SortCommand(PersonComparators.NAME_ASC, "n/", "asc");
        sortAsc.execute(model);
        // Restore without prefix
        SortCommand restoreNoPrefix = new SortCommand(null, "none", "none");
        restoreNoPrefix.execute(model);
        List<Person> restoredNoPrefix = new ArrayList<>(model.getFilteredPersonList());
        assertEquals(originalOrder, restoredNoPrefix);
    }

    @Test
    void execute_sortAlreadySorted_showsAlreadySortedMessage() {
        // Sort ascending first
        SortCommand sortAsc = new SortCommand(PersonComparators.NAME_ASC, "n/", "asc");
        sortAsc.execute(model);
        // Try sorting again in the same order
        CommandResult result = sortAsc.execute(model);
        assertEquals(Messages.MESSAGE_NO_CHANGE_IN_DISPLAYED_LIST, result.getFeedbackToUser());
    }

    @Test
    void execute_restoreAlreadyOriginalOrder_showsNoChangeMessage() {
        // List is already in original order
        SortCommand restore = new SortCommand(null, "none", "none");
        CommandResult result = restore.execute(model);
        assertEquals(Messages.MESSAGE_NO_CHANGE_IN_DISPLAYED_LIST, result.getFeedbackToUser());
    }

    @Test
    void execute_restoreFromSortedOrder_returnsMessageRestored() {
        // Sort first to change the order
        SortCommand sortDesc = new SortCommand(PersonComparators.NAME_DESC, "n/", "desc");
        sortDesc.execute(model);
        List<Person> sortedList = new ArrayList<>(model.getFilteredPersonList());
        // Verify list is actually different from original
        if (!sortedList.equals(originalOrder)) {
            // Now restore to original - should return MESSAGE_RESTORED
            SortCommand restore = new SortCommand(null, "none", "none");
            CommandResult result = restore.execute(model);
            assertEquals(SortCommand.MESSAGE_RESTORED, result.getFeedbackToUser());
            // Verify list is restored
            assertEquals(originalOrder, model.getFilteredPersonList());
        }
    }

    @Test
    void equals_and_toString() {
        SortCommand a = new SortCommand(PersonComparators.NAME_ASC, "n/", "asc");
        SortCommand b = new SortCommand(PersonComparators.NAME_ASC, "n/", "asc");
        SortCommand c = new SortCommand(PersonComparators.NAME_DESC, "n/", "desc");
        SortCommand d = new SortCommand(PersonComparators.NAME_ASC, "e/", "asc");

        // Test equality for same values
        assertEquals(a, b);

        // Test inequality for different comparators/orders
        assertNotEquals(a, c);

        // Test inequality for different prefix
        assertNotEquals(a, d);

        // Test self-equality (line 100: if (other == this))
        assertEquals(a, a);

        // Test null inequality (line 104: instanceof handles nulls)
        assertNotEquals(a, null);

        // Test different type inequality (line 104: instanceof)
        assertNotEquals(a, "string");
        assertNotEquals(a, 123);

        // Test toString contains expected fields
        String str = a.toString();
        assert str.contains("prefix") && str.contains("order");
    }
}
