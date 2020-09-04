import java.util.Iterator;
import java.util.Scanner;

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
public class Stack<T> implements Iterable<T>
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
    @Override
    public String toString()
    {
        // using a standard case to return a standard value
        if (isEmpty())
        {
            return "[]";
        }

        // printed lists starts and end with '[' and ']' respectively
        StringBuilder sb = new StringBuilder();
        sb.append('[');

        // all values are appended to sb with a joint comma for each successive pair
        // of values, last value left out of loop to avoid an empty array
        // [.., x, y, z, ] -> [.., x, y, z]
        for (int i = 0; i < size() - 1; i++)
        {
            sb
                    .append(values[i].toString())
                    .append(", ");
        }

        // append last element
        sb
                .append(values[size() - 1].toString());
        
        sb.append(']');
        return sb.toString();
    }

    // returns an iterator that spans over the stack.
    public Iterator<T> iterator()
    {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<T>
    {
        int stackPointer;

        public StackIterator()
        {
            stackPointer = size();
        }

        // returns a bool specifying if the iterator has following element.
        public boolean hasNext()
        {
            return stackPointer > 0;
        }

        // moves the iterator to the next value and returns it.
        public T next()
        {
            return values[--stackPointer];
        }

        // warnings are given when `remove` is not overridden, throwing UnsupportedOperation exception instead.
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    // test method
    public static void main(String[] args)
    {
        // member tests
        // these tests make sure that each member of the type works
        // on a surface-level.
        // for a more rigorous test, see case test below.

        Stack<Character> s = new Stack<Character>();

        // check that `size` and `isEmpty` are correct at the initial state of a `Stack`
        int initial_size_result = s.size();

        assert initial_size_result == 0 : initial_size_result;
        assert s.isEmpty();


        // after 1 push to the stack the `size` should be 1 and `isEmpty` should be false
        s.push('a');

        int push_size_result = s.size();

        assert push_size_result == 1 : push_size_result;
        assert !s.isEmpty();


        // popping a value from the stack should reduce the size by one (in this case to 0)
        // after popping the last value, `isEmpty` should return `true` as well
        //
        // as a consequence of popping one value, a resizing to a smaller array should happen
        // if this is the case, valid functionality at later uses of this stack proves
        // that resizing to smaller arrays is working.
        char popped_result = s.pop();
        int popped_size_result = s.size();
        boolean popped_isEmpty_result = s.isEmpty();

        assert popped_size_result == 0 : popped_size_result;
        assert popped_result == 'a' : popped_result;
        assert popped_isEmpty_result;


        // the `toString` function should return the elements starting from the
        // bottom of the stack and ending at the top of the stack, in the
        // specified format.
        // this will also test resizing to bigger arrays.
        s.push('h');
        s.push('e');
        s.push('l');
        s.push('l');
        s.push('o');

        String toString_result = s.toString();
        assert toString_result.equals("[h, e, l, l, o]") : toString_result;

        // to reset the stack
        while (!s.isEmpty())
        {
            s.pop();
        }

        // case test
        // here the test focuses on regular usage of the type,
        // rather than surface level method testing like above
        String value = "this is a test value";
        
        for (char character : value.toCharArray())
        {
            s.push(character);
        }

        System.out.println("Expecting (with formatting): " + 
                            value +
                            "   Got: " +
                            s.toString());

        System.out.println("Iterative output:");
        for (char character : s)
        {
            System.out.print(character);
        }
        System.out.print('\n');

        // clearing stack
        while (!s.isEmpty())
        {
            s.pop();
        }

        System.out.println("Testing for input:");
        Scanner input = new Scanner(System.in);
        String inputString = input.nextLine();

        System.out.println(s.toString());
        for (char character : inputString.toCharArray())
        {
            s.push(character);
            System.out.println(s.toString());
        }

        while (!s.isEmpty())
        {
            s.pop();
            System.out.println(s.toString());
        }
    }
}