import java.io.IOException;

public class Program {
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
    }
}