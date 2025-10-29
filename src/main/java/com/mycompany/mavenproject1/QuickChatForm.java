/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 * QuickChatForm.java
 * Final PoE Project — ST10500239
 * Author: [Dennisious Moima]
 * Description: GUI app for sending, storing, and managing chat messages.
 */

package com.mycompany.mavenproject1;

    
    import javax.swing.JOptionPane;
    import java.util.ArrayList;
    import java.util.List;
    import com.mycompany.mavenproject1.Message;
    import com.mycompany.mavenproject1.MessageStore;
    import com.mycompany.mavenproject1.LogInForm;

public class QuickChatForm extends javax.swing.JFrame {

private String currentUser;

// Arrays to store categorized messages for reporting and search
List<Message> sentMessages = new ArrayList<>();        //for all sent messages
private List<Message> disregardedMessages = new ArrayList<>();//  forall disregarded messages
List<Message> storedMessages = new ArrayList<>();      // messages loaded from / saved to JSON
private List<String> messageHashes = new ArrayList<>();       // all message hashes
private List<String> messageIDs = new ArrayList<>();          // all message IDs
private MessageStore store;
private int maxMessages = 0;  
private int messageCounter = 0;
   





public List<Message> getSentMessages() {
    return sentMessages;
}

public List<Message> getStoredMessages() {
    return storedMessages;
}

public List<Message> getDisregardedMessages() {
    return disregardedMessages;
}

public List<String> getMessageIDs() {
    return messageIDs;
}

public List<String> getMessageHashes() {
    return messageHashes;
}
    public QuickChatForm() {
      initComponents();
}
    
public String showLongestSentMessage() {
    Message longest = null;

    // Check sent messages
    for (Message m : sentMessages) {
        if (longest == null || (m.getText() != null && m.getText().length() > longest.getText().length())) {
            longest = m;
        }
    }

    // Check stored messages
    for (Message m : storedMessages) {
        if (longest == null || (m.getText() != null && m.getText().length() > longest.getText().length())) {
            longest = m;
        }
    }

    return longest != null ? longest.getText() : "";
}

public String searchMessageByID(String id) {
    for (Message m : sentMessages) {
        if (m.getMessageID().equals(id)) return m.getText();
    }
    for (Message m : storedMessages) {
        if (m.getMessageID().equals(id)) return m.getText();
    }
    for (Message m : disregardedMessages) {
        if (m.getMessageID().equals(id)) return m.getText();
    }
    return null;
}

public String[] searchMessagesByRecipient(String recipient) {
    java.util.List<String> results = new java.util.ArrayList<>();
    for (Message m : sentMessages) {
        if (m.getRecipient().equals(recipient)) results.add(m.getText());
    }
    for (Message m : storedMessages) {
        if (m.getRecipient().equals(recipient)) results.add(m.getText());
    }
    for (Message m : disregardedMessages) {
        if (m.getRecipient().equals(recipient)) results.add(m.getText());
    }
    return results.toArray(new String[0]);
}

public String deleteMessageByHash(String hash) {
    java.util.Iterator<Message> it = storedMessages.iterator();
    while (it.hasNext()) {
        Message m = it.next();
        if (m.getMessageHash().equals(hash)) {
            it.remove();
            return "Message \"" + m.getText() + "\" successfully deleted.";
        }
    }
    return "Message not found.";
}

public String displayReport() {
    StringBuilder report = new StringBuilder();
    for (Message m : sentMessages) {
        report.append("Hash: ").append(m.getMessageHash())
              .append(", Recipient: ").append(m.getRecipient())
              .append(", Message: ").append(m.getText()).append("\n");
    }
    return report.toString();
}
// Display a simple report of sent messages (hash, recipient, message)
private void displayReports() {
    StringBuilder sb = new StringBuilder();
    sb.append("SENT MESSAGES REPORT\n\n");

    if (sentMessages.isEmpty()) {
        sb.append("No sent messages.\n");
        JOptionPane.showMessageDialog(this, sb.toString());
        return;
    }

    for (Message m : sentMessages) {
        sb.append("Message Hash: ").append(m.getMessageHash()).append("\n")
          .append("Message ID: ").append(m.getMessageID()).append("\n")
          .append("Recipient: ").append(m.getRecipient()).append("\n")
          .append("Message: ").append(m.getText()).append("\n")
          .append("----------------------------\n");
    }
    JOptionPane.showMessageDialog(this, sb.toString());
}

// returns the longest message text among sentMessages or storedMessages
private String findLongestMessageInSent() {
    String longest = "";
    for (Message m : sentMessages) {
        if (m.getText() != null && m.getText().length() > longest.length()) {
            longest = m.getText();
        }
    }
    return longest;
}



// Delete a message using message hash (search in sent + stored; remove from lists and save stored again)
private void deleteByHash() {
    String hash = JOptionPane.showInputDialog(this, "Enter Message Hash to delete (e.g. 00:1:HELLOYOU):");
    if (hash == null || hash.isEmpty()) return;

    // try remove from sentMessages
    for (int i = 0; i < sentMessages.size(); i++) {
        if (sentMessages.get(i).getMessageHash().equalsIgnoreCase(hash)) {
            String msgText = sentMessages.get(i).getText();
            messageIDs.remove(sentMessages.get(i).getMessageID());
            messageHashes.remove(sentMessages.get(i).getMessageHash());
            sentMessages.remove(i);
            JOptionPane.showMessageDialog(this, "Message \"" + msgText + "\" successfully deleted.");
            return;
        }
    }

    // try remove from storedMessages
    for (int i = 0; i < storedMessages.size(); i++) {
        if (storedMessages.get(i).getMessageHash().equalsIgnoreCase(hash)) {
            String msgText = storedMessages.get(i).getText();
            messageIDs.remove(storedMessages.get(i).getMessageID());
            messageHashes.remove(storedMessages.get(i).getMessageHash());
            storedMessages.remove(i);
            // re-save stored messages JSON
            MessageStore.saveMessages(storedMessages);
            JOptionPane.showMessageDialog(this, "Message \"" + msgText + "\" successfully deleted.");
            return;
        }
    }

    JOptionPane.showMessageDialog(this, "Message hash not found.");
    
    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this message?");
if (confirm == JOptionPane.YES_OPTION) {
    // proceed
}
    
}

//Search by Message ID: finds a message and shows recipient + message text
private void searchByMessageID() {
    String id = JOptionPane.showInputDialog(this, "Enter Message ID to search:");
    if (id == null || id.isEmpty()) return;

    for (Message m : sentMessages) {
        if (m.getMessageID().equals(id)) {
            JOptionPane.showMessageDialog(this,
                "Message found!\nRecipient: " + m.getRecipient() +
                "\nMessage: " + m.getText());
            return;
        }
    }
    for (Message m : storedMessages) {
        if (m.getMessageID().equals(id)) {
            JOptionPane.showMessageDialog(this,
                "Message found in stored messages!\nRecipient: " + m.getRecipient() +
                "\nMessage: " + m.getText());
            return;
        }
    }
    JOptionPane.showMessageDialog(this, "Message ID not found.");
}


//Search by Recipient: shows all messages sent to a specific number
private void searchByRecipient() {
    String recip = JOptionPane.showInputDialog(this, "Enter recipient number to search:");
    if (recip == null || recip.isEmpty()) return;

    StringBuilder sb = new StringBuilder();
    for (Message m : sentMessages) {
        if (recip.equals(m.getRecipient())) {
            sb.append(m.getText()).append("\n");
        }
    }
    for (Message m : storedMessages) {
        if (recip.equals(m.getRecipient())) {
            sb.append(m.getText()).append("\n");
        }
    }

    if (sb.length() == 0) {
        JOptionPane.showMessageDialog(this, "No messages found for recipient: " + recip);
    } else {
        JOptionPane.showMessageDialog(this, "Messages for " + recip + ":\n" + sb.toString());
    }
}


    public QuickChatForm(String username) {
        initComponents();
        sentMessages = MessageStore.loadMessages();
        this.currentUser = username;
        // load any previously stored messages from JSON into storedMessages at startup
        storedMessages = MessageStore.loadMessages();

        // also populate messageIDs and messageHashes from loaded storedMessages (so the Arrays reflect JSON contents)
        for (Message m : storedMessages) {
    if (m.getMessageID() != null) messageIDs.add(m.getMessageID());
    if (m.getMessageHash() != null) messageHashes.add(m.getMessageHash());
}
        this.setTitle("QuickChat - Logged in as " + username);
        javax.swing.JOptionPane.showMessageDialog(this, "Welcome to QuickChat, ");
        boolean validInput = false;
    while(!validInput) {
        try {
            String input = JOptionPane.showInputDialog(this, "How many messages do you want to send?");
            if(input == null) input = "0";
            maxMessages = Integer.parseInt(input);
            if(maxMessages <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a positive number.");
            } else {
                validInput = true;
            }
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        }
    }
    showMenu();
}
    
    
    public void showMenu() {
    boolean running = true;
    while(running) {
    if(currentUser == null || currentUser.isEmpty()) {
    javax.swing.JOptionPane.showMessageDialog(this, "You must log in first!");
    return;
}
        String input = javax.swing.JOptionPane.showInputDialog(
            this,
          "Choose an option:\n" +
   "1) Send Messages\n" +
   "2) Show recently sent messages\n" +
   "3) View stored messages\n" +
   "4) Display reports\n" +
   "5) Search message by ID\n" +
   "6) Search messages by recipient\n" +
   "7) Quit",
   "QuickChat Menu",
    javax.swing.JOptionPane.QUESTION_MESSAGE
        );

        if(input == null) { 
            running = false;
            break;
        }

     switch (input) {
    case "1":
        sendMessages();
        break;
    case "2":
        showRecentMessages();
        break;
    case "3":
        showStoredMessages();
        break;
    case "4":
        displayReports();
        break;
    case "5":
        searchByMessageID();
        break;
    case "6":
        searchByRecipient();
        break;
    case "7":
        running = false;
        break;
    default:
        javax.swing.JOptionPane.showMessageDialog(this, "Invalid option. Please enter 1 - 7.");
}

}
}
 
private void showStoredMessages() {
    if (storedMessages.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No stored messages found.");
        return;
    }

    StringBuilder sb = new StringBuilder();
    for (Message m : storedMessages) {
        sb.append("Message ID: ").append(m.getMessageID()).append("\n")
          .append("Recipient: ").append(m.getRecipient()).append("\n")
          .append("Message: ").append(m.getText()).append("\n")
          .append("----------------------------\n");
    }

    JOptionPane.showMessageDialog(this, sb.toString(), "Stored Messages", JOptionPane.INFORMATION_MESSAGE);
}

private void showRecentMessages() {
    if (sentMessages.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No messages have been sent yet.");
        return;
    }

    StringBuilder sb = new StringBuilder();
    for (Message m : sentMessages) {
        sb.append("Message ID: ").append(m.getMessageID()).append("\n")
          .append("Recipient: ").append(m.getRecipient()).append("\n")
          .append("Message: ").append(m.getText()).append("\n")
          .append("----------------------------\n");
    }

    JOptionPane.showMessageDialog(this, sb.toString(), "Recently Sent Messages", JOptionPane.INFORMATION_MESSAGE);
}
private void sendMessages() {
    int count = sentMessages.size();
    while (count < maxMessages) {
        String recipient = JOptionPane.showInputDialog(this, "Enter recipient's number (with international code):");
        if (recipient == null) break; // user cancelled

        if (!recipient.matches("^\\+\\d{1,3}\\d{4,14}$")) {
            JOptionPane.showMessageDialog(this, "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
            continue;
        }

        String text = JOptionPane.showInputDialog(this, "Enter your message (max 250 characters):");
        if (text == null) break; // user cancelled

        if (text.length() > 250) {
            int excess = text.length() - 250;
            JOptionPane.showMessageDialog(this, "Message exceeds 250 characters by " + excess + ", please reduce size.");
            continue;
        }

        // create message
        count++;
        Message msg = new Message(count, recipient, text);

        // ask what to do next
        String option = JOptionPane.showInputDialog(this, "Choose an option:\n1) Send Message\n2) Disregard Message\n3) Store Message to send later");
        if (option == null) break;

        switch (option) {
            case "1":
                // send message -> add to sentMessages and register id/hash
                sentMessages.add(msg);
                messageIDs.add(msg.getMessageID());
                messageHashes.add(msg.getMessageHash());
                messageCounter = sentMessages.size();
                // show full details as requested by assignment
                JOptionPane.showMessageDialog(this, "Message sent\n\n" + msg.toString());
                break;

            case "2":
                disregardedMessages.add(msg);
                JOptionPane.showMessageDialog(this, "Message disregarded ❌");
                // do not increment count of sent messages
                count--; // to let user still enter up to maxMessages sent messages
                break;

            case "3":
                storedMessages.add(msg);
                // persist the stored list to JSON
                MessageStore.saveMessages(storedMessages);
                // record its id and hash too
                messageIDs.add(msg.getMessageID());
                messageHashes.add(msg.getMessageHash());
                JOptionPane.showMessageDialog(this, "Message stored ✅");
                break;

            default:
                // treat as send by default
                sentMessages.add(msg);
                messageIDs.add(msg.getMessageID());
                messageHashes.add(msg.getMessageHash());
                JOptionPane.showMessageDialog(this, "Invalid option; message sent by default.\n\n" + msg.toString());
        }
    }

    // after loop - show total number of messages sent (requirement 6)
    JOptionPane.showMessageDialog(this, "Total number of messages sent: " + sentMessages.size());
}


    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JOptionPane.showMessageDialog(null, "Please login first!");
                new LogInForm().setVisible(true);
}
});
}




    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
/*
 * Portions of this code were developed with assistance from ChatGPT (OpenAI).
 * ChatGPT provided guidance on code structure, debugging, and test design.
 * Date: October 2025
 */