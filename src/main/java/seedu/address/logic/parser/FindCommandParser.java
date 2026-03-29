package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBERSHIP_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.ExpiryDateContainsKeywordsPredicate;
import seedu.address.model.person.MembershipIdContainsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.PostalCodeContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        // Accept all possible prefixes
        Prefix[] allPrefixes = new Prefix[] {
            PREFIX_NAME,
            PREFIX_ID,
            PREFIX_PHONE,
            PREFIX_ADDRESS,
            PREFIX_MEMBERSHIP_EXPIRY_DATE,
            PREFIX_EMAIL
        };
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, allPrefixes);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Use utility method to check for duplicate identical prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(allPrefixes);

        // Use utility method to check that exactly one prefix is present
        Prefix usedPrefix = argMultimap.verifyExactlyOnePrefixPresentFor(FindCommand.MESSAGE_USAGE, allPrefixes);

        // No prefix detected
        if (usedPrefix == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Get keywords (simplified logic)
        String value = argMultimap.getValue(usedPrefix).get().trim();
        value = value.replaceAll("\\s+", " ");
        if (value.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        List<String> keywordList = Arrays.asList(value.split(" "));

        // Route to the correct predicate using a switch statement
        switch (usedPrefix.getPrefix()) {
        case "n/":
            return new FindCommand(new NameContainsKeywordsPredicate(keywordList));
        case "p/":
            return new FindCommand(new PhoneContainsKeywordsPredicate(keywordList));
        case "e/":
            return new FindCommand(new EmailContainsKeywordsPredicate(keywordList));
        case "a/":
            return new FindCommand(new PostalCodeContainsKeywordsPredicate(keywordList));
        case "id/":
            return new FindCommand(new MembershipIdContainsPredicate(keywordList));
        case "m/":
            return new FindCommand(new ExpiryDateContainsKeywordsPredicate(keywordList));
        default:
            throw new ParseException("Find by this prefix is not yet supported.");
        }
    }
}
