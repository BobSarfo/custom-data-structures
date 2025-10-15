package org.bob.priority_queue;

public class PriorityQueue <D,P extends Comparable<P>>{

    private CustomLinkedList<D,P> linkedList;

    public void push(D data){
        linkedList.add(data);
    }

    public void push(D data, P priority){
        linkedList.add(data,priority);
    }

    public D pop(){
       return linkedList.pop();
    }

    public D peek(){
        return linkedList.peek();
    }
    public void bump(D data, P priority){
        linkedList.bump(data,priority);
    }
}
