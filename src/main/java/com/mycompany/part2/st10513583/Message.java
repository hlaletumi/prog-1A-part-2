/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part2.st10513583;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Message class - handles a single QuickChat message plus the
 * lists/counters shared by all messages while the program runs.
 */
public class Message {

    public static final String CELL_OK = "Cell phone number successfully captured.";
    public static final String CELL_BAD = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    public static final String LENGTH_OK = "Message ready to send.";

    // Shared across all messages while the program is running
    private static final ArrayList<Message> sentMessages = new ArrayList<>();
    private static final ArrayList<Message> storedMessages = new ArrayList<>();
    private static int totalMessagesSent = 0;

    static String returnTotalMessages() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    static boolean isValidRecipient(String strRecipient) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    static boolean isValidLength(String strText) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private final String messageID;     // kept as a String so a leading 0 is not lost
    private final int messageNumber;    // starts at 0 and goes up per message
    private final String recipient;
    private final String message;
    private final String messageHash;

    // Normal constructor - the message ID is randomly generated
    public Message(String recipient, String message, int messageNumber) {
        this(generateRandomID(), recipient, message, messageNumber);
    }

    // Constructor with a given ID (used by the unit tests so the hash is predictable)
    public Message(String messageID, String recipient, String message, int messageNumber) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.message = message;
        this.messageNumber = messageNumber;
        this.messageHash = createMessageHash();
    }

    // Generates a random ten-digit number
    private static String generateRandomID() {
        long number = (long) (new Random().nextDouble() * 10000000000L);
        return String.format("%010d", number);
    }

    // Ensures the message ID is not more than ten characters
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    // Returns the message ID creation text
    public String getMessageIDStatus() {
        return "Message ID generated: " + messageID;
    }

    // Ensures the message is not more than 250 characters
    public String checkMessageLength() {
        if (message.length() <= 250) {
            return LENGTH_OK;
        }
        int over = message.length() - 250;
        return "Message exceeds 250 characters by " + over + "; please reduce the size.";
    }

    // Ensures the recipient has the international code (+27) and is correctly formatted
    // (same rule as the cell number check in the Login class)
    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("^\\+27[0-9]{1,10}$")) {
            return CELL_OK;
        }
        return CELL_BAD;
    }

    // Creates the hash: first two ID digits : message number : FIRSTLAST (all caps)
    public String createMessageHash() {
        String[] words = message.trim().split("\\s+");
        String first = words[0].replaceAll("[^A-Za-z0-9]", "");
        String last = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        return (messageID.substring(0, 2) + ":" + messageNumber + ":" + first + last).toUpperCase();
    }

    // option 1 = send, 2 = disregard, 3 = store to send later
    public String SentMessage(int option) {
        switch (option) {
            case 1:
                sentMessages.add(this);
                totalMessagesSent++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    // Full details of one message: ID, Hash, Recipient, Message
    public String getMessageDetails() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + message;
    }

    // Returns all messages sent while the program is running
    public String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) {
            sb.append(m.getMessageDetails()).append("\n\n");
        }
        return sb.toString();
    }

    // Returns the total number of messages sent
    public int returnTotalMessagess() {
        return totalMessagesSent;
    }

    // Stores all stored messages in a JSON file (written by hand, no library needed)
    public void storeMessage() {
        storedMessages.add(this);
        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            json.append("  {\n")
                .append("    \"messageID\": \"").append(escape(m.messageID)).append("\",\n")
                .append("    \"messageHash\": \"").append(escape(m.messageHash)).append("\",\n")
                .append("    \"recipient\": \"").append(escape(m.recipient)).append("\",\n")
                .append("    \"message\": \"").append(escape(m.message)).append("\"\n")
                .append("  }").append(i < storedMessages.size() - 1 ? "," : "").append("\n");
        }
        json.append("]");
        try (FileWriter writer = new FileWriter("messages.json")) {
            writer.write(json.toString());
        } catch (IOException e) {
            System.out.println("Could not write messages.json: " + e.getMessage());
        }
    }

    // Escapes characters that would break the JSON text
    private static String escape(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"")
                   .replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }

    // Clears the shared lists/counter (used by the unit tests)
    public static void resetMessages() {
        sentMessages.clear();
        storedMessages.clear();
        totalMessagesSent = 0;
    }

    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }

    boolean messageIDGenerated() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    boolean sentMessage(int intChoice) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}