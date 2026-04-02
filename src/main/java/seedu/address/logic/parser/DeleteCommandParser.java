package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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

        if (!argMultimap.getValue(PREFIX_ID).isPresent() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        String idString = argMultimap.getValue(PREFIX_ID).get().trim();

        int idValue;
        try {
            idValue = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        // Reject invalid membership id numeric forms such as "+1000" or "0001000"
        if (!idString.equals(Integer.toString(idValue)) || !MembershipId.isValidMembershipId(idValue)) {
            throw new ParseException(MembershipId.MESSAGE_CONSTRAINTS);
        }

        return new DeleteCommand(new MembershipId(idValue));
    }
}
