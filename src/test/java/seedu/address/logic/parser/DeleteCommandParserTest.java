package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.person.MembershipId;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "id/1000" and "id/1000 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, " id/1000", new DeleteCommand(new MembershipId(MembershipId.MIN_ID)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no prefix
        assertParseFailure(parser, "1000",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        // invalid Id format should be rejected
        assertParseFailure(parser, " id/+1000", MembershipId.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " id/0001000", MembershipId.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidIdFormat_throwsParseException() {
        // non-integer after prefix
        assertParseFailure(parser, " id/abc",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_outOfRangeId_throwsParseException() {
        // valid integer but out of 1000-9999 range
        assertParseFailure(parser, " id/999", MembershipId.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        // empty input
        assertParseFailure(parser, "",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

}
