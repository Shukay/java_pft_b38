package ru.stqa.pft.mantis.appmanager;

import java.security.SecureRandom;

public class SaltGenerator {
    public static void main(String[] args) {
        int length = 32; // желаемая длина соли
        String salt = generateSalt(length);
        System.out.println(salt);
    }

    private static String generateSalt(int length) {
        byte[] saltBytes = new byte[length];
        SecureRandom random = new SecureRandom();
        random.nextBytes(saltBytes);
        return bytesToHex(saltBytes);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
