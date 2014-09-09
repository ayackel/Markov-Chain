package org.me.markov;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App
{
    private static String filename = "src/main/resources/scott.txt";

    public static void main(String[] args) throws IOException
    {
        // File test = new File(filename);
        //
        // String contents = FileUtils.readFileToString(test);
        MarkovGenerator generator = new MarkovGenerator();
        generator.read(filename);
        System.out.println(generator.generate(200));

        // System.out.println("Found " + generator.nodes.size() + " words.");
        // for (Node node : generator.nodes.values()) {
        // System.out.println(node.text);
        // }
    }

}
