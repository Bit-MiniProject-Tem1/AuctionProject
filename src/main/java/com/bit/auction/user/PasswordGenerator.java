package com.bit.auction.user;

import java.security.SecureRandom;

public class PasswordGenerator {
    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomPassword(int length) {
        StringBuilder password = new StringBuilder(length);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }
    public static void main(String[] args) {
        // Example: Generate a 6-character random password
        String temporaryPassword = generateRandomPassword(6);
        System.out.println("Temporary Password: " + temporaryPassword);
    }
}
