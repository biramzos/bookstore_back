package com.example.bookstore_back.Services;


public class ByteService {
    public static String getString(byte[] byteArray) {
        StringBuilder byteaString = new StringBuilder("\\x");
        for (byte b : byteArray) {
            byteaString.append(String.format("%02X", b));
        }
        return byteaString.toString();
    }

    public static byte[] getBytes(String byteaString) {
        if (byteaString.startsWith("\\x")) {
            byteaString = byteaString.substring(2); // Remove the "\\x" prefix
            int len = byteaString.length();
            byte[] byteArray = new byte[len / 2];

            for (int i = 0; i < len; i += 2) {
                byteArray[i / 2] = (byte) ((Character.digit(byteaString.charAt(i), 16) << 4)
                        + Character.digit(byteaString.charAt(i + 1), 16));
            }
            return byteArray;
        } else {
            return null;
        }
    }

}
