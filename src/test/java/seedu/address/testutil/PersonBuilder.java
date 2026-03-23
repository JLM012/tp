package seedu.address.testutil;

import static seedu.address.testutil.TestUtil.getDateNDaysRelativeToToday;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.MembershipExpiryDate;
import seedu.address.model.person.MembershipId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_MEMBERSHIP_EXPIRY_DATE = getDateNDaysRelativeToToday(285);

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private MembershipId membershipId;
    private MembershipExpiryDate membershipExpiryDate; // ADD THIS

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        membershipId = new MembershipId(MembershipId.MIN_ID);
        membershipExpiryDate = new MembershipExpiryDate(DEFAULT_MEMBERSHIP_EXPIRY_DATE);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        membershipId = personToCopy.getMembershipId();
        membershipExpiryDate = personToCopy.getMembershipExpiryDate(); // ADD THIS
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code MembershipId} of the {@code Person} that we are building.
     */
    public PersonBuilder withMembershipId(int id) {
        this.membershipId = new MembershipId(id);
        return this;
    }

    /**
     * Sets the {@code MembershipExpiryDate} of the {@code Person} that we are building.
     */
    public PersonBuilder withMembershipExpiryDate(String expiryDate) { // ADD THIS
        this.membershipExpiryDate = new MembershipExpiryDate(expiryDate);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, membershipId, membershipExpiryDate); // UPDATED
    }
}
