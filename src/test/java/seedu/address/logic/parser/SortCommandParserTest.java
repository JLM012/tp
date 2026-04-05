package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes;
import static seedu.address.logic.commands.SortCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.model.util.PersonComparators;

public class SortCommandParserTest {
    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_invalidArgs_returnsParseException() {
        // No prefix
        assertParseFailure(parser, " desc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        // Multiple different prefixes
        assertParseFailure(parser, " n/asc id/desc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        // Multiple identical prefixes
        assertParseFailure(parser, " n/asc n/desc", getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
        // Invalid order
        assertParseFailure(parser, " n/invalid", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        // Unsupported prefix
        assertParseFailure(parser, " x/asc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortCommand() {
        // Ascending
        SortCommand expectedAsc = new SortCommand(
                PersonComparators.NAME_ASC, "n/", "asc");
        assertParseSuccess(parser, " n/asc", expectedAsc);
        // Descending
        SortCommand expectedDesc = new SortCommand(
                PersonComparators.NAME_DESC, "n/", "desc");
        assertParseSuccess(parser, " n/desc", expectedDesc);
        // None (restore)
        SortCommand expectedNone = new SortCommand(
                null, "n/", "none");
        assertParseSuccess(parser, " n/none", expectedNone);

        // Leading space
        assertParseSuccess(parser, "  n/asc", expectedAsc);
        // Trailing space
        assertParseSuccess(parser, " n/asc ", expectedAsc);
        // Both leading and trailing space
        assertParseSuccess(parser, "  n/asc ", expectedAsc);
        // Multiple prefixes (should throw ParseException due to verifyNoDuplicatePrefixesFor)
        assertParseFailure(parser, " n/asc n/desc", getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
    }
}