package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.MembershipId;

/**
 * Parses input arguments and creates a new DeleteCommand object.
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    private static final Prefix PREFIX_ID = new Prefix("id/");

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ID);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ID);

        Prefix usedPrefix = argMultimap.verifyExactlyOnePrefixPresentFor(DeleteCommand.MESSAGE_USAGE, PREFIX_ID);

        // Split all values after id/ by whitespace
        String[] idTokens = argMultimap.getValue(usedPrefix).get().trim().split("\\s+");

        List<MembershipId> membershipIds = new ArrayList<>();
        for (String idString : idTokens) {
            membershipIds.add(ParserUtil.parseMembershipId(idString));
        }

        return new DeleteCommand(membershipIds);
    }
}
