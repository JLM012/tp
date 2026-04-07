package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.SortCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBERSHIP_EXPIRY_DATE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.model.util.PersonComparators;

public class SortCommandParserTest {
    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_nonEmptyPreamble_throwsParseException() {
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + " n/asc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

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

    // More tests for each prefix
    @Test
    public void parse_phonePrefix_returnsSortCommand() {
        // Phone ascending
        SortCommand expectedPhoneAsc = new SortCommand(
                PersonComparators.PHONE_ASC, "p/", "asc");
        assertParseSuccess(parser, " p/asc", expectedPhoneAsc);

        // Phone descending
        SortCommand expectedPhoneDesc = new SortCommand(
                PersonComparators.PHONE_DESC, "p/", "desc");
        assertParseSuccess(parser, " p/desc", expectedPhoneDesc);
    }

    @Test
    public void parse_emailPrefix_returnsSortCommand() {
        // Email ascending
        SortCommand expectedEmailAsc = new SortCommand(
                PersonComparators.EMAIL_ASC, "e/", "asc");
        assertParseSuccess(parser, " e/asc", expectedEmailAsc);

        // Email descending
        SortCommand expectedEmailDesc = new SortCommand(
                PersonComparators.EMAIL_DESC, "e/", "desc");
        assertParseSuccess(parser, " e/desc", expectedEmailDesc);
    }

    @Test
    public void parse_addressPrefix_returnsSortCommand() {
        // Address postal code ascending
        SortCommand expectedAddressAsc = new SortCommand(
                PersonComparators.ADDRESS_POSTAL_CODE_ASC, "a/", "asc");
        assertParseSuccess(parser, " a/asc", expectedAddressAsc);

        // Address postal code descending
        SortCommand expectedAddressDesc = new SortCommand(
                PersonComparators.ADDRESS_POSTAL_CODE_DESC, "a/", "desc");
        assertParseSuccess(parser, " a/desc", expectedAddressDesc);
    }

    @Test
    public void parse_idPrefix_returnsSortCommand() {
        // ID ascending
        SortCommand expectedIdAsc = new SortCommand(
                PersonComparators.ID_ASC, "id/", "asc");
        assertParseSuccess(parser, " id/asc", expectedIdAsc);

        // ID descending
        SortCommand expectedIdDesc = new SortCommand(
                PersonComparators.ID_DESC, "id/", "desc");
        assertParseSuccess(parser, " id/desc", expectedIdDesc);
    }

    @Test
    public void parse_membershipExpiryDatePrefix_returnsSortCommand() {
        // Membership expiry date ascending
        SortCommand expectedExpiryAsc = new SortCommand(
                PersonComparators.EXPIRY_DATE_ASC, "m/", "asc");
        assertParseSuccess(parser, " m/asc", expectedExpiryAsc);

        // Membership expiry date descending
        SortCommand expectedExpiryDesc = new SortCommand(
                PersonComparators.EXPIRY_DATE_DESC, "m/", "desc");
        assertParseSuccess(parser, " m/desc", expectedExpiryDesc);
    }
}
