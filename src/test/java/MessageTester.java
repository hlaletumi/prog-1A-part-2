/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.part2.st10513583.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */

public class MessageTester {

    // Test data 1 and 2 from the brief (IDs are fixed here so the hashes are predictable)
    private Message msg1;
    private Message msg2;

    @BeforeEach
    public void setUp() {
        Message.resetMessages();
        msg1 = new Message("0012345678", "+27718693002", "Hi Molome, can you join us for dinner tonight?", 0);
        msg2 = new Message("0812345678", "08575975889", "Hi Boitumelo, did you receive the payment?", 1);
    }

    // Message length - success
    @Test
    public void testMessageLengthSuccess() {
        assertEquals("Message ready to send.", msg1.checkMessageLength());
    }

    // Message length - failure (260 characters = 10 over)
    @Test
    public void testMessageLengthFailure() {
        String longText = "a".repeat(260);
        Message longMsg = new Message("0012345678", "+27718693002", longText, 0);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                longMsg.checkMessageLength());
    }

    // Recipient number - success
    @Test
    public void testRecipientNumberSuccess() {
        assertEquals("Cell phone number successfully captured.", msg1.checkRecipientCell());
    }

    // Recipient number - failure
    @Test
    public void testRecipientNumberFailure() {
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                msg2.checkRecipientCell());
    }

    // Message hash - Test Case 1
    @Test
    public void testMessageHashTestCase1() {
        assertEquals("00:0:HITONIGHT", msg1.createMessageHash());
    }

    // Message hash - remaining hashes tested in a loop
    @Test
    public void testMessageHashesInLoop() {
        String[] ids = {"0012345678", "0812345678"};
        String[] texts = {"Hi Molemo, can you join us for dinner tonight?",
                          "Hi Boitumelo, did you receive the payment?"};
        String[] expected = {"00:0:HITONIGHT", "08:1:HIPAYMENT"};

        for (int i = 0; i < ids.length; i++) {
            Message m = new Message(ids[i], "+27718693002", texts[i], i);
            assertEquals(expected[i], m.createMessageHash());
        }
    }

    // Message ID is created (ten characters or less)
    @Test
    public void testMessageIDCreated() {
        Message random = new Message("+27718693002", "Hello there", 0);
        assertTrue(random.checkMessageID());
        assertEquals("Message ID generated: " + random.getMessageID(), random.getMessageIDStatus());
    }

    // Message ID longer than ten characters is rejected
    @Test
    public void testMessageIDTooLong() {
        Message bad = new Message("00123456789", "+27718693002", "Hello there", 0);
        assertFalse(bad.checkMessageID());
    }

    // User selects Send Message
    @Test
    public void testSentMessageSend() {
        assertEquals("Message successfully sent.", msg1.SentMessage(1));
    }

    // User selects Disregard Message
    @Test
    public void testSentMessageDisregard() {
        assertEquals("Press 0 to delete the message.", msg2.SentMessage(2));
    }

    // User selects Store Message
    @Test
    public void testSentMessageStore() {
        assertEquals("Message successfully stored.", msg1.SentMessage(3));
    }

    // Total sent: message 1 sent, message 2 discarded = 1
    @Test
    public void testReturnTotalMessages() {
        msg1.SentMessage(1);
        msg2.SentMessage(2);
        assertEquals(1, msg1.returnTotalMessagess());
    }

    // printMessages shows the sent message details
    @Test
    public void testPrintMessages() {
        msg1.SentMessage(1);
        assertTrue(msg1.printMessages().contains("00:0:HITONIGHT"));
    }
}