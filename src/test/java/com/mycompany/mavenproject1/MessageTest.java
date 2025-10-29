
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;
/**
 *
 * @author RC_Student_Lab
 */
 /**
 * QuickChatForm.java
 * Final PoE Project — ST10500239
 * Author: [Dennisious Moima]
 * Description: GUI app for sending, storing, and managing chat messages.
 */


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessageTest {

    QuickChatForm chat;
    Message m1, m2, m3, m4, m5;

    @BeforeEach
    void setUp() {
        chat = new QuickChatForm();

        // Test Data Message 1 (Sent)
        m1 = new Message(1, "+27834557896", "Did you get the cake?");
        chat.getSentMessages().add(m1);
        chat.getMessageIDs().add(m1.getMessageID());
        chat.getMessageHashes().add(m1.getMessageHash());

        // Test Data Message 2 (Stored)
        m2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        chat.getStoredMessages().add(m2);
        chat.getMessageIDs().add(m2.getMessageID());
        chat.getMessageHashes().add(m2.getMessageHash());

        // Test Data Message 3 (Disregard)
        m3 = new Message(3, "+27834484567", "Yohoooo, I am at your gate.");
        chat.getDisregardedMessages().add(m3);

        // Test Data Message 4 (Sent)
        m4 = new Message(4, "0838884567", "It is dinner time !");
        chat.getSentMessages().add(m4);
        chat.getMessageIDs().add(m4.getMessageID());
        chat.getMessageHashes().add(m4.getMessageHash());

        // Test Data Message 5 (Stored)
        m5 = new Message(5, "+27838884567", "Ok, I am leaving without you.");
        chat.getStoredMessages().add(m5);
        chat.getMessageIDs().add(m5.getMessageID());
        chat.getMessageHashes().add(m5.getMessageHash());
    }

    @Test
    void testSentMessagesPopulated() {
        assertEquals(2, chat.getSentMessages().size());
        assertEquals("Did you get the cake?", chat.getSentMessages().get(0).getText());
        assertEquals("It is dinner time !", chat.getSentMessages().get(1).getText());
    }

    @Test
    void testLongestMessage() {
        // Longest message should be m2 (stored message)
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                     chat.showLongestSentMessage());
    }

    @Test
    void testSearchByMessageID() {
        assertEquals("It is dinner time !", chat.searchMessageByID(m4.getMessageID()));
    }

    @Test
    void testSearchByRecipient() {
        String[] messages = chat.searchMessagesByRecipient("+27838884567");
        assertEquals(2, messages.length);
        assertTrue(messages[0].equals("Where are you? You are late! I have asked you to be on time.") ||
                   messages[0].equals("Ok, I am leaving without you."));
        assertTrue(messages[1].equals("Where are you? You are late! I have asked you to be on time.") ||
                   messages[1].equals("Ok, I am leaving without you."));
    }

    @Test
    void testDeleteByHash() {
        String hash = m2.getMessageHash();
        String result = chat.deleteMessageByHash(hash);
        assertTrue(result.contains("successfully deleted"));
        assertFalse(chat.getStoredMessages().contains(m2));
    }

    @Test
    void testDisplayReport() {
        String report = chat.displayReport();
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains("It is dinner time !"));
        // Should NOT contain disregarded or stored messages
        assertFalse(report.contains("Where are you? You are late!"));
        assertFalse(report.contains("Ok, I am leaving without you."));
    }
}

/*
 * Portions of this code were developed with assistance from ChatGPT (OpenAI).
 * ChatGPT provided guidance on code structure, debugging, and test design.
 * Date: October 2025
 */