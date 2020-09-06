/*
    Author: Andreas Hammarstrand
    Written:
    Updated:
    Purpose:
    Usage:
 */

import java.io.IOException;
import java.util.Scanner;

public class Program {
    private static void recursive() throws IOException
    {
        // input from user
        char c = (char)System.in.read();

        // if input was not new line, take in another input recursively
        if (c != '\n') {
            recursive();
        }

        // output raw input to stdout
        System.out.print(c);
    }

    public static void main(String[] args) throws IOException
    {
        System.out.println("Recursive:");
        recursive();

        System.out.println("Iterative:");
        Stack<Character>  characters = new Stack<Character>();

        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        // push all of input to the stack
        for (char c : input.toCharArray())
        {
            characters.push(c);
        }

        // write out all of the stack through popping
        while (characters.isEmpty())
        {
            System.out.print(characters.pop());
        }
    }
}