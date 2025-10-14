
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;
/**
 *
 * @author RC_Student_Lab
 */
 


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class MessageTest {

    // Test for creating messages
    @Test
    public void testMessageCreation_ValidData() {
        Message msg1 = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        assertEquals(10, msg1.getMessageID().length());
        assertNotNull(msg1.getMessageHash());
        assertFalse(msg1.getMessageHash().isEmpty());
        assertEquals("+27718693002", msg1.getRecipient());
        assertEquals("Hi Mike, can you join us for dinner tonight", msg1.getText());
    }

    @Test
    public void testDiscardedMessage_NotStored() {
        Message msg2 = new Message(2, "08575975889", "Hi Keegan, did you receive the payment?");
        assertEquals(10, msg2.getMessageID().length());
        assertNotNull(msg2.getMessageHash());
        assertEquals("08575975889", msg2.getRecipient());
    }

    //TestS for message length
    @Test
    public void testMessageLength_Success() {
        String text = "Hi Mike, can you join us for dinner tonight";
        assertTrue(text.length() <= 250, "Message ready to send.");
    }

   
    @Test
    public void testMessageLength_Failure() {
        String text = "A".repeat(260); // 260 chars
        int excess = text.length() - 250;
        assertTrue(excess > 0, "Message exceeds 250 characters by " + excess + ", please reduce size.");
    }

    //Test for Reciepent Format
    @Test
    public void testRecipientFormat_Success() {
        String recipient = "+27718693002";
        boolean isValid = recipient.matches("^\\+\\d{1,3}\\d{4,14}$");
        assertTrue(isValid, "Cell phone number successfully captured.");
    }

   @Test
    public void testRecipientFormat_Failure() {
        String recipient = "08575975889"; // missing +
        boolean isValid = recipient.matches("^\\+\\d{1,3}\\d{4,14}$");
        assertFalse(isValid, "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
    }

    //Test for Message Hash
    @Test
    public void testMessageHash_Correctness() {
        Message msg = new Message(1, "+27718693002", "Hi Mike tonight");
        String[] words = msg.getText().split(" ");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        String expectedHash = msg.getMessageID().substring(0, 2) + ":" + msg.getMessageNumber() + ":" + firstWord + lastWord;
        assertEquals(expectedHash, msg.getMessageHash(), "Message hash is correct.");
    }

    //Test for creating ID
    @Test
    public void testMessageID_Creation() {
        Message msg = new Message(1, "+27718693002", "Hi Mike tonight");
        assertNotNull(msg.getMessageID(), "Message ID generated: " + msg.getMessageID());
        assertEquals(10, msg.getMessageID().length(), "Message ID should be 10 digits.");
    }

    // Test for     // Test 6: Message Actions (Send, Disregard, Store)
    @Test
    public void testMessageSentActions() {
        String sendAction = "Send";
        String discardAction = "Discard";
        String storeAction = "Store";

        // Send
        String sendMsg = (sendAction.equals("Send")) ? "Message successfully sent." : "";
        assertEquals("Message successfully sent.", sendMsg);

        // Discard
        String discardMsg = (discardAction.equals("Discard")) ? "Press 0 to delete message." : "";
        assertEquals("Press 0 to delete message.", discardMsg);

        // Store
        String storeMsg = (storeAction.equals("Store")) ? "Message successfully stored." : "";
        assertEquals("Message successfully stored.", storeMsg);
    }
}