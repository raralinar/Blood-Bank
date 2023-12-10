package com.bloodbank.bloodbank.service.login;

import java.util.Random;

public class EmailGenerator {

    public static String generateRandomEmail() {
        String str2 = generateRandomString(3, 5);
        String str3 = generateRandomString(3, 5);
        return String.format("%s@%s.com", str2, str3);
    }

    public static String generateRandomString(int min, int max) {
        String signsSet = "0123456789abcdefghijklmnoprqstuvwxyz";
        char[] chars = signsSet.toCharArray();
        String randString = "";
        Random rand = new Random();
        int length = rand.nextInt((max - min) + 1) + min;
        while (length > 0) {
            randString += chars[rand.nextInt(chars.length)];
            length--;
        }
        return randString;
    }
}
