package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MembershipIdTest {

    @Test
    public void isValidMembershipId_validBoundaries_returnsTrue() {
        assertTrue(MembershipId.isValidMembershipId(MembershipId.MIN_ID));
        assertTrue(MembershipId.isValidMembershipId(MembershipId.MAX_ID));
    }

    @Test
    public void isValidMembershipId_outsideBoundaries_returnsFalse() {
        assertFalse(MembershipId.isValidMembershipId(MembershipId.MIN_ID - 1));
        assertFalse(MembershipId.isValidMembershipId(MembershipId.MAX_ID + 1));
    }

    @Test
    public void equals_nonMembershipId_returnsFalse() {
        MembershipId membershipId = new MembershipId(MembershipId.MIN_ID);

        assertFalse(membershipId.equals("not membership id"));
    }
}

