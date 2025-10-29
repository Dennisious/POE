/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;
/**
 * QuickChatForm.java
 * Final PoE Project — ST10500239
 * Author: [Dennisious Moima]
 * Description: GUI app for sending, storing, and managing chat messages.
 */

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String text;
    private String messageHash;

    
    public Message() {
        // initialize with defaults to avoid nulls
        this.messageID = "";
        this.messageNumber = 0;
        this.recipient = "";
        this.text = "";
        this.messageHash = "";
    }

    // PARAMETERIZED constructor for normal usage
    public Message(int messageNumber, String recipient, String text) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.text = text;
        this.messageID = generateMessageID();
        this.messageHash = generateMessageHash();
    }

    private String generateMessageID() {
        long number = (long) (Math.random() * 1_000_000_0000L); // 10 digits
        return String.format("%010d", number);
    }

    private String generateMessageHash() {
        if (text == null || text.isEmpty()) return messageID.substring(0, 2) + ":" + messageNumber + ":";
        String[] words = text.split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        return (messageID.substring(0, 2) + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    // Public Classes
    public String getMessageID() { return messageID; }
    public void setMessageID(String messageID) { this.messageID = messageID; }

    public int getMessageNumber() { return messageNumber; }
    public void setMessageNumber(int messageNumber) { this.messageNumber = messageNumber; }

    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getMessageHash() { return messageHash; }
    public void setMessageHash(String messageHash) { this.messageHash = messageHash; }

    // Message validation methods
    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10;
    }

    public boolean checkRecipientCell() {
        if (recipient == null || !recipient.startsWith("+")) return false;
        String digitsOnly = recipient.substring(1);
        return digitsOnly.length() <= 10 && digitsOnly.matches("\\d+");
    }

    // Sent / stored / disregarded messages
    public String sentMessage(String action) {
        switch (action.toLowerCase()) {
            case "send":
                return "Message sent \n" + this.toString();
            case "disregard":
                return "Message disregarded ";
            case "store":
                return "Message stored (you can send it later) ";
            default:
                return "Invalid action. Message sent by default \n" + this.toString();
        }
    }

    @Override
    public String toString() {
        return "MessageID: " + messageID +
                "\nMessage Hash: " + messageHash +
                "\nRecipient: " + recipient +
                "\nMessage: " + text;
    }
}
