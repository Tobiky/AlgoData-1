import java.util.Iterator;

public class CircularDoubleLinkedQueue<T> implements Iterable<T>
{
    protected class Node
    {
        public T value;
        public Node next;
        public Node previous;
    }

    private Node first, last;
    private int size;

    protected Node first()
    {
        return first;
    }

    protected Node last()
    {
        return last;
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

        Node n = first;

        // printed lists starts and end with '[' and ']' respectively
        StringBuilder sb = new StringBuilder();
        sb.append('[');

        // all values are appended to sb with a joint comma for each successive pair
        // of values, last value left out of loop to avoid an empty array
        // [.., x, y, z, ] -> [.., x, y, z]
        while (n != last){
            sb
                    .append(n.value.toString())
                    .append(", ");
            n = first.next;
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

        // warnings are given when `remove` is not overridden, throwing UnsupportedOperation exception instead.
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    // test method
    public static void main(String[] args)
    {
        // these tests make sure that each member of the type works
        // on a surface-level.
        // for a more rigorous test, see case test below.

        // case test
        // here the test focuses on regular usage of the type,
        // rather than surface level method testing like above
    }
}
