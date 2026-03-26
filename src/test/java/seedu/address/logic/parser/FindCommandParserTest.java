package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_singleKeyword_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        assertParseSuccess(parser, "find n/Alice", expectedFindCommand);
    }

    @Test
    public void parse_multipleKeywords_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "find n/Alice Bob", expectedFindCommand);
    }

    @Test
    public void parse_multipleKeywordsWithExtraSpaces_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        // multiple spaces between keywords
        assertParseSuccess(parser, "find n/Alice    Bob", expectedFindCommand);
        // leading and trailing whitespace
        assertParseSuccess(parser, "find n/   Alice Bob   ", expectedFindCommand);
    }

    @Test
    public void parse_keywordsWithTabsNewlinesAndMixedWhitespace_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        // tabs between keywords
        assertParseSuccess(parser, "find n/Alice\tBob", expectedFindCommand);
        // newlines between keywords
        assertParseSuccess(parser, "find n/Alice\nBob", expectedFindCommand);
        // mix of spaces, tabs, and newlines
        assertParseSuccess(parser, "find n/ Alice \n \t Bob  ", expectedFindCommand);
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_phoneKeywords_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("91234567", "98765432")));
        assertParseSuccess(parser, "find p/91234567 98765432", expectedFindCommand);
    }

    @Test
    public void parse_multiplePrefixes_throwsParseException() {
        assertParseFailure(parser, "find n/Alice p/98765432",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_commandWordOnly_throwsParseException() {
        assertParseFailure(parser, "find",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_prefixOnly_throwsParseException() {
        assertParseFailure(parser, "find n/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_onlyWhitespaceAfterPrefix_throwsParseException() {
        assertParseFailure(parser, "find n/    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

}
