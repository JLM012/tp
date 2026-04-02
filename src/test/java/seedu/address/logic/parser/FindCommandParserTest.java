package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.ExpiryDateContainsKeywordsPredicate;
import seedu.address.model.person.MembershipIdContainsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.PostalCodeContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_nonEmptyPreamble_throwsParseException() {
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + " n/Alice",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_singleKeyword_returnsFindCommand() {
        FindCommand expectedFindCommand =
            new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        assertParseSuccess(parser, " n/Alice", expectedFindCommand);
    }

    @Test
    public void parse_multipleKeywords_returnsFindCommand() {
        FindCommand expectedFindCommand =
            new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);
    }

    @Test
    public void parse_multipleKeywordsWithExtraSpaces_returnsFindCommand() {
        FindCommand expectedFindCommand =
            new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        // multiple spaces between keywords
        assertParseSuccess(parser, " n/Alice    Bob", expectedFindCommand);
        // leading and trailing whitespace
        assertParseSuccess(parser, " n/   Alice Bob   ", expectedFindCommand);
    }

    @Test
    public void parse_keywordsWithTabsNewlinesAndMixedWhitespace_returnsFindCommand() {
        FindCommand expectedFindCommand =
            new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        // tabs between keywords
        assertParseSuccess(parser, " n/Alice\tBob", expectedFindCommand);
        // newlines between keywords
        assertParseSuccess(parser, " n/Alice\nBob", expectedFindCommand);
        // mix of spaces, tabs, and newlines
        assertParseSuccess(parser, " n/ Alice \n \t Bob  ", expectedFindCommand);
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
        assertParseSuccess(parser, " p/91234567 98765432", expectedFindCommand);
    }

    @Test
    public void parse_multiplePrefixes_throwsParseException() {
        assertParseFailure(parser, " n/Alice p/98765432",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_postalCodeKeywords_returnsFindCommand() {
        FindCommand expectedFindCommandSingle =
            new FindCommand(new PostalCodeContainsKeywordsPredicate(Arrays.asList("123456")));
        assertParseSuccess(parser, " a/123456", expectedFindCommandSingle);

        FindCommand expectedFindCommandMultiple =
            new FindCommand(new PostalCodeContainsKeywordsPredicate(Arrays.asList("123456", "654321")));
        assertParseSuccess(parser, " a/123456 654321", expectedFindCommandMultiple);
    }

    @Test
    public void parse_expiryDateKeywords_returnsFindCommand() {
        FindCommand expectedFindCommandSingle =
            new FindCommand(new ExpiryDateContainsKeywordsPredicate(Arrays.asList("2026-12-31")));
        assertParseSuccess(parser, " m/2026-12-31", expectedFindCommandSingle);

        FindCommand expectedFindCommandMultiple =
            new FindCommand(new ExpiryDateContainsKeywordsPredicate(Arrays.asList("2026-12-31", "2027-01-01")));
        assertParseSuccess(parser, " m/2026-12-31 2027-01-01", expectedFindCommandMultiple);
    }

    @Test
    public void parse_emailKeywords_returnsFindCommand() {
        FindCommand expectedFindCommandSingle =
            new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList("alice@example.com")));
        assertParseSuccess(parser, " e/alice@example.com", expectedFindCommandSingle);

        FindCommand expectedFindCommandMultiple =
            new FindCommand(new EmailContainsKeywordsPredicate(Arrays
                .asList("alice@example.com", "bob@example.com")));
        assertParseSuccess(parser, " e/alice@example.com bob@example.com", expectedFindCommandMultiple);
    }

    @Test
    public void parse_commandWordOnly_throwsParseException() {
        assertParseFailure(parser, "",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_prefixOnly_throwsParseException() {
        assertParseFailure(parser, " n/",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_onlyWhitespaceAfterPrefix_throwsParseException() {
        assertParseFailure(parser, " n/    ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_singleIdKeyword_returnsFindCommand() {
        FindCommand expectedFindCommand =
            new FindCommand(new MembershipIdContainsPredicate(Arrays.asList("1000")));
        assertParseSuccess(parser, " id/1000", expectedFindCommand);
    }

    @Test
    public void parse_multipleIdKeywords_returnsFindCommand() {
        FindCommand expectedFindCommand =
            new FindCommand(new MembershipIdContainsPredicate(Arrays.asList("1000", "1001")));
        assertParseSuccess(parser, " id/1000 1001", expectedFindCommand);
    }

    @Test
    public void parse_singlePhoneKeyword_returnsFindCommand() {
        FindCommand expectedFindCommand =
            new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("91234567")));
        assertParseSuccess(parser, " p/91234567", expectedFindCommand);
    }

    @Test
    public void parse_singleEmailKeyword_returnsFindCommand() {
        FindCommand expectedFindCommand =
            new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList("alice@example.com")));
        assertParseSuccess(parser, " e/alice@example.com", expectedFindCommand);
    }

    @Test
    public void parse_singlePostalCodeKeyword_returnsFindCommand() {
        FindCommand expectedFindCommand =
            new FindCommand(new PostalCodeContainsKeywordsPredicate(Arrays.asList("123456")));
        assertParseSuccess(parser, " a/123456", expectedFindCommand);
    }

    @Test
    public void parse_singleExpiryDateKeyword_returnsFindCommand() {
        FindCommand expectedFindCommand =
            new FindCommand(new ExpiryDateContainsKeywordsPredicate(Arrays.asList("2026-12-31")));
        assertParseSuccess(parser, " m/2026-12-31", expectedFindCommand);
    }


}
