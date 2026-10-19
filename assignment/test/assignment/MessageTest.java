package assignment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessageTest {

    // Test data from the brief
    private static final String RECIPIENT_1 = "+27718693002";
    private static final String MESSAGE_1 = "Hi Mike, can you join us for dinner tonight?";
    private static final String RECIPIENT_2 = "08575975889";
    private static final String MESSAGE_2 = "Hi Keegan, did you receive the payment?";

    @BeforeEach
    void resetSharedState() {
        Message.resetState();
    }

    // ---------- Message length ----------

    @Test
    void messageLengthSuccess() {
        assertEquals("Message ready to send.", Message.checkMessageLength(MESSAGE_1));
    }

    @Test
    void messageLengthFailure() {
        String tooLong = "a".repeat(260);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                Message.checkMessageLength(tooLong));
    }

    // ---------- Recipient number ----------

    @Test
    void recipientNumberSuccess() {
        Message m = new Message("0000000001", 0, RECIPIENT_1, MESSAGE_1);
        assertEquals("Cell phone number successfully captured.", m.checkRecipientCell());
    }

    @Test
    void recipientNumberFailure() {
        Message m = new Message("0000000002", 1, RECIPIENT_2, MESSAGE_2);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                m.checkRecipientCell());
    }

    // ---------- Message hash (tested in a loop) ----------

    @Test
    void messageHashIsCorrect() {
        String[] ids = {"0000000001", "0000000002"};
        String[] recipients = {RECIPIENT_1, RECIPIENT_2};
        String[] messages = {MESSAGE_1, MESSAGE_2};
        String[] expected = {"00:0:HITONIGHT", "00:1:HIPAYMENT"};

        for (int i = 0; i < messages.length; i++) {
            Message m = new Message(ids[i], i, recipients[i], messages[i]);
            assertEquals(expected[i], m.createMessageHash());
        }
    }

    // ---------- Message ID ----------

    @Test
    void messageIDIsCreated() {
        Message m = new Message(0, RECIPIENT_1, MESSAGE_1);
        assertEquals("Message ID generated: " + m.getMessageID(), m.messageIDGenerated());
        assertEquals(10, m.getMessageID().length());
        assertTrue(m.getMessageID().matches("\\d{10}"));
        assertTrue(m.checkMessageID());
    }

    // ---------- Send / Disregard / Store ----------

    @Test
    void sendMessageOption() {
        Message m = new Message("0000000001", 0, RECIPIENT_1, MESSAGE_1);
        assertEquals("Message successfully sent.", m.sentMessage(1));
    }

    @Test
    void disregardMessageOption() {
        Message m = new Message("0000000002", 1, RECIPIENT_2, MESSAGE_2);
        assertEquals("Press 0 to delete the message.", m.sentMessage(2));
    }

    @Test
    void storeMessageOption() {
        Message m = new Message("0000000003", 2, RECIPIENT_1, MESSAGE_1);
        assertEquals("Message successfully stored.", m.sentMessage(3));
    }

    // ---------- Totals ----------

    @Test
    void totalOnlyCountsSentMessages() {
        Message sent = new Message("0000000001", 0, RECIPIENT_1, MESSAGE_1);
        Message discarded = new Message("0000000002", 1, RECIPIENT_2, MESSAGE_2);
        sent.sentMessage(1);
        discarded.sentMessage(2);
        assertEquals(1, Message.returnTotalMessages());
        assertTrue(Message.printMessages().contains(MESSAGE_1));
        assertFalse(Message.printMessages().contains(MESSAGE_2));
    }
}
