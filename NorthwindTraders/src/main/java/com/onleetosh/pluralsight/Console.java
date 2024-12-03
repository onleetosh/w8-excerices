package com.onleetosh.pluralsight;

import java.util.Scanner;

public class Console {

    static Scanner scanner = new Scanner(System.in);
    public static int PromptForInt(String prompt){
        System.out.print(prompt);
        String value = scanner.nextLine();
        int input = Integer.parseInt(value);
        return input;
    }
    public static String PromptForString(String prompt){
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static String PromptForString(){
        return scanner.nextLine();
    }

    public static boolean PromptForYesNo(String prompt){
        System.out.print(prompt + " ( Y for Yes, N for No ) ?");
        String input = scanner.nextLine();

        return
                (
                        input.equalsIgnoreCase("Y")
                                ||
                                input.equalsIgnoreCase("YES")
                ); // Returns false for any invalid input, may cause unexpected behavior

    }

    public static short PromptForShort(String prompt){
        System.out.print(prompt);
        String value = scanner.nextLine();
        short input = Short.parseShort(value);
        return  input;
    }



    public static double PromptForDouble(String prompt){
        System.out.print(prompt);
        String userInputs = scanner.nextLine();
        double input = Double.parseDouble(userInputs);
        return input;
    }

    public static byte PromptForByte(String prompt){
        System.out.print(prompt);
        String value = scanner.nextLine();
        byte input = Byte.parseByte(value);
        return input;
    }

    public static byte PromptForByte(){
        String value = scanner.nextLine();
        byte input = Byte.parseByte(value);
        return input;
    }

    public static float PromptForFloat(String prompt){
        System.out.print(prompt);
        String value = scanner.nextLine();
        float input =Float.parseFloat(value);
        return  input;
    }
}
