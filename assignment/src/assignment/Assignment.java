package assignment;

import java.util.Scanner;

public class Assignment {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        login userLogin = new login();

        System.out.println("--- REGISTRATION ---");
        System.out.println("Enter first name:");
        userLogin.setFirstName(input.nextLine());

        System.out.println("Enter last name:");
        userLogin.setLastName(input.nextLine());

        String username = "";
        String password = "";
        String cellNumber = "";

        // Cell phone input validation loop
        while (true) {
            System.out.println("Enter South African cell phone number (e.g. +27838968976):");
            cellNumber = input.nextLine();

            if (userLogin.checkCellPhoneNumber(cellNumber)) {
                System.out.println("Cell number successfully captured.");
                break;
            } else {
                System.out.println("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
            }
        }

        // Username input validation loop
        while (true) {
            System.out.println("Enter username:");
            username = input.nextLine();

            if (userLogin.checkUserName(username)) {
                System.out.println("Username successfully captured.");
                break;
            } else {
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        }

        // Password input validation loop
        while (true) {
            System.out.println("Enter user password:");
            password = input.nextLine();

            if (userLogin.checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured.");
                break;
            } else {
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
        }

        // Final registration attempt
        String registrationResponse = userLogin.registerUser(username, password, cellNumber);
        System.out.println("\n" + registrationResponse + "\n");

        // --- LOGIN SECTION ---
        System.out.println("--- LOGIN PAGE ---");
        while (true) {
            System.out.println("Enter username:");
            String enteredUser = input.nextLine();

            System.out.println("Enter password:");
            String enteredPass = input.nextLine();

            boolean loginSuccess = userLogin.loginUser(enteredUser, enteredPass);
            System.out.println(userLogin.returnLoginStatus(loginSuccess));

            if (loginSuccess) {
                break;
            }
        }

        // --- PART 2: only reachable after a successful login ---
        sendmessage sendMessage = new sendmessage();
        sendMessage.start(input);

        input.close();
    }
}