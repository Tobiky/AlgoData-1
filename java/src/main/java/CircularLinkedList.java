import java.util.Iterator;

public class CircularLinkedList<T> implements Iterable<T>
{
    private class Node
    {
        public T value;
        public Node next;
    }

    private Node first, last;
    int size;

    public CircularLinkedList()
    {
        size = 0;
    }

    public int size()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

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

    public void addFirst(T item)
    {
        first = addNode(item);
    }

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
        last.next = first.next;
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
        current.next = first;

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
                    .append(current.value.toString())
                    .append(", ");
        }

        // append last element
        sb.append(last.toString());

        sb.append(']');
        return  sb.toString();
    }

    public static void main(String[] args)
    {

    }
}
