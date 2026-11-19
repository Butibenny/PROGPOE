package assignment;

public class login {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellNumber;

    // Checks that username contains '_' and is <= 5 characters
    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    // Checks that password is >= 8 chars, contains upper case, digit, and special char
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(ch)) {
                hasSpecial = true;
            }
        }

        return hasUpper && hasDigit && hasSpecial;
    }

    // Regular expression check for South African international cell number (+27 followed by 9 digits)
    public boolean checkCellPhoneNumber(String cellNumber) {
        return cellNumber != null && cellNumber.matches("^\\+27\\d{9}$");
    }

    // Method to handle registration messages according to requirements
    public String registerUser(String username, String password, String cellNumber) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellNumber)) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }

        // Store credentials once validation succeeds
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;

        return "The two above conditions have been met, and the user has been registered successfully.";
    }

    // Verifies stored credentials against login input
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return enteredUsername != null && enteredPassword != null 
                && enteredUsername.equals(this.username) 
                && enteredPassword.equals(this.password);
    }

    // Returns login status message
    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // Setters and Getters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
