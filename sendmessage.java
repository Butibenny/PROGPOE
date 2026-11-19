package assignment;

import java.util.Scanner;

public class sendmessage {

    // Runs the QuickChat menu. Called from Assignment after a successful login.
    public void start(Scanner input) {
        System.out.println("\nWelcome to QuickChat.");

        int maxMessages = readInt(input, "How many messages do you wish to enter?");
        int entered = 0;
        boolean running = true;

        while (running) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            int choice = readInt(input, "Enter option number:");

            switch (choice) {
                case 1:
                    if (entered >= maxMessages) {
                        System.out.println("You have already entered all " + maxMessages + " messages.");
                    } else {
                        handleNewMessage(input, entered);
                        entered++;
                        if (entered == maxMessages) {
                            System.out.println("\nAll messages entered. Total messages sent: "
                                    + Message.returnTotalMessages());
                        }
                    }
                    break;
                case 2:
                    System.out.println("Coming Soon.");
                    break;
                case 3:
                    System.out.println("Total messages sent: " + Message.returnTotalMessages());
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please enter 1, 2 or 3.");
            }
        }
    }

    private void handleNewMessage(Scanner input, int messageNumber) {
        // Recipient
        String recipient;
        while (true) {
            System.out.println("Enter recipient cell number (e.g. +27718693002):");
            recipient = input.nextLine();
            Message check = new Message(messageNumber, recipient, "check");
            String cellResult = check.checkRecipientCell();
            System.out.println(cellResult);
            if (cellResult.startsWith("Cell phone number successfully")) {
                break;
            }
        }

        // Message text (max 250 characters)
        String text;
        while (true) {
            System.out.println("Enter your message:");
            text = input.nextLine();
            if (text.trim().isEmpty()) {
                System.out.println("A message cannot be empty.");
            } else if (text.length() > 250) {
                System.out.println("Please enter a message of less than 250 characters.");
                System.out.println(Message.checkMessageLength(text));
            } else {
                System.out.println("Message ready to send.");
                break;
            }
        }

        Message message = new Message(messageNumber, recipient, text);
        System.out.println(message.messageIDGenerated());

        System.out.println("What would you like to do with this message?");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        int action = readInt(input, "Enter option number:");

        String result = message.sentMessage(action);
        System.out.println(result);

        if (action == 1) {
            System.out.println("\n--- Message details ---");
            System.out.println(message.getDetails());
        } else if (action == 2) {
            String zero = input.nextLine().trim();
            System.out.println(zero.equals("0") ? "Message deleted." : "Message disregarded.");
        }
    }

    // Keeps asking until the user types a whole number
    private int readInt(Scanner input, String prompt) {
        while (true) {
            System.out.println(prompt);
            String line = input.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}

