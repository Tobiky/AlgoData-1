/*
    Author: Andreas Hammarstrand
    Written: 2020/09/07
    Purpose:
    Usage:
 */

import java.util.Iterator;
import java.util.Scanner;

public class BalancedParentheses
{
    private static char opposingParentheses(char parentheses)
    {
        switch (parentheses)
        {
            case '{': return '}';
            case '(': return ')';
            case '[': return ']';
        }

        throw new IllegalArgumentException();
    }

    private static boolean isClosing(char parentheses)
    {
        switch (parentheses)
        {
            case '}':
            case ')':
            case ']':
                return true;
        }
        return false;
    }

    private static boolean isOpening(char parentheses)
    {
        switch (parentheses)
        {
            case '{':
            case '(':
            case '[':
                return true;
        }
        return false;
    }


    private static boolean matching(Iterator<Character> iter, char allowedClosingParentheses)
    {
        while (iter.hasNext())
        {
            char next = iter.next();
            if (next == allowedClosingParentheses)
            {
                return true;
            }
            else if (isOpening(next))
            {
                if (!matching(iter, opposingParentheses(next)))
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }

        return false;
    }

    private static class StringIterator implements Iterator<Character>
    {
        private int index = 0;
        private final String string;

        StringIterator(String string)
        {
            this.string = string;
        }

            public boolean hasNext() {
                return index < string.length();
            }

            public Character next() {
                return string.charAt(index++);
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
    }

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        Iterator<Character> iter = new StringIterator(input);


        boolean result = true;
        while (iter.hasNext() && result)
        {
            char opening = iter.next();
            result = matching(iter, opposingParentheses(opening));
        }

        System.out.println(result);
    }
}
