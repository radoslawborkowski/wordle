package org.example;

import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_RESET  = "\u001B[0m";


          Word correctWord = new Word("pl");


          String correct = correctWord.getWord().toUpperCase();
          int chances = correctWord.getChances();
          int length = correctWord.getWord().length();

//        System.out.println(correct);


        System.out.println("Długość słowa to: " + length+
                            "\n Liczba szans to:"+ chances+
                            "\n Powodzenia!\n");

        Scanner sc = new Scanner(System.in);
        String guess = "";

        // Loop for number of guesses
        for (int round = 0; round <= chances; round++) {
            System.out.print("Please guess. > ");
            guess = sc.nextLine().toUpperCase();

            try {
                // Loop for checking letters
                for (int i = 0; i < length; i++) {
                    if (guess.substring(i, i + 1).equals(correct.substring(i, i + 1))) {
                        // Letter is guessed
                        System.out.print(ANSI_GREEN + guess.charAt(i) + ANSI_RESET );
                    } else if (correct.contains(guess.substring(i, i + 1))) {
                        // Letter guessed but in a different place
                        System.out.print(ANSI_YELLOW  + guess.charAt(i) + ANSI_RESET );
                    } else {
                        // Letter not guessed
                        System.out.print(guess.charAt(i));
                    }

                }
            }catch(StringIndexOutOfBoundsException e){
                System.out.println("\nPodałeś za krótkie słowo!");
            }
            if (guess.length() > correct.length()) System.out.println("\nPodałeś za długie słowo!");

            System.out.println("");

            // If the guess is correct
            if (guess.equals(correct)) {
                System.out.println("Wygrałeś!");
                break;
            }
            if (round == chances) {
                System.out.println("Przegrałeś! Prawodłowe słowo to:" + correct);
            }

        }
    }
}