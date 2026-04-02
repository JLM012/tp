package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setAddressBookFilePath(null));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        UserPrefs userPrefs = new UserPrefs();
        assertTrue(userPrefs.equals(userPrefs));
    }

    @Test
    public void equals_nonUserPrefs_returnsFalse() {
        UserPrefs userPrefs = new UserPrefs();
        assertFalse(userPrefs.equals(null));
        assertFalse(userPrefs.equals("not user prefs"));
    }

    @Test
    public void equals_differentGuiSettings_returnsFalse() {
        UserPrefs first = new UserPrefs();
        UserPrefs second = new UserPrefs();

        // Keep file path the same; only gui settings differ
        second.setGuiSettings(new GuiSettings(800, 600, 10, 10));

        assertFalse(first.equals(second));
    }

    @Test
    public void equals_sameGuiSettingsAndPath_returnsTrue() {
        UserPrefs first = new UserPrefs();
        UserPrefs second = new UserPrefs();

        GuiSettings custom = new GuiSettings(800, 600, 10, 10);
        first.setGuiSettings(custom);
        second.setGuiSettings(new GuiSettings(800, 600, 10, 10)); // equal value object

        assertTrue(first.equals(second));
    }

    @Test
    public void hashCode_equalsUserPrefsSameHashCode() {
        UserPrefs first = new UserPrefs();
        UserPrefs second = new UserPrefs(first);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }
}
