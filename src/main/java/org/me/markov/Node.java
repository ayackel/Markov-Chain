package org.me.markov;

import java.util.Iterator;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;

public class Node {
    Bag<Node> links;
    String text;
    boolean start;
    boolean end;

    public Node(String text, boolean start, boolean end) {
        this.text = text;
        this.start = start;
        this.end = end;
        links = new HashBag<Node>();
    }

    public Node addLink(Node node) {
        links.add(node);

        return node;
    }

    public Node randomNode() {
        System.out.println("Links size: " + links.size());

        double random = Math.random();
        int index = (int) Math.floor(random * (links.size() + 1));

        System.out.println("Index of: " + index);

        Node node;
        Iterator<Node> iterator = links.iterator();
        int i = 0;
        do {
            node = iterator.next();
            i++;
        } while (i < index);

        return node;
    }

    @Override
    public String toString() {
        return "Node [links=" + links + ", text=" + text + ", start=" + start + ", end=" + end + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Node other = (Node) obj;
        if (text == null) {
            if (other.text != null) {
                return false;
            }
        } else if (!text.equals(other.text)) {
            return false;
        }
        return true;
    }

}
