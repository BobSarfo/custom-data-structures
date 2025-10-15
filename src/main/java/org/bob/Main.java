package org.bob;

import org.bob.priority_queue.CustomLinkedList;

public class Main {
    public static void main(String[] args) {
        CustomLinkedList<Integer,Integer> list = new CustomLinkedList<>();
        list.add(1,2);
        list.add(2,3);
        list.add(3,4);

        System.out.println(list.pop());
        System.out.println(list.pop());
        System.out.println(list.pop());


    }
}