package seedu.address.model.util;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * A collection of static {@link Comparator} constants for sorting {@link Person} objects.
 *
 * <p>Each field exposes an ascending ({@code _ASC}) and a descending ({@code _DESC}) variant.
 * Entries that cannot be compared meaningfully (e.g. a malformed postal code) are sorted to the
 * end of the list in both directions.
 *
 * <p>This is a utility class and cannot be instantiated.
 */
public class PersonComparators {

    // -------------------------------------------------------------------------
    // Name
    // -------------------------------------------------------------------------

    /**
     * Case-insensitive ascending sort by full name.
     * Ties are broken by membership ID to ensure a stable, deterministic order.
     */
    public static final Comparator<Person> NAME_ASC =
        Comparator.comparing((Person p) -> p.getName().fullName, String.CASE_INSENSITIVE_ORDER)
            .thenComparingInt(p -> p.getMembershipId().value);

    public static final Comparator<Person> NAME_DESC = NAME_ASC.reversed();

    // -------------------------------------------------------------------------
    // Phone
    // -------------------------------------------------------------------------

    /** Natural (lexicographic) ascending sort by phone number. */
    public static final Comparator<Person> PHONE_ASC =
        Comparator.comparing((Person p) -> p.getPhone().value);

    public static final Comparator<Person> PHONE_DESC = PHONE_ASC.reversed();

    // -------------------------------------------------------------------------
    // Email
    // -------------------------------------------------------------------------

    /** Case-insensitive ascending sort by e-mail address. */
    public static final Comparator<Person> EMAIL_ASC =
        Comparator.comparing((Person p) -> p.getEmail().value, String.CASE_INSENSITIVE_ORDER);

    public static final Comparator<Person> EMAIL_DESC = EMAIL_ASC.reversed();

    // -------------------------------------------------------------------------
    // Address — postal code
    // -------------------------------------------------------------------------

    public static final Comparator<Person> ADDRESS_POSTAL_CODE_ASC =
        Comparator.comparingInt(PersonComparators::extractPostalCode);

    public static final Comparator<Person> ADDRESS_POSTAL_CODE_DESC =
        ADDRESS_POSTAL_CODE_ASC.reversed();

    // -------------------------------------------------------------------------
    // Membership expiry date
    // -------------------------------------------------------------------------

    /**
     * Ascending sort by membership expiry date.
     *
     * <p>Requires {@code MembershipExpiryDate.value} to be {@link Comparable} with correct natural
     * order (e.g. {@link java.time.LocalDate}, or an ISO-8601 string such as {@code YYYY-MM-DD}).
     */
    public static final Comparator<Person> EXPIRY_DATE_ASC =
        Comparator.comparing(p -> p.getMembershipExpiryDate().value);

    public static final Comparator<Person> EXPIRY_DATE_DESC = EXPIRY_DATE_ASC.reversed();

    // -------------------------------------------------------------------------
    // Membership ID
    // -------------------------------------------------------------------------

    public static final Comparator<Person> ID_ASC =
        Comparator.comparingInt(p -> p.getMembershipId().value);

    public static final Comparator<Person> ID_DESC = ID_ASC.reversed();

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    private PersonComparators() {}

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /**
     * Extracts the 6-digit trailing postal code from an address string.
     *
     * <p>Returns {@link Integer#MAX_VALUE} if the address is too short or the last six characters
     * are not a valid integer, so malformed entries always sort last regardless of direction.
     */
    private static int extractPostalCode(Person p) {
        String address = p.getAddress().value.stripTrailing();
        if (address.length() < 6) {
            return Integer.MAX_VALUE;
        }
        String candidate = address.substring(address.length() - 6);
        try {
            return Integer.parseInt(candidate);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }
}
