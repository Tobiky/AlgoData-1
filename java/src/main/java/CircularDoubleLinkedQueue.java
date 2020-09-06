/*
    Author: Andreas Hammarstrand
    Written: 2020/09/01
    Updated: 2020/09/06
    Purpose:
        CircularDoubleLinkedQueue<T> is a generic queue implementation, based on double linked
        nodes and is linked circularly.
    Usage:
        Create an instance with `new CircularDoubleLinkedQueue<T>()` where `T`
        is wanted type. Use `void enqueue(T item)` to add items to the list
        and `T dequeue()` to remove (and retrieve those removed) items from
        the list.
 */

import java.util.Iterator;
import java.util.Scanner;

public class CircularDoubleLinkedQueue<T> implements Iterable<T>
{
    protected class Node
    {
        public T value;
        public Node next;
        public Node previous;

        @Override
        public String toString()
        {
            return value.toString();
        }
    }

    private Node first, last;
    private int size;

    protected Node first()
    {
        return first;
    }
    protected void setFirst(Node node) { first = node;}

    protected Node last()
    {
        return last;
    }
    protected void setLast(Node node) { last = node; }

    protected void setSize(int size)
    {
        this.size = size;
    }

    public CircularDoubleLinkedQueue()
    {
        size = 0;
    }

    // puts the item at the end of the queue.
    public void enqueue(T item)
    {
        // a value is added to the queue; increment counter
        size++;

        Node n = new Node();
        n.value = item;

        // if `first` is null then the new node is first and last
        if (first == null)
        {
            n.next = n.previous = n;
            first = last = n;
            return;
        }

        // update node n links
        n.next = first;
        n.previous = last;

        // update node last links
        last.next = n;
        last = n;

        // update node first links
        first.previous = n;
    }

    // returns and removes the value from the front of the queue.
    public T dequeue()
    {
        // put away the saved value so it can be returned later.
        // this value is now considered removed, or "dequeued".
        T value = first.value;

        // count is decremented from removed value and a check to see if it was the
        // last element is made.
        // if the node was the last node; reset the queue to its initial state.
        size--;
        if (size == 0)
        {
            first = last = null;
            return value;
        }

        // storing the next node in a variable with a user friendly name
        // to improve readability
        Node newFirst = first.next;

        // link together the next item with the last item, so the circle continues.
        last.next = newFirst;
        newFirst.previous = last;

        // update `first` reference to point at the new first node
        first = newFirst;

        return value;
    }

    // returns amount of elements in the queue.
    public int size()
    {
        return size;
    }

    // returns true if the queue is empty, otherwise false.
    public boolean isEmpty()
    {
        return size == 0;
    }

    // returns a string representation of the queue instance.
    @Override
    public String toString()
    {
        // using a standard case to return a standard value
        if (isEmpty())
        {
            return "[]";
        }

        Node current = first;

        // printed lists starts and end with '[' and ']' respectively
        StringBuilder sb = new StringBuilder();
        sb.append('[');

        // all values are appended to sb with a joint comma for each successive pair
        // of values, last value left out of loop to avoid an empty array
        // [.., x, y, z, ] -> [.., x, y, z]
        while (current != last){
            sb
                    .append(current.toString())
                    .append(", ");
            current = current.next;
        }

        // append last element
        sb.append(last.toString());

        sb.append(']');
        return sb.toString();
    }

    // returns an iterator that spans over the stack.
    public Iterator<T> iterator()
    {
        return new FIFOQueueIterator(first, last);
    }

    private class FIFOQueueIterator implements Iterator<T>
    {
        Node current, last;

        public FIFOQueueIterator(Node first, Node last)
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

        // warnings are given when `remove` is not overridden,
        // throwing UnsupportedOperation exception instead.
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    // test method
    public static void main(String[] args)
    {
        CircularDoubleLinkedQueue<Character> q = new CircularDoubleLinkedQueue<Character>();

        // these tests make sure that each member of the type works
        // on a surface-level.
        // for a more rigorous test, see case test below.

        // check that `size` and `isEmpty` are correct at the initial
        // state of a `CircularDoubleLinkedQueue`
        int initial_size_result = q.size();
        String initial_toString = q.toString();

        assert q.isEmpty();
        assert initial_size_result == 0 : initial_size_result;
        assert initial_toString.equals("[]") : initial_toString;

        // after 1 enqueue to the queue the `size` should be 1 and `isEmpty`
        // should be false
        q.enqueue('a');

        int enqueue_size_result = q.size();
        String enqueue_toString = q.toString();

        assert !q.isEmpty();
        assert enqueue_size_result == 1 : enqueue_size_result;
        assert enqueue_toString.equals("[a]") : enqueue_toString;

        // dequeueing a value from the stack should reduce the size by one
        // (in this case to 0) after popping the last value, `isEmpty`
        // should return `true` as well
        //
        // as a consequence of dequeueing one value, the queue should reset
        // back to its initial state
        char dequeue_result = q.dequeue();
        int dequeue_size_result = q.size();
        String dequeue_toString = q.toString();

        assert q.isEmpty();
        assert dequeue_result == 'a' : dequeue_result;
        assert dequeue_size_result == 0 : dequeue_size_result;
        assert dequeue_toString.equals("[]") : dequeue_toString;

        // considering that adding the first value differs
        // slightly from adding second and later, we test its
        // functionality here
        q.enqueue('h');
        q.enqueue('e');
        q.enqueue('l');
        q.enqueue('l');
        q.enqueue('o');

        String multiple_values_toString = q.toString();

        assert multiple_values_toString.equals("[h, e, l, l, o]") : multiple_values_toString;

        // reset q; the queue
        while (!q.isEmpty())
        {
            q.dequeue();
        }

        // case test
        // here the test focuses on regular usage of the type,
        // rather than surface level method testing like above
        System.out.println("Input:");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        System.out.println(q);
        for (char c : input.toCharArray())
        {
            q.enqueue(c);
            System.out.println(q);
        }

        // testing that for each works
        for (char c : q)
        {
            // processing the outputted character does not matter and so is left
            // out of implementation
        }

        while (!q.isEmpty())
        {
            char c = q.dequeue();
            System.out.println(q);
        }

        System.out.println(q);
    }
}
