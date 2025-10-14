/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.mavenproject1;
/**
 *
 * @author RC_Student_Lab
 */
    
    import javax.swing.JOptionPane;
    import java.util.ArrayList;
    import java.util.List;
    import com.mycompany.mavenproject1.Message;
    import com.mycompany.mavenproject1.MessageStore;
    import com.mycompany.mavenproject1.LogInForm;

public class QuickChatForm extends javax.swing.JFrame {

private String currentUser;   
private List<Message> sentMessages = new ArrayList<>();
private int maxMessages = 0;  
private int messageCounter = 0;

   
    public QuickChatForm() {
      initComponents();
}

    public QuickChatForm(String username) {
        initComponents();
        sentMessages = MessageStore.loadMessages();
        this.currentUser = username;
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
            "3) Quit",
            "QuickChat Menu",
            javax.swing.JOptionPane.QUESTION_MESSAGE
        );

        if(input == null) { 
            running = false;
            break;
        }

        switch(input) {
            case "1":
           sendMessages();
                break;
            case "2":
                javax.swing.JOptionPane.showMessageDialog(this, "Coming Soon.");
                break;
            case "3":
                running = false;
                break;
            default:
                javax.swing.JOptionPane.showMessageDialog(this, "Invalid option. Please enter 1, 2, or 3.");
        }
}
}
 


private void sendMessages() {    
      int count = sentMessages.size();
    while(count < maxMessages) {
        String recipient = JOptionPane.showInputDialog(this, "Enter recipient's number (with international code):");
        if(recipient == null) break;

        if(!recipient.matches("^\\+\\d{1,3}\\d{4,14}$")) { // simple validation
            JOptionPane.showMessageDialog(this, "Invalid number format ❌");
            continue;
        }
  

        String text = JOptionPane.showInputDialog(this, "Enter your message (max 250 characters):");
        if(text == null) break;

     if(text.length() > 50) {
    JOptionPane.showMessageDialog(this, "Please enter a message of less than 250 characters ❌");
    continue;
        }

        count++;
        Message msg = new Message(count, recipient, text);
        sentMessages.add(msg);
       messageCounter = count;

   JOptionPane.showMessageDialog(this, "Message sent \nHash: " + msg.getMessageHash());

        // Ask what to do with the message
        String option = JOptionPane.showInputDialog(this, "Choose an option:\n1) Send Message\n2) Disregard Message\n3) Store Message to send later");
        if(option == null) break;

        switch(option) {
            case "1": // already sent
                break;
            case "2": // disregard
                sentMessages.remove(msg);
                JOptionPane.showMessageDialog(this, "Message disregarded ❌");
                count--;
                break;
            case "3": // store (optional: save to JSON later)
                  sentMessages.add(msg); // make sure it's added to the list
                  MessageStore.saveMessages(sentMessages); // save to JSON
                  JOptionPane.showMessageDialog(this, "Message stored ✅");
                break;
            default:
                JOptionPane.showMessageDialog(this, "Invalid option, message sent by default.");
        }
    }
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