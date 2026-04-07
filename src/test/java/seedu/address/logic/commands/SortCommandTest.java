package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.TypicalPersons.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
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
    void equals_and_toString() {
        SortCommand a = new SortCommand(PersonComparators.NAME_ASC, "n/", "asc");
        SortCommand b = new SortCommand(PersonComparators.NAME_ASC, "n/", "asc");
        SortCommand c = new SortCommand(PersonComparators.NAME_DESC, "n/", "desc");
        assertEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(a, null);
        assertNotEquals(a, "string");
        String str = a.toString();
        assert str.contains("prefix") && str.contains("order");
    }
}
