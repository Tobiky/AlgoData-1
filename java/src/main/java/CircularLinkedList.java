/*
    Author: Andreas Hammarstrand
    Written: 2020/09/03
    Updated: 2020/09/06
    Purpose:
        `CircularLinkedList<T>` is an implementation of a list, based on linked
         nodes and is linked circularly.
    Usage:
        Create an instance with `new CircularLinkedList<T>()` where `T`
        is wanted type. To add values, use either `void addLast(T item)`
        or `void addFirst(T item)`, to remove values use either
        `T removeLast()` or `T removeFirst()`. To run tests, run static
        main function of the class.
 */

import java.util.Iterator;
import java.util.Scanner;

public class CircularLinkedList<T> implements Iterable<T>
{
    private class Node
    {
        public T value;
        public Node next;

        @Override
        public String toString()
        {
            return value.toString();
        }
    }

    private Node first, last;
    int size;

    public CircularLinkedList()
    {
        size = 0;
    }

    // returns the amount of elements in the list.
    public int size()
    {
        return size;
    }

    // returns true if the list is empty, otherwise false.
    public boolean isEmpty()
    {
        return size == 0;
    }

    // returns an iterator over the list.
    public Iterator<T> iterator()
    {
        return new CircularLinkedListIterator(first, last);
    }

    private class CircularLinkedListIterator implements Iterator<T>
    {
        Node current, last;

        public CircularLinkedListIterator(Node first, Node last)
        {
            current = first;
            this.last = last;
        }

        // returns a bool specifying if the iterator has following element.
        public boolean hasNext()
        {
            return current != last;
        }

        // moves the iterator to the next value and returns it.
        public T next()
        {
            T value = current.value;

            current = current.next;

            return  value;
        }

        // warnings are given when `remove` is not overridden, throwing UnsupportedOperation exception instead.
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    // adds a node between the last and first node
    private Node addNode(T item)
    {
        // a value is added to the queue; increment counter
        size++;

        Node n = new Node();
        n.value = item;

        // if `first` is null then the new node is first and last
        if (first == null)
        {
            first = last = n;
        }

        // update links of first and last
        n.next = first;
        last.next = n;

        return n;
    }

    // adds the item to the front of the list
    public void addFirst(T item)
    {
        first = addNode(item);
    }

    // adds the item to the end of the list
    public void addLast(T item)
    {
        last = addNode(item);
    }

    // removes the first value and returns it
    public T removeFirst()
    {
        // put away the saved value so it can be returned later.
        // this value is now considered removed.
        T value = first.value;

        // count is decremented from removed value and a check to see if it was the
        // last element is made.
        // if the node was the last node; reset the list to its initial state.
        size--;
        if (size == 0)
        {
            first = last = null;
            return value;
        }

        // connect the last one with the next item on the list
        // and update the first to be the next item on the list.
        last.next = first.next;
        first = first.next;

        return value;
    }

    // removes the last value and returns it
    public T removeLast()
    {
        // put away the saved value so it can be returned later.
        // this value is now considered removed.
        T value = last.value;

        // count is decremented from removed value and a check to see if it was the
        // last element is made.
        // if the node was the last node; reset the list to its initial state.
        size--;
        if (size == 0)
        {
            first = last = null;
            return value;
        }

        // go through the nodes until the second last node is found
        Node current = first;

        while (current.next != last)
        {
            current = current.next;
        }

        // connect the last one with the next item on the list
        // and update `last` to be the earlier node.
        current.next = first;
        last = current;

        return value;
    }

    // returns a string representation of the circular linked list instance.
    @Override
    public String toString()
    {
        // if first is null, then
        if (first == null)
        {
            return "[]";
        }

        // printed lists starts and end with '[' and ']' respectively
        StringBuilder sb = new StringBuilder();
        sb.append('[');

        // all values are appended to sb with a joint comma for each successive pair
        // of values, last value left out of loop to avoid an empty array
        // [.., x, y, z, ] -> [.., x, y, z]
        Node current = first;

        while (current != last)
        {
            sb
                    .append(current.toString())
                    .append(", ");

            current = current.next;
        }

        // append last element
        sb.append(last.toString());

        sb.append(']');
        return  sb.toString();
    }

    // test method
    public static void main(String[] args)
    {
        CircularLinkedList<Character> l = new CircularLinkedList<Character>();
        // these tests make sure that each member of the type works
        // on a surface-level.
        // for a more rigorous test, see case test below.
        int initial_size_result = l.size();
        String initial_toString = l.toString();

        assert l.isEmpty();
        assert initial_size_result == 0 : initial_size_result;
        assert initial_toString.equals("[]") : initial_toString;

        // here `addFirst` and `removeFirst` are tested
        // after 1 push to the list the `size` should be 1 and `isEmpty`
        // should be false
        l.addFirst('a');

        int addFirst_size_result = l.size();
        String addFirst_toString = l.toString();

        assert !l.isEmpty();
        assert addFirst_size_result == 1 : addFirst_size_result;
        assert addFirst_toString.equals("[a]") : addFirst_toString;

        // dequeueing a value from the stack should reduce the size by one
        // (in this case to 0) after popping the last value, `isEmpty`
        // should return `true` as well
        //
        // as a consequence of dequeueing one value, the queue should reset
        // back to its initial state
        char removeFirst_result = l.removeFirst();
        int removeFirst_size_result = l.size();
        String removeFirst_toString = l.toString();

        assert l.isEmpty();
        assert removeFirst_result == 'a' : removeFirst_result;
        assert removeFirst_size_result == 0 : removeFirst_size_result;
        assert removeFirst_toString.equals("[]") : removeFirst_toString;

        // here `addLast` and `removeLast` are tested.
        // because `addLast` and `addFirst` use the same underlying method
        // we only test if `addLast` puts the items in the correct order,
        // rather than its entire functionality.

        // considering that adding the first value differs
        // slightly from adding second and later, we test its
        // functionality here
        l.addLast('h');
        l.addLast('e');
        l.addLast('l');
        l.addLast('l');
        l.addLast('o');

        String multiple_values_toString = l.toString();

        assert multiple_values_toString.equals("[h, e, l, l, o]") : multiple_values_toString;

        // reset l; the list
        while (!l.isEmpty())
        {
            l.removeLast();
        }

        // case test
        // here the test focuses on regular usage of the type,
        // rather than surface level method testing like above
        System.out.println("Input:");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        System.out.println("\nFIFO");
        System.out.println(l);
        for (char c : input.toCharArray())
        {
            l.addLast(c);
            System.out.println(l);
        }

        while (!l.isEmpty())
        {
            char c = l.removeFirst();
            System.out.println(l);
        }

        System.out.println("\nFILO");
        System.out.println(l);
        for (char c : input.toCharArray())
        {
            l.addLast(c);
            System.out.println(l);
        }

        while (!l.isEmpty())
        {
            char c = l.removeLast();
            System.out.println(l);
        }

        // these become just like regular linked lists
        System.out.println("\n`addFirst` -> `removeFirst`");
        System.out.println(l);
        for (char c : input.toCharArray())
        {
            l.addFirst(c);
            System.out.println(l);
        }

        while (!l.isEmpty())
        {
            char c = l.removeFirst();
            System.out.println(l);
        }

        System.out.println("\n`addLast` -> `removeLast`");
        System.out.println(l);
        for (char c : input.toCharArray())
        {
            l.addLast(c);
            System.out.println(l);
        }

        while (!l.isEmpty())
        {
            char c = l.removeLast();
            System.out.println(l);
        }
    }
}
