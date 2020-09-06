/*
    Author: Andreas Hammarstrand
    Written: 2020/09/01
    Updated: 2020/09/06
    Purpose:
        GeneralizedQueue<T> extends the functionality of
        CircularDoubleLinkedQueue<T> by allowing the user
        to remove values at certain indices.
    Usage:
        See CircularDoubleLinkedQueue<T> for base functionality.
        `removeAt(int index)` allows the user to remove a value at the
        specified index. To run tests, run static main function of the class.
 */

public class GeneralizedQueue<T> extends CircularDoubleLinkedQueue<T>
{
    public GeneralizedQueue()
    {
        super();
    }

    // goes from the back to front to remove the element
    private T removeFromBack(int index)
    {
        // count is the amount of nodes we need to traverse to get to
        // wanted node.
        int count = super.size() - index;

        Node current = super.last();

        // traverse the nodes
        while (count > 0)
        {
            current = current.previous;
            count--;
        }

        T value = current.value;

        // update links to remove element at `index` from circulation and reconnect the circle
        Node back = current.previous;
        Node front = current.next;

        back.next = front;
        front.previous = back;

        if (index == size())
        {
            setLast(back);
        }

        return value;
    }

    // goes from the front to back to remove the element
    private T removeFromFront(int index)
    {
        Node current = super.first();

        // as index also serves the amount of nodes that need traversal,
        // index will be use to keep track of the traversed nodes.
        // however, the case of first node is already being taken cared of
        // so one node is skipped, therefore `index > 1`.
        while (index > 1)
        {
            current = current.next;
            index--;
        }

        T value = current.next.value;

        // update links to remove element at `index` from circulation and reconnect the circle
        Node back = current.previous;
        Node front = current.next;

        back.next = front;
        front.previous = back;

        return value;
    }

    // index is 1-based
    // removes and returns the value at the specified index.
    public T removeAt(int index)
    {
        // we cannot remove or return an element that is not part of the list
        if (index < 1 || index > super.size())
        {
            throw new IndexOutOfBoundsException();
        }

        // using a standard case to use a native function
        if (index == 1)
        {
            return super.dequeue();
        }

        // searches from back to front if the index is in the end half,
        // otherwise it search front to back
        T result =  (index <= super.size() / 2)
                        ? removeFromFront(index)
                        : removeFromBack(index);

        // decrement the size
        // because this is an extended class, it does not have direct access
        // to the size field.
        super.setSize(super.size() - 1);
        return result;
    }

    // test method
    // because `GeneralizedQueue` extends `CircularDoubleLinkedQueue` we only need to test the added methods
    public static void main(String[] args)
    {
        GeneralizedQueue<Character> q = new GeneralizedQueue<Character>();
        // these tests make sure that each member of the type works
        // on a surface-level.
        // for a more rigorous test, see case test below.

        // testing removal of one value
        q.enqueue('a');
        char removeAt_result = q.removeAt(1);

        assert removeAt_result == 'a' : removeAt_result;


        // testing removal of middle value
        q.enqueue('a');
        q.enqueue('b');
        q.enqueue('c');

        char removeAt_middle_result = q.removeAt(2);

        assert removeAt_middle_result == 'b' : removeAt_middle_result;

        // reset q; the queue
        while (!q.isEmpty())
        {
            q.dequeue();
        }


        // testing removal of end value
        q.enqueue('a');
        q.enqueue('b');
        q.enqueue('c');
        q.enqueue('d');

        char removeAt_end_result = q.removeAt(4);

        assert removeAt_end_result == 'd' : removeAt_end_result;

        // reset q; the queue
        while (!q.isEmpty())
        {
            q.dequeue();
        }

        // case test
        // here the test focuses on regular usage of the type,
        // rather than surface level method testing like above

        // case:
        // misspellt word was inputted, removing the letters that are wrong
        String testValue = "Hellloip";
        for (char c : testValue.toCharArray())
        {
            q.enqueue(c);
            System.out.println(q);
        }

        // remove the extra 'l'
        char extra_l = q.removeAt(5);
        assert extra_l == 'l' : extra_l;
        System.out.println(q);

        // remove 'i'
        char i = q.removeAt(6);
        assert i == 'i' : i;
        System.out.println(q);

        // remove 'p'
        char p = q.removeAt(6);
        assert p == 'p' : p;
        System.out.println(q);
    }
}
