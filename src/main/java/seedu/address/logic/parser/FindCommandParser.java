package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBERSHIP_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.MembershipIdContainsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MEMBERSHIP_ID);

        boolean hasName = argMultimap.getValue(PREFIX_NAME).isPresent()
            && !argMultimap.getValue(PREFIX_NAME).get().trim().isEmpty();
        boolean hasId = argMultimap.getValue(PREFIX_MEMBERSHIP_ID).isPresent()
            && !argMultimap.getValue(PREFIX_MEMBERSHIP_ID).get().trim().isEmpty();

        if (hasName == hasId) {
            // both present or neither present
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        Predicate<Person> predicate;

        if (hasName) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().trim().split("\\s+");
            predicate = new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords));
        } else {
            String[] idKeywords = argMultimap.getValue(PREFIX_MEMBERSHIP_ID).get().trim().split("\\s+");
            predicate = new MembershipIdContainsPredicate(Arrays.asList(idKeywords));
        }

        return new FindCommand(predicate);
    }
}
