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
        // Take input from user
        Stack<Character>  characters = new Stack<Character>();
        System.out.println("Input:");
        int in = System.in.read();

        while (in > -1 && (char)in != '\n')
        {
            characters.push((char)in);
            in = System.in.read();
        }

        // Print out input in different formats
        System.out.println("\nOutput:");
        System.out.println(characters.toString());

        while (!characters.isEmpty())
        {
            System.out.print(characters.pop());
        }
        System.out.print('\n');
    }
}