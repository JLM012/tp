package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.MembershipId;
import seedu.address.model.person.Person;

public class SampleDataUtilTest {

    @Test
    void getSamplePersons_returnsCorrectNumber() {
        Person[] persons = SampleDataUtil.getSamplePersons();
        assertEquals(6, persons.length);
    }

    @Test
    void getSampleAddressBook_notNull() {
        ReadOnlyAddressBook ab = SampleDataUtil.getSampleAddressBook();
        assertNotNull(ab);
    }

    @Test
    public void getSamplePersons_returnsExpectedPersons() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        assertEquals(6, samplePersons.length);
        assertEquals(new MembershipId(MembershipId.MIN_ID), samplePersons[0].getMembershipId());
        assertEquals(new MembershipId(MembershipId.MIN_ID + 5), samplePersons[5].getMembershipId());
        assertEquals("Alex Yeoh", samplePersons[0].getName().toString());
        assertEquals("royb@example.com", samplePersons[5].getEmail().toString());
    }

    @Test
    public void getSampleAddressBook_containsAllSamplePersonsInOrder() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();

        List<Person> expected = Arrays.asList(samplePersons);
        assertEquals(expected.size(), sampleAddressBook.getPersonList().size());
        assertIterableEquals(expected, sampleAddressBook.getPersonList());
    }
}
