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
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        // No prefix
        assertParseFailure(parser, " desc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        // Unsupported prefix
        assertParseFailure(parser, " x/asc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_multiplePrefixes_throwsParseException() {
        // Multiple different prefixes
        assertParseFailure(parser, " n/asc id/desc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        // Multiple identical prefixes
        assertParseFailure(parser, " n/asc n/desc", getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
    }

    @Test
    public void parse_invalidOrder_throwsParseException() {
        // Invalid order
        assertParseFailure(parser, " n/invalid", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        // None (with prefix)
        assertParseFailure(parser, " n/none", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_none_returnsSortCommand() {
        SortCommand expectedNoPrefixNone = new SortCommand(null, "none", "none");
        assertParseSuccess(parser, " none", expectedNoPrefixNone);
        assertParseSuccess(parser, "\t none \n", expectedNoPrefixNone);
    }

    @Test
    public void parse_validPrefix_returnsSortCommand() {
        // Ascending
        SortCommand expectedAsc = new SortCommand(
                PersonComparators.NAME_ASC, "n/", "asc");
        assertParseSuccess(parser, " n/asc", expectedAsc);

        // Descending
        SortCommand expectedDesc = new SortCommand(
                PersonComparators.NAME_DESC, "n/", "desc");
        assertParseSuccess(parser, " n/desc", expectedDesc);
    }

    @Test
    public void parse_whitespace_returnsSortCommand() {
        SortCommand expectedAsc = new SortCommand(
                PersonComparators.NAME_ASC, "n/", "asc");

        // Leading space
        assertParseSuccess(parser, "  n/asc", expectedAsc);
        // Trailing space
        assertParseSuccess(parser, " n/asc ", expectedAsc);
        // Both leading and trailing space
        assertParseSuccess(parser, "  n/asc ", expectedAsc);

        // Tabs and newlines
        assertParseSuccess(parser, "\t n/asc", expectedAsc);
        assertParseSuccess(parser, " n/asc\n", expectedAsc);
        assertParseSuccess(parser, " n/\nasc", expectedAsc);
        assertParseSuccess(parser, " n/\tasc", expectedAsc);
        assertParseSuccess(parser, "\t\n n/asc \n\t", expectedAsc);
    }
}