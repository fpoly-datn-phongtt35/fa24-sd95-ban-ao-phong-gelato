package com.example.datn.utils;

import java.util.Random;

public class RandomQRCodeUtils {

    public static String randomOrderId(Integer length){
        String character = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder randomString = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < length; i++){
            int index = random.nextInt(character.length()); // qr length limit
            randomString.append(character.charAt(index));
        }

        return randomString.toString();
    }


    public static String randomNumber(int length){
        String characters = "0123456789";
        StringBuilder randomString = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }

        return randomString.toString();
    }
}
