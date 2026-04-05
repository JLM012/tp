package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.util.PersonComparators;
import java.util.Comparator;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {
    @Override
    public SortCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME);
        // Future: Add more prefixes for id, phone, email, address, expiry date

        Prefix usedPrefix = null;
        for (Prefix prefix : new Prefix[]{PREFIX_NAME}) {
            // Future: Add more prefixes to the array above
            if (argMultimap.getValue(prefix).isPresent()) {
                // Ensure only one prefix is used
                if (usedPrefix != null) {
                    throw new ParseException(SortCommand.MESSAGE_INVALID_PREFIX);
                }
                usedPrefix = prefix;
            }
        }

        if (usedPrefix == null || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(SortCommand.MESSAGE_INVALID_PREFIX);
        }

        String order = argMultimap.getValue(usedPrefix).orElse("").trim().toLowerCase();

        if (!(order.equals("asc") || order.equals("desc") || order.equals("none"))) {
            throw new ParseException(SortCommand.MESSAGE_INVALID_ORDER);
        }

        Comparator<Person> comparator = null;
        String prefixString = usedPrefix.getPrefix();

        switch (prefixString) {
            case "n/":
                switch (order) {
                    case "asc":
                        comparator = PersonComparators.NAME_ASC;
                        break;
                    case "desc":
                        comparator = PersonComparators.NAME_DESC;
                        break;
                    case "none":
                        comparator = null;
                        break;
                    default:
                        throw new ParseException(SortCommand.MESSAGE_INVALID_ORDER);
                }
                break;
            // Future: Add more cases for id, phone, email, address, expiry date
            default:
                throw new ParseException(SortCommand.MESSAGE_INVALID_PREFIX);
        }
        return new SortCommand(comparator, prefixString, order);
    }
}