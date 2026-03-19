package seedu.address.storage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.MembershipExpiryDate;
import seedu.address.model.person.MembershipId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final Integer membershipId;
    private final String membershipExpiryDate; // ADD THIS

    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("membershipId") Integer membershipId,
                             @JsonProperty("membershipExpiryDate") String membershipExpiryDate) { // ADD THIS
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.membershipId = membershipId;
        this.membershipExpiryDate = membershipExpiryDate;
    }

    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        membershipId = source.getMembershipId().value;
        membershipExpiryDate = source.getMembershipExpiryDate().toString(); // ADD THIS
    }

    public Person toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);


        if (membershipId == null) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, MembershipId.class.getSimpleName()));
        }
        if (!MembershipId.isValidMembershipId(membershipId)) {
            throw new IllegalValueException(MembershipId.MESSAGE_CONSTRAINTS);
        }
        final MembershipId modelMembershipId = new MembershipId(membershipId);

        if (membershipExpiryDate == null) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, MembershipExpiryDate.class.getSimpleName()));
        }

        // Allow any valid date format from JSON, including expired dates via overloaded constructor
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(membershipExpiryDate, MembershipExpiryDate.DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(MembershipExpiryDate.MESSAGE_CONSTRAINTS);
        }

        final MembershipExpiryDate modelMembershipExpiryDate = new MembershipExpiryDate(parsedDate);

        return new Person(modelName, modelPhone, modelEmail, modelAddress,
                modelMembershipId, modelMembershipExpiryDate);
    }
}
