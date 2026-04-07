package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;

import seedu.address.logic.commands.RenewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.MembershipId;

/**
 * Parses input arguments and creates a new RenewCommand object.
 */
public class RenewCommandParser implements Parser<RenewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RenewCommand
     * and returns a RenewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RenewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_DAYS);

        if (!argMultimap.getPreamble().isEmpty()
                || !argMultimap.getValue(PREFIX_ID).isPresent()
                || !argMultimap.getValue(PREFIX_DAYS).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenewCommand.MESSAGE_USAGE));
        }

        String membershipIdToken = argMultimap.getValue(PREFIX_ID).get().trim();
        String daysToken = argMultimap.getValue(PREFIX_DAYS).get().trim();

        if (membershipIdToken.isEmpty() && daysToken.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenewCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ID, PREFIX_DAYS);


        MembershipId membershipId = ParserUtil.parseMembershipId(membershipIdToken);

        if (daysToken.isEmpty()) {
            throw new  ParseException(RenewCommand.MESSAGE_INVALID_DAYS);
        }

        int daysToAdd;
        try {
            daysToAdd = Integer.parseInt(daysToken);
        } catch (NumberFormatException e) {
            throw new ParseException(RenewCommand.MESSAGE_INVALID_DAYS);
        }

        if (!daysToken.equals(Integer.toString(daysToAdd))
                || !RenewCommand.isValidDaysToAdd(daysToAdd)) {
            throw new ParseException(RenewCommand.MESSAGE_INVALID_DAYS);
        }

        return new RenewCommand(membershipId, daysToAdd);

    }
}
