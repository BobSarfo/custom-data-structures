package org.bob.priority_queue;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

// implements List<E>, Deque<E>, Cloneable, java.io.Serializable
public class CustomLinkedList <D, P extends Comparable<P> >  implements Cloneable, Serializable {

    private Node head;
    private Node tail;
    private int size;
    private Comparator comparator;
    private Boolean sortDirection;

    public CustomLinkedList() {

    }


    public CustomLinkedList(Boolean sortDirection) {
        this.sortDirection = sortDirection;
    }

       @SuppressWarnings("unchecked")
       @Override
       public CustomLinkedList<D, P> clone() {
           try {
               return (CustomLinkedList<D,P>) super.clone();
           } catch (CloneNotSupportedException e) {
               throw new AssertionError();
           }
       }


    class Node
    {
        D data;
        Node next;
        Node prev;
        P priority;

        public Node(D data){
            this.data = data;
        }
        public Node(D data, P priority){
            this(data);
            this.priority = priority;
        }
    }


    public void add(D data){
        if( data == null){
            throw  new IllegalArgumentException();
        }
        Node newNode = new Node(data);
        if(head == null) {
            head = tail = newNode;
        }else
        {
            tail.next  = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public void add(D data, P priority) {
        if(data == null || priority == null){
                throw new IllegalArgumentException();
            }
        Node newNode = new Node(data,priority);
        if(head==null){
            head=tail= newNode;
            size++;
            return;
        }

        if (compare(head.priority,priority)<=0)
        {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }
        Node current = head;
        while (current.next!=null && compare(current.priority,priority)>=0){
            current= current.next;
        }

        if (current.next != null){
            newNode.next= current;
            newNode.prev= current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
        }else if (compare(current.priority,priority)<=0){
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
        }else {
            current.next = newNode;
            newNode.prev= current;
            tail = newNode;
        }

        size++;
    }

    public D get(int index){
        if(index>size || index<=0) throw new IndexOutOfBoundsException();

        Node current = head;
        int count = 0;

        Node node = head;
        for (int i = 0; i < index; i++) {
            if (node == null) throw new IndexOutOfBoundsException();
            node = node.next;
        }

        return node.data  ;
    }

    public D peek(){
        if (head ==null) throw new NoSuchElementException();
        return head.data;
    }

    public Iterator<D> iterator(){
        return new Iterator<D>() {

            private Node current = head;
            @Override
            public boolean hasNext() {
                return  current != null;
            }

            @Override
            public D next() {
                if (current == null) throw new NoSuchElementException();
                D data = current.data;
                current = current.next;
                return  data;
            }
        };
    }
    public D pop(){
        if (head ==null) {
            throw new NoSuchElementException();
        }
        D data = head.data;
        if (head.next ==null) {
            head=tail=null;
        }else{
            head = head.next;
            head.prev=null;
        }
        size--;
        return data;
    }

    public int size(){
        return size;
    }

    public void bump(D data, P priority) {
        if (data == null || priority == null) {
            throw new IllegalArgumentException("Data or priority cannot be null");
        }

        Node current = head;
        while (current != null && !current.data.equals(data)) {
            current = current.next;
        }

        if (current == null) {
            throw new NoSuchElementException("Data not found in the list");
        }

        current.priority = priority;

        boolean isCorrectPosition = true;
        if (current.prev != null && compare(current.prev.priority, current.priority) < 0) {
            isCorrectPosition = false;
        } else if (current.next != null && compare(current.next.priority, current.priority) > 0) {
            isCorrectPosition = false;
        }

        if (isCorrectPosition) {
            return;
        }

        // Remove the node from its current position
        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;
        }
        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;
        }

        current.next = null;
        current.prev = null;


        size--;

        add(data, priority);
    }

    @SuppressWarnings("unchecked")
    private int compare(P p1, P p2) {
        int cmp = comparator == null? comparator.compare(p1,p2) : p1.compareTo(p2);
        return sortDirection ? cmp : -cmp;
    }

}
