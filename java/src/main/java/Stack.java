/*
    Author: Andreas Hammarstrand
    Written: 2020/08/26
    Updated: 2020/08/28
    Purpose:
        Stack<T> is an array-based implementation of a stack ADT.
    Usage:
        Stack<T> is instantiated without any specified starting capacity or
        starting items. Items are added to the stack through `push(T item)`
        and removed from the stack through `pop(item)`.
 */
public class Stack<T>
{
    public Stack()
    {
        values = (T[])new Object[8];
    }

    private T[] values;

    // ´index´ is the index of the next empty position
    private int index;

    // returns the number of elements in the stack.
    public int size()
    {
        // because ´index´ is the index of the next empty position
        // it is also the index of the last element + 1, which is
        // also the number of items in the stack.
        return index;
    }

    // returns true if stack is empty, otherwise false.
    public boolean isEmpty()
    {
        // when ´index´ is 0, the first empty position is on index 0
        // and the stack is thus empty.
        return index == 0;
    }

    // creates a new array of larger or smaller size and fills it
    // with the values from the previous array, then assigns the
    // new array as the stacks active array.
    private void resize(
        int newLength)
    {
        T[] newValues = (T[])new Object[newLength];
        System.arraycopy(values, 0, newValues, 0, index);
        values = newValues;
    }

    // removes and returns the element on the top of the stack.
    public T pop() 
    {
        // if the user attempts to pop an item off the top of the stack
        // when there are no items on the stack, an exception is thrown.
        // the user bypasses this by checking if the stack is empty
        // through ´bool Stack.isEmpty´.
        if (index == 0) 
        {
            throw new IndexOutOfBoundsException();
        }

        // get the value at the top of the stack and move the stack pointer down
        T c = values[--index];
        
        // check if the amount of items in stacks allow for a resize to
        // a smaller stack.
        if (index < values.length / 4)
        {
            resize(values.length / 2);
        }

        return c;
    }

    // put the value on the top of the stack.
    public void push(
        T value) 
    {
        // assigning a value to the top of the stack and move the stack pointer up
        values[index++] = value;

        // check if the amount of items in stacks allow for a resize to
        // a larger stack.
        if (index > values.length / 2) 
        {
            resize(values.length * 2);
        }
    }

    // returns a string representation of the object instance.
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append('[');
        
        // here we avoid appending the string version of the last element
        // to make the string neater. otherwise it would result in:
        // "[a, b, c, ]"
        // by moving the printing of the last element to after the array
        // we will get the neat version:
        // "[a, b, c]"
        for (int i = 0; i < size() - 1; i++)
        {
            String numberString = values[i].toString();
            sb
                    .append('\'')
                    .append(numberString)
                    .append('\'')
                    .append(", ");
        }

        // last item is appended
        sb
                .append('\'')
                .append(values[size() - 1].toString())
                .append('\'');
        
        sb.append(']');

        return sb.toString();
    }

    // test method
    public static void main(String[] args)
    {
        // member tests
        // these tests make sure that each member of the type works
        // on a surface-level.
        // for a more rigorous test, see case test below.

        Stack<Character> s = new Stack<Character>();
        
        System.out.println("Expecting: 0   Got: " 
            + s.size());
        
        s.push('a');
        
        System.out.println("Expecting: 1   Got: " 
            + s.size());
        
        char c = s.pop();

        System.out.println("Expecting: 0   Got: " 
            + s.size());
        System.out.println("Expecting: a   Got: " 
            + c);

        s.push('h');
        s.push('e');
        s.push('l');
        s.push('l');
        s.push('o');

        System.out.println("Expecting: ['h', 'e', 'l', 'l', 'o']   Got: " 
            + s.toString());

        while (!s.isEmpty())
        {
            s.pop();
        }

        // case test
        // here the test focuses on regular usage of the type,
        // rather than surface level method testing like above
        String value = "this is a test value";
        String expected = (new StringBuilder(value))
                            .reverse()
                            .toString();
        
        for (char character : value.toCharArray())
        {
            s.push(character);
        }

        System.out.println("Expecting (with formatting): " + 
                            value +
                            "   Got: " +
                            s.toString());

        System.out.println("Iterative output:");
        while (!s.isEmpty())
        {
            System.out.print(s.pop());
        }
    }
}