public class GeneralizedQueue<T> extends CircularDoubleLinkedQueue<T>
{
    public GeneralizedQueue()
    {
        super();
    }

    // goes from the back to front to remove the element
    private T removeFromBack(int index)
    {
        int count = size() - index;

        Node current = last();

        // because we want the node before the searched node we look for the node
        // at `index` - 1, e.i. we will decrement until we hit the index before
        // the starting index value (1).
        while (count > 1)
        {
            current = current.previous;
            count--;
        }

        T value = current.previous.value;

        // update links to remove element at `index` from circulation and reconnect the circle
        current.previous = current.previous.previous;
        current.previous.next = current;

        return value;
    }

    // goes from the front to back to remove the element
    private T removeFromFront(int index)
    {
        // using a standard case to use a native function
        if (index == 1)
        {
            return super.dequeue();
        }

        Node current = first();

        // because we want the node before the searched node we look for the node
        // at `index` - 1, e.i. we will decrement until we hit the index before
        // the starting index value (1). however, that case has already been
        // handled above.
        while (index > 2)
        {
            current = current.next;
            index--;
        }

        T value = current.next.value;

        // update links to remove element at `index` from circulation and reconnect the circle
        current.next = current.next.next;
        current.next.previous = current;

        return value;
    }

    // index is 1-based
    // removes and returns the value at the specified index.
    public T removeAt(int index)
    {
        // we cannot remove or return an element that is not part of the list
        if (index < 1 || index > size())
        {
            throw new IndexOutOfBoundsException();
        }

        return (index < size() / 2)
                    ? removeFromFront(index)
                    : removeFromBack(index);
    }
}
