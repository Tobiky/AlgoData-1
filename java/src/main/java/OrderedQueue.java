/*
    Author: Andreas Hammarstrand
    Written: 2020/09/04
    Updated: 2020/09/06
    Purpose:
        OrderedQueue is an Integer queue, based on circular double linked nodes
        and implements an ordered enqueue algorithm. Integers are enqueued in
        an ascending order.
    Usage:
        Create a new instance with `new OrderedQueue()`. Use
        `void enqueue(Integer item) to add an item to the queue and
        `Integer dequeue()` to remove an item from the queue.
 */

public class OrderedQueue extends CircularDoubleLinkedQueue<Integer>
{
    public OrderedQueue()
    {
        super();
    }

    // puts the item after an item in the queue with less value or first.
    @Override
    public void enqueue(Integer item)
    {
        // increment as we are adding values to the queue
        setSize(size() + 1);

        // create node
        Node newNode = new Node();
        newNode.value = item;

        // special procedure for initial/empty state
        if (first() == null)
        {
            newNode.previous = newNode.next = newNode;
            setFirst(newNode);
            setLast(newNode);
            return;
        }

        // iterate through all the nodes until a node that has a bigger value
        // than argument value.
        Node current = first();
        while (current != last() && item.compareTo(current.value) > 0)
        {
            current = current.next;
        }

        // update all links for the new node, its to-be-previous and to-be-next nodes
        Node front = current;
        Node back = current.previous;

        newNode.next = front;
        newNode.previous = back;

        back.next = newNode;

        front.previous = newNode;

        // above code is indiscriminate to if nodes are `last` or `first`
        // here we do a check for that to see if `first` or `last` needs
        // to update to the new node or not.
        if (front == first())
        {
            if (item.compareTo(first().value) <= 0)
            {
                setFirst(newNode);
            }
            else if (item.compareTo(last().value) >= 1)
            {
                setLast(newNode);
            }
        }
    }

    // test method
    // as this class extends the behavior of CircularDoubleLinkedQueue<T>
    // only the new method needs to be tested
    public static void main(String[] args)
    {
        OrderedQueue oq = new OrderedQueue();

        // tests that values are added correctly
        {
            oq.enqueue(1);

            int enqueue_size_result = oq.size();
            assert enqueue_size_result == 1 : enqueue_size_result;

            System.out.println(oq);
        }

        // tests that subsequent values are registered
        {
            oq.enqueue(3);

            int enqueue_second_size_result = oq.size();
            assert enqueue_second_size_result == 2 : enqueue_second_size_result;
            System.out.println(oq);
        }

        // tests if the algorithm can put values between each other
        {
            oq.enqueue(2);
            System.out.println(oq);

            int first_dequeue = oq.dequeue();
            assert first_dequeue == 1 : first_dequeue;
            System.out.println(oq);

            int second_dequeue = oq.dequeue();
            assert second_dequeue == 2 : second_dequeue;
            System.out.println(oq);

            int third_dequeue = oq.dequeue();
            assert third_dequeue == 3 : third_dequeue;
            System.out.println(oq);
        }
    }
}
