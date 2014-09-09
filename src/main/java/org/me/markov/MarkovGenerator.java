package org.me.markov;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;


public class MarkovGenerator {

    Map<String, Node> nodes;
    Node rootNode;
    Parser parser;

    public MarkovGenerator() {
        nodes = new LinkedHashMap<String, Node>();
        rootNode = new Node("", false, false);
    }

    public void read(String inputFileName) throws IOException {
        System.out.println("Reading...");
        String contents = IOUtils.toString(new FileInputStream(inputFileName));
        createChain(contents);
    }

    protected void createChain(String contents) {
        parser = new Parser(contents);

        Node current = rootNode;
        while (parser.hasNextSentence()) {
            String sentence = parser.nextSentence();
            while (parser.hasNextWord()) {
                String token = parser.nextWord();
                Node newNode = createNode(token, parser.isStart(), parser.isTerminator());
                Node dest = current.addLink(newNode);
                if (parser.isStart() && current != rootNode) {
                    rootNode.addLink(newNode);
                }
                current = dest;
                nodes.put(token, current);
            }
        }
    }

    protected Node createNode(String token, boolean start, boolean end) {
        Node node = nodes.get(token);
        if (node == null) {
            node = new Node(token, start, end);
        }
        return node;
    }

    public String generate(int words) {
        System.out.println("Generating...");
        StringBuilder sb = new StringBuilder();
        boolean done = false;
        int count = 0;

        Node current = rootNode;
        while (count < words && !done) {
            current = current.randomNode();
            if (!current.end && StringUtils.isAlphanumeric(current.text)) {
                sb.append(' ');
            }
            sb.append(current.text);
            if (current.end) {
                sb.append("  ");
            }


            count++;
            if (count >= words && current.end) {
                done = true;
            }
        }

        return sb.toString();
    }

}
