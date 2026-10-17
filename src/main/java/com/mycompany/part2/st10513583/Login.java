/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part2.st10513583;

/**
 *
 * @author Student
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.regex.Pattern;

/**
 *
 * @author Student
 */
public class Login {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;

    // Regex patterns based on the task's condition tables
    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^(?=.{1,5}$).*_.*$");
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,}$");
    private static final Pattern CELLPHONE_PATTERN =
            Pattern.compile("^\\+27[0-9]{1,10}$");

    public Login() {
    }

    public Login(String firstName, String lastName, String username, String password, String cellPhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // Checks that the username contains an underscore and is no more than 5 characters long
    public boolean checkUserName(String username) {
        return USERNAME_PATTERN.matcher(username).matches();
    }

    // Checks the password meets complexity rules: 8+ chars, capital letter, number, special character
    public boolean checkPasswordComplexity(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    // Checks the cell phone number has the SA international code and is correctly formatted
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        return CELLPHONE_PATTERN.matcher(cellPhoneNumber).matches();
    }

    // Returns the registration outcome message based on username and password validity
    public String registerUser(String username, String password, String cellPhoneNumber) {
        boolean usernameValid = checkUserName(username);
        boolean passwordValid = checkPasswordComplexity(password);
        boolean cellValid = checkCellPhoneNumber(cellPhoneNumber);

        if (!usernameValid) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!passwordValid) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!cellValid) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }

        // All valid - store the details
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully captured.\nUser registered successfully.";
    }

    // Verifies that the entered login details match the stored registration details
    public boolean loginUser(String username, String password) {
        return this.username != null
                && this.username.equals(username)
                && this.password != null
                && this.password.equals(password);
    }

    // Returns the appropriate message for a login attempt
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // Getters/Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getUsername() { return username; }

    public String getCellPhoneNumber() { return cellPhoneNumber; }
}