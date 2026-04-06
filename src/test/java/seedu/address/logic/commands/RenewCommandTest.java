package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.MembershipExpiryDate;
import seedu.address.model.person.MembershipId;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RenewCommand.
 */
public class RenewCommandTest {

    @Test
    public void execute_membershipNotExpired_success() {
        MembershipId id = new MembershipId(1000);
        int daysToAdd = 7;
        LocalDate today = LocalDate.now();

        Person base = new PersonBuilder().withMembershipId(id.value).build();
        MembershipExpiryDate nonExpiredDate = new MembershipExpiryDate(today.plusDays(10));
        Person personToRenew = new Person(
                base.getName(),
                base.getPhone(),
                base.getEmail(),
                base.getAddress(),
                base.getMembershipId(),
                nonExpiredDate
        );

        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(personToRenew);
        Model model = new ModelManager(addressBook, new UserPrefs());

        MembershipExpiryDate expectedNewExpiry = new MembershipExpiryDate(nonExpiredDate.value.plusDays(daysToAdd));
        Person renewedPerson = new Person(
                personToRenew.getName(),
                personToRenew.getPhone(),
                personToRenew.getEmail(),
                personToRenew.getAddress(),
                personToRenew.getMembershipId(),
                expectedNewExpiry
        );

        RenewCommand renewCommand = new RenewCommand(id, daysToAdd);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToRenew, renewedPerson);

        String expectedMessage = String.format(RenewCommand.MESSAGE_RENEW_PERSON_SUCCESS,
                Messages.format(renewedPerson), personToRenew.getMembershipExpiryDate(), expectedNewExpiry);

        assertCommandSuccess(renewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_membershipExpiryToday_success() {
        MembershipId id = new MembershipId(1000);
        int daysToAdd = 7;

        LocalDate today = LocalDate.now();

        Person base = new PersonBuilder().withMembershipId(id.value).build();
        MembershipExpiryDate todayExpiry = new MembershipExpiryDate(today);
        Person personToRenew = new Person(
                base.getName(),
                base.getPhone(),
                base.getEmail(),
                base.getAddress(),
                base.getMembershipId(),
                todayExpiry
        );

        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(personToRenew);
        Model model = new ModelManager(addressBook, new UserPrefs());

        MembershipExpiryDate expectedNewExpiry = new MembershipExpiryDate(today.plusDays(daysToAdd));
        Person renewedPerson = new Person(
                personToRenew.getName(),
                personToRenew.getPhone(),
                personToRenew.getEmail(),
                personToRenew.getAddress(),
                personToRenew.getMembershipId(),
                expectedNewExpiry
        );

        RenewCommand renewCommand = new RenewCommand(id, daysToAdd);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToRenew, renewedPerson);

        String expectedMessage = String.format(RenewCommand.MESSAGE_RENEW_PERSON_SUCCESS,
                Messages.format(renewedPerson), personToRenew.getMembershipExpiryDate(), expectedNewExpiry);

        assertCommandSuccess(renewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_membershipExpired_success() {
        MembershipId id = new MembershipId(1000);
        int daysToAdd = 7;
        LocalDate today = LocalDate.now();

        Person base = new PersonBuilder().withMembershipId(id.value).build();
        MembershipExpiryDate expiredDate = new MembershipExpiryDate(today.minusDays(5));
        Person personToRenew = new Person(
                base.getName(),
                base.getPhone(),
                base.getEmail(),
                base.getAddress(),
                base.getMembershipId(),
                expiredDate
        );

        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(personToRenew);
        Model model = new ModelManager(addressBook, new UserPrefs());

        // Current logic: if expired, +1 day means "today".
        MembershipExpiryDate expectedNewExpiry = new MembershipExpiryDate(today.plusDays(daysToAdd - 1));
        Person renewedPerson = new Person(
                personToRenew.getName(),
                personToRenew.getPhone(),
                personToRenew.getEmail(),
                personToRenew.getAddress(),
                personToRenew.getMembershipId(),
                expectedNewExpiry
        );

        RenewCommand renewCommand = new RenewCommand(id, daysToAdd);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToRenew, renewedPerson);

        String expectedMessage = String.format(RenewCommand.MESSAGE_RENEW_PERSON_SUCCESS,
                Messages.format(renewedPerson), personToRenew.getMembershipExpiryDate(), expectedNewExpiry);

        assertCommandSuccess(renewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentMembershipId_failure() {
        MembershipId existingId = new MembershipId(1000);
        MembershipId nonExistentId = new MembershipId(1001);

        Person existingPerson = new PersonBuilder().withMembershipId(existingId.value).build();
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(existingPerson);
        Model model = new ModelManager(addressBook, new UserPrefs());

        RenewCommand renewCommand = new RenewCommand(nonExistentId, 7);

        assertCommandFailure(renewCommand, model,
                String.format(Messages.MESSAGE_PERSON_NOT_FOUND, nonExistentId));
    }

    @Test
    public void equals() {
        MembershipId firstId = new MembershipId(1000);
        MembershipId secondId = new MembershipId(1001);

        RenewCommand firstCommand = new RenewCommand(firstId, 7);
        RenewCommand firstCommandCopy = new RenewCommand(firstId, 7);
        RenewCommand secondIdCommand = new RenewCommand(secondId, 7);
        RenewCommand differentDaysCommand = new RenewCommand(firstId, 14);

        // same values -> returns true
        assertTrue(firstCommand.equals(firstCommandCopy));

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different types -> returns false
        assertFalse(firstCommand.equals(new ClearCommand()));

        // different membership ID -> returns false
        assertFalse(firstCommand.equals(secondIdCommand));

        // different days -> returns false
        assertFalse(firstCommand.equals(differentDaysCommand));
    }

    @Test
    public void toStringMethod() {
        MembershipId membershipId = new MembershipId(1000);
        int daysToAdd = 7;
        RenewCommand renewCommand = new RenewCommand(membershipId, daysToAdd);

        String expected = RenewCommand.class.getCanonicalName()
                + "{membershipId=" + membershipId + ", daysToAdd=" + daysToAdd + "}";
        assertEquals(expected, renewCommand.toString());
    }
}
