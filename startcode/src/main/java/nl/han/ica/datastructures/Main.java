package nl.han.ica.datastructures;

public class Main {
    public static void main(String[] args) {
        // Maak een nieuwe HANLinkedList van type Integer
        HANLinkedList<Integer> linkedList = new HANLinkedList<>();

        // Test: Voeg elementen toe aan de lijst
        linkedList.addFirst(10);
        linkedList.addFirst(20);
        linkedList.addFirst(30);
        System.out.println("After adding elements: ");
        printList(linkedList);  // Verwacht: 30, 20, 10

        // Test: Voeg een element toe op een specifieke index
        linkedList.insert(1, 15);  // Voeg 15 toe op index 1 (tussen 30 en 20)
        System.out.println("After inserting 15 at index 1: ");
        printList(linkedList);  // Verwacht: 30, 15, 20, 10

        // Test: Verwijder een element op een specifieke positie
        linkedList.delete(2);  // Verwijder element op index 2 (20)
        System.out.println("After deleting element at index 2: ");
        printList(linkedList);  // Verwacht: 30, 15, 10

        // Test: Haal een element op
        int valueAtIndex1 = linkedList.get(1);  // Verwacht: 15
        System.out.println("Element at index 1: " + valueAtIndex1);

        // Test: Verwijder het eerste element
        linkedList.removeFirst();
        System.out.println("After removing first element: ");
        printList(linkedList);  // Verwacht: 15, 10

        // Test: Haal het eerste element op
        int firstElement = linkedList.getFirst();  // Verwacht: 15
        System.out.println("First element: " + firstElement);

        // Test: Leeg de lijst
        linkedList.clear();
        System.out.println("After clearing the list, size: " + linkedList.getSize());  // Verwacht: 0

        System.out.println("//////////////////////////////////////////////");
        System.out.println("//////////////////////////////////////////////");
        System.out.println("//////////////////////////////////////////////");
        System.out.println("//////////////////////////////////////////////");
        System.out.println("//////////////////////////////////////////////");

        // Maak een nieuwe HANStack van type Integer
        HANStack<Integer> stack = new HANStack<>();

        // Test: Push elementen op de stack
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.println("After pushing elements: ");
        printStack(stack);  // Verwacht: 30, 20, 10

        // Test: Peek het bovenste element
        int topElement = stack.peek();
        System.out.println("Top element (peek): " + topElement);  // Verwacht: 30

        // Test: Pop een element van de stack
        int poppedElement = stack.pop();
        System.out.println("Popped element: " + poppedElement);  // Verwacht: 30
        System.out.println("After popping an element: ");
        printStack(stack);  // Verwacht: 20, 10

        // Test: Controleer of de stack leeg is
        System.out.println("Is the stack empty? " + stack.isEmpty());  // Verwacht: false

        // Test: Pop alle elementen van de stack
        stack.pop();
        stack.pop();
        System.out.println("After popping all elements: ");
        printStack(stack);  // Verwacht: (geen uitvoer)

        // Test: Controleer of de stack nu leeg is
        System.out.println("Is the stack empty? " + stack.isEmpty());  // Verwacht: true

        // Test: Probeer te poppen van een lege stack
        try {
            stack.pop();
        } catch (IllegalStateException e) {
            System.out.println("Caught exception: " + e.getMessage());  // Verwacht: "Stack is empty"
        }

        System.out.println("//////////////////////////////////////////////");
        System.out.println("//////////////////////////////////////////////");
        System.out.println("//////////////////////////////////////////////");
        System.out.println("//////////////////////////////////////////////");
        System.out.println("//////////////////////////////////////////////");

        // Maak een nieuwe HANQueue van type Integer
        HANQueue<Integer> queue = new HANQueue<>();

        // Test: Enqueue elementen in de queue
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        System.out.println("After enqueueing elements: ");
        printQueue(queue);  // Verwacht: 10, 20, 30

        // Test: Peek het voorste element
        int frontElement = queue.peek();
        System.out.println("Front element (peek): " + frontElement);  // Verwacht: 10

        // Test: Dequeue een element van de queue
        int dequeuedElement = queue.dequeue();
        System.out.println("Dequeued element: " + dequeuedElement);  // Verwacht: 10
        System.out.println("After dequeuing an element: ");
        printQueue(queue);  // Verwacht: 20, 30

        // Test: Controleer of de queue leeg is
        System.out.println("Is the queue empty? " + queue.isEmpty());  // Verwacht: false

        // Test: Dequeue alle elementen van de queue
        queue.dequeue();
        queue.dequeue();
        System.out.println("After dequeuing all elements: ");
        printQueue(queue);  // Verwacht: (geen uitvoer)

        // Test: Controleer of de queue nu leeg is
        System.out.println("Is the queue empty? " + queue.isEmpty());  // Verwacht: true

        // Test: Probeer te dequeueen van een lege queue
        try {
            queue.dequeue();
        } catch (IllegalStateException e) {
            System.out.println("Caught exception: " + e.getMessage());  // Verwacht: "Queue is empty"
        }

    }

    // Helper methode om de elementen in de linked list af te drukken
    public static <T> void printList(HANLinkedList<T> list) {
        for (T item : list) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    public static <T> void printStack(HANStack<T> stack) {
        HANLinkedList<T> list = new HANLinkedList<>();
        for (T item : stack.list) {
            list.addFirst(item);
        }
        for (T item : list) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    // Helper methode om de queue te printen
    public static <T> void printQueue(HANQueue<T> queue) {
        for (T item : queue.list) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
}