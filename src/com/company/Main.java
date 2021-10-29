package com.company;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static String [] CompareAndDestroy(String [] array)
    {
        Set<String> stringSet = new HashSet<>(Arrays.asList(array));
        return stringSet.toArray(new String[0]);
    }

    public static void main(String[] value) throws NoSuchAlgorithmException, InvalidKeyException {
        String[] args = CompareAndDestroy(value);

        if (args.length%2==0){
            System.out.println("You entered an even number of arguments");
            return;
        }
        Scanner sc = new Scanner(System.in);
        SecureRandom random = new SecureRandom();
        int pc = random.nextInt(args.length);
        StringBuilder menu = new StringBuilder();

        for (int i = 0; i<args.length;i++){
            menu.append(i + 1).append("-").append(args[i]).append("\n");
        }
        while (true){
            GenerationKeyAndHMAC hmac = new GenerationKeyAndHMAC();
            String[] hmacResult = hmac.keyGenerate(args, pc);
            System.out.println("HMac: "+hmacResult[0]);
            System.out.println("Available moves:\n"+menu+
                    "0-exit\n" +
                    "?-help");
            System.out.print("Enter your move: ");
            String key = sc.nextLine();
            switch(key){
                case "0": return;
                case "?": {
                    WinTable table = new WinTable(args);
                    table.displayTable();
                    break;
                }
                default:{
                    try {
                        if(Integer.parseInt(key)<=args.length){
                            RulesOfVictories rules = new RulesOfVictories(args);
                            System.out.println("Your move: "+args[Integer.parseInt(key)-1]);
                            System.out.println("Computer move: "+args[pc]);
                            System.out.println(rules.result(pc, Integer.parseInt(key)-1));
                            System.out.println("HMac key: "+hmacResult[1]+"\n");
                        }else {
                            System.out.println("Incorrect value entered!!!");
                        }
                    }
                    catch (NumberFormatException e){
                        System.out.println("Incorrect value entered!!!");
                    }
                    break;
                }
            }
        }

    }
}
