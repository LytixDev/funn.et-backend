package edu.ntnu.idatt2105.funn.security;

public class Validation {
    
    /**
     * Checks if the given string is null or empty.
     */
    public boolean isNotNullOrEmpty(String string) {
        return !(string == null || string.isEmpty());
    }

    /**
     * Checks if the given string is a valid email.
     * @param email The email to check.
     * @return True if the email is valid, false otherwise.
     */
    public boolean validateEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /**
     * Checks if the given string is a valid password.
     * Must contain 1 uppercase letter, 1 lowercase letter, 1 number and be at least 8 characters long.
     * @param password The password to check.
     * @return True if the password is valid, false otherwise.
     */
    public boolean validatePassword(String password) {
        return isNotNullOrEmpty(password) && password.matches("^[0-9a-zA-Z]") && isBetween(password, 9, 255);
    }

    /**
     * Check if the given string is smaller than the given length.
     * @param string The string to check.
     * @param length The length to check against.
     * @return True if the string is smaller than the length, false otherwise.
     */
    public boolean isSmallerThan(String string, int length) {
        return string.length() < length;
    }

    /**
     * Check if the given string is larger than the given length.
     * @param string The string to check.
     * @param length The length to check against.
     * @return True if the string is larger than the length, false otherwise.
     */
    public boolean isLargerThan(String string, int length) {
        return string.length() > length;
    }

    /**
     * Check if the given string is between the given lengths.
     * @param string The string to check.
     * @param minLength The minimum length to check against.
     * @param maxLength The maximum length to check against.
     * @return True if the string is between the lengths, false otherwise.
     */
    public boolean isBetween(String string, int minLength, int maxLength) {
        return string.length() >= minLength && string.length() <= maxLength;
    }

    /**
     * Check if the given string is a valid username.
     * Must be between 3 and 32 characters long and only contain letters, numbers and underscores.
     * @param username The username to check.
     * @return True if the username is valid, false otherwise.
     */
    public boolean validateUsername(String username) {
        return isNotNullOrEmpty(username) && username.matches("^[a-zA-Z0-9_]") && isBetween(username, 3, 32);
    }

    /**
     * Check if the given string is a valid name.
     * Must be smaller than 64 characters long and only contain letters.
     * @param firstName The name to check.
     * @return True if the name is valid, false otherwise.
     */
    public boolean validateName(String firstName) {
        return isNotNullOrEmpty(firstName) && firstName.matches("^[a-zA-Z]") && isSmallerThan(firstName, 64);
    }

    /**
     * Validate registration form.
     * @param username The username to validate.
     * @param email The email to validate.
     * @param firstName The first name to validate.
     * @param lastName The last name to validate.
     * @param password The password to validate.
     * @return True if the form is valid, false otherwise.
     */
    public boolean validateRegistrationForm(String username, String email, String firstName, String lastName, String password) {
        boolean valid = true;
        valid &= validateUsername(username);
        valid &= validateEmail(email);
        valid &= validatePassword(password);
        valid &= validateName(firstName);
        valid &= validateName(lastName);

        return valid;
    }

    /**
     * Validate login form.
     * @param username The username to validate.
     * @param password The password to validate.
     * @return True if the form is valid, false otherwise.
     */
    public boolean validateLoginForm(String username, String password) {
        boolean valid = true;
        valid &= validateUsername(username);
        valid &= validatePassword(password);

        return valid;
    }
}
