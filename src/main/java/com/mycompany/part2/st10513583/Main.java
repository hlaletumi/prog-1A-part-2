package com.mycompany.part2.st10513583;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

import java.util.Scanner;

/**
 * Main class - registration, login, then the QuickChat menu.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try (scanner) {
            Login login = new Login();
            
            // ---------- Registration ----------
            System.out.println("=== User Registration ===");
            
            System.out.print("Enter first name: ");
            login.setFirstName(scanner.nextLine());
            
            System.out.print("Enter last name: ");
            login.setLastName(scanner.nextLine());
            
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            
            System.out.print("Enter South African cell phone number (e.g. +27838968976): ");
            String cellPhoneNumber = scanner.nextLine();
            
            System.out.println(login.registerUser(username, password, cellPhoneNumber));
            
            // getUsername() is only set when registration succeeded
            if (login.getUsername() == null) {
                System.out.println("Registration failed. Please run the program again.");
                return;
            }
            
            // ---------- Login ----------
            System.out.println("\n=== Login ===");
            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();
            
            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();
            
            System.out.println(login.returnLoginStatus(loginUsername, loginPassword));
            
            // Only logged-in users may continue
            if (!login.loginUser(loginUsername, loginPassword)) {
                return;
            }
            
            // ---------- QuickChat ----------
            System.out.println("\nWelcome to QuickChat.");
            
            int maxMessages = readNumber("How many messages do you wish to enter? ");
            int entered = 0;
            Message tracker = new Message("0000000000", "+27", "x", 0); // used for totals only
            int choice = 0;
            
            while (choice != 3) {
                System.out.println("\n1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");
                choice = readNumber("Choose an option: ");
                
                if (choice == 1) {
                    if (entered >= maxMessages) {
                        System.out.println("You have already entered all " + maxMessages + " messages.");
                        continue;
                    }
                    while (entered < maxMessages) {
                        enterMessage(entered);
                        entered++;
                    }
                    System.out.println("\nTotal messages sent: " + tracker.returnTotalMessagess());
                } else if (choice == 2) {
                    System.out.println("Coming Soon.");
                } else if (choice != 3) {
                    System.out.println("Please choose option 1, 2 or 3.");
                }
            }
            
            System.out.println("\nTotal messages sent: " + tracker.returnTotalMessagess());
            System.out.println("Goodbye.");
        }
    }

    // Captures one message and lets the user send, disregard or store it
    private static void enterMessage(int messageNumber) {
        System.out.println("\n--- Message " + (messageNumber + 1) + " ---");

        // Recipient - repeat until correctly formatted
        String recipient;
        while (true) {
            System.out.print("Enter recipient cell number (e.g. +27718693002): ");
            recipient = scanner.nextLine();
            String cellResult = new Message(recipient, "x", messageNumber).checkRecipientCell();
            System.out.println(cellResult);
            if (cellResult.equals(Message.CELL_OK)) {
                break;
            }
        }

        // Message text - repeat until 250 characters or less
        String text;
        while (true) {
            System.out.print("Enter your message: ");
            text = scanner.nextLine();
            if (text.trim().isEmpty()) {
                System.out.println("The message cannot be empty.");
            } else if (text.length() > 250) {
                System.out.println("Please enter a message of less than 250 characters.");
            } else {
                System.out.println("Message sent");
                break;
            }
        }

        Message msg = new Message(recipient, text, messageNumber);
        System.out.println(msg.getMessageIDStatus());

        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        int option = readNumber("Choose an option: ");
        while (option < 1 || option > 3) {
            option = readNumber("Please choose 1, 2 or 3: ");
        }

        System.out.println(msg.SentMessage(option));

        if (option == 1) {
            System.out.println("\n" + msg.getMessageDetails());
        } else if (option == 2) {
            String answer = scanner.nextLine();
            if (answer.trim().equals("0")) {
                System.out.println("Message deleted.");
            }
        }
    }

    // Reads a whole number from the user, repeating until valid
    private static int readNumber(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}