package myLinkedList;

import myLinkedList.MyLinkedList;

import java.util.NoSuchElementException;

public class MyQueue<T> extends MyLinkedList<T> {

  //enqueue, dequeue
  private Node<T> tail;



  public void enqueue(T data) {
    Node<T> node = new Node<>(data);
    if(this.head == null) {
      this.head = node;
      this.tail = node;
    } else {
      this.tail.setNext(node);
      this.tail = node;
    }
    this.size++;
  }


  //
  public T dequeue() {
    if(this.head == null) {
      throw new NoSuchElementException();
    }

    T data = head.getData();
    this.head = this.head.getNext();
    if(head == null) {
      this.tail = null;
    }
    this.size--;
    return data;
  }
}
