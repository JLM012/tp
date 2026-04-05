package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.util.PersonComparators;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;

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
        // Future: Add more maps for id, phone, email, address, expiry date
    }

    @Override
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.equalsIgnoreCase("none")) {
            return new SortCommand(null, "none", "none");
        }

        Prefix[] allPrefixes = new Prefix[] {
                PREFIX_NAME,
                // Future: Add support for other prefixes
        };
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, allPrefixes);

        // Use utility method to check for duplicate identical prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(allPrefixes);

        // Use utility method to check that exactly one prefix is present
        Prefix usedPrefix = argMultimap.verifyExactlyOnePrefixPresentFor(SortCommand.MESSAGE_USAGE, allPrefixes);

        // No prefix detected
        if (usedPrefix == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        // Obtain the sorting order
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
