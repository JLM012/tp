package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBERSHIP_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.util.PersonComparators;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {
    // COMPARATOR_MAP: prefix string -> (order -> comparator)
    private static final Map<String, Map<String, Comparator<Person>>> COMPARATOR_MAP = new HashMap<>();
    static {
        Map<String, Comparator<Person>> nameMap = new HashMap<>();
        nameMap.put("asc", PersonComparators.NAME_ASC);
        nameMap.put("desc", PersonComparators.NAME_DESC);
        COMPARATOR_MAP.put("n/", nameMap);

        Map<String, Comparator<Person>> phoneMap = new HashMap<>();
        phoneMap.put("asc", PersonComparators.PHONE_ASC);
        phoneMap.put("desc", PersonComparators.PHONE_DESC);
        COMPARATOR_MAP.put("p/", phoneMap);

        Map<String, Comparator<Person>> emailMap = new HashMap<>();
        emailMap.put("asc", PersonComparators.EMAIL_ASC);
        emailMap.put("desc", PersonComparators.EMAIL_DESC);
        COMPARATOR_MAP.put("e/", emailMap);

        Map<String, Comparator<Person>> addressPostalCodeMap = new HashMap<>();
        addressPostalCodeMap.put("asc", PersonComparators.ADDRESS_POSTAL_CODE_ASC);
        addressPostalCodeMap.put("desc", PersonComparators.ADDRESS_POSTAL_CODE_DESC);
        COMPARATOR_MAP.put("a/", addressPostalCodeMap);

        Map<String, Comparator<Person>> idMap = new HashMap<>();
        idMap.put("asc", PersonComparators.ID_ASC);
        idMap.put("desc", PersonComparators.ID_DESC);
        COMPARATOR_MAP.put("id/", idMap);

        Map<String, Comparator<Person>> expiryDateMap = new HashMap<>();
        expiryDateMap.put("asc", PersonComparators.EXPIRY_DATE_ASC);
        expiryDateMap.put("desc", PersonComparators.EXPIRY_DATE_DESC);
        COMPARATOR_MAP.put("m/", expiryDateMap);
    }

    @Override
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.equalsIgnoreCase("none")) {
            return new SortCommand(null, "none", "none");
        }

        Prefix[] allPrefixes = new Prefix[] {
            PREFIX_NAME,
            PREFIX_PHONE,
            PREFIX_EMAIL,
            PREFIX_ADDRESS,
            PREFIX_ID,
            PREFIX_MEMBERSHIP_EXPIRY_DATE
        };

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, allPrefixes);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        // Use utility method to check for duplicate identical prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(allPrefixes);

        // Use utility method to check that exactly one prefix is present
        Prefix usedPrefix = argMultimap.verifyExactlyOnePrefixPresentFor(SortCommand.MESSAGE_USAGE, allPrefixes);

        // No prefix detected
        if (usedPrefix == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        // Get the sorting order
        String order = argMultimap.getValue(usedPrefix).orElse("").trim().toLowerCase();

        // Validate the sorting order
        if (!(order.equals("asc") || order.equals("desc"))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        // Comparator lookup
        String prefixString = usedPrefix.getPrefix();
        Map<String, Comparator<Person>> orderMap = COMPARATOR_MAP.get(prefixString);

        if (orderMap == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        if (!orderMap.containsKey(order)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        Comparator<Person> comparator = orderMap.get(order);

        return new SortCommand(comparator, prefixString, order);
    }
}
