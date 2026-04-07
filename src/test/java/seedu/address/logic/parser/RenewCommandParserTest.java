package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.RenewCommand;
import seedu.address.model.person.MembershipId;

public class RenewCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenewCommand.MESSAGE_USAGE);

    private final RenewCommandParser parser = new RenewCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, " id/1001 d/7",
                new RenewCommand(new MembershipId(1001), 7));
        assertParseSuccess(parser, " id/1001 d/730",
                new RenewCommand(new MembershipId(1001), 730));

    }

    @Test
    public void parse_missingParts_failure() {
        // missing id
        assertParseFailure(parser, " d/7", MESSAGE_INVALID_FORMAT);

        // missing days
        assertParseFailure(parser, " id/1001", MESSAGE_INVALID_FORMAT);

        // both missing
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, " junk id/1001 d/7", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidMembershipId_failure() {
        assertParseFailure(parser, " id/+1001 d/7", MembershipId.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " id/0001001 d/7", MembershipId.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " id/999 d/7", MembershipId.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " id/10000 d/7", MembershipId.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidDays_failure() {
        assertParseFailure(parser, " id/1001 d/abc", RenewCommand.MESSAGE_INVALID_DAYS);
        assertParseFailure(parser, " id/1001 d/0", RenewCommand.MESSAGE_INVALID_DAYS);
        assertParseFailure(parser, " id/1001 d/-1", RenewCommand.MESSAGE_INVALID_DAYS);
        assertParseFailure(parser, " id/1001 d/+7", RenewCommand.MESSAGE_INVALID_DAYS);
        assertParseFailure(parser, " id/1001 d/07", RenewCommand.MESSAGE_INVALID_DAYS);
        assertParseFailure(parser, " id/1001 d/7.5", RenewCommand.MESSAGE_INVALID_DAYS);
        assertParseFailure(parser, " id/1001 d/731", RenewCommand.MESSAGE_INVALID_DAYS);
    }

    @Test
    public void parse_repeatedPrefixes_failure() {
        assertParseFailure(parser, " id/1001 id/1002 d/7",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ID));

        assertParseFailure(parser, " id/1001 d/7 d/8",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DAYS));

        assertParseFailure(parser, " id/1001 id/1002 d/7 d/8",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ID, PREFIX_DAYS));
    }

    @Test
    public void parse_emptyValues_failure() {
        // empty id
        assertParseFailure(parser, " id/ d/7", MembershipId.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " d/7 id/", MembershipId.MESSAGE_CONSTRAINTS);

        // empty days
        assertParseFailure(parser, " id/1001 d/", RenewCommand.MESSAGE_INVALID_DAYS);
        assertParseFailure(parser, " d/ id/1001", RenewCommand.MESSAGE_INVALID_DAYS);

        // both empty
        assertParseFailure(parser, " id/ d/", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, " d/ id/", MESSAGE_INVALID_FORMAT);
    }
}

