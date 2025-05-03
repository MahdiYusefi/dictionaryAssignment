package org.example;

import java.util.Scanner;


// insert Ball language FRENCH Ball
// delete Ball
// get Ball language FRENCH
public class Main {
    public static void main(String[] args) {
        IDictionaryCommand command = new DictionaryCommand(new DictionaryRepository());
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter your command:");
            String input = scanner.nextLine();
            String[] commands = input.split(" ");

            if ("insert".equals(commands[0])) {

                String word = commands[1];
                String language = commands[3];
                String meaning = commands[4];
                System.out.println(command.insert(new Word(word), new MeaningInLanguage(meaning, MeaningInLanguage.Language.valueOf(language))));

            } else if ("delete".equals(commands[0])) {

                String word = commands[1];
                command.delete(new Word(word));

            } else if ("get".equals(commands[0])) {
                String word = commands[1];
                String language = commands[3];
                System.out.println(command.get(new Word(word), MeaningInLanguage.Language.valueOf(language)).getMeaning());

            } else if ("search".equals(commands[0])) {
                String term = commands[1];
                for (Word search : command.search(term)) {
                    System.out.println(search.getWord());
                }
            }


        }
    }
}