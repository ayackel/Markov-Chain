package org.me.markov;

import static org.junit.Assert.*;

import org.apache.commons.collections4.Bag;
import org.junit.Before;
import org.junit.Test;

public class MarkovGeneratorTest {

    public static String NO_LOOPS = "An iterator over a collection";
    public static String ONE_LOOP = "Map it to it";
    public static String ONE_LOOP_TWO_LINKS = "Map it to it again";
    public static String ONE_LOOP_TWO_LINKS_WEIGHT = "Map it to it again it again";
    public static String SENTENCE = "This is a short sentence.";
    public static String SENTENCES = "This is a short sentence. Here is another one?  Wow!  Then we have one more.";

    MarkovGenerator generator;

    @Before
    public void setup() {
        generator = new MarkovGenerator();
    }

    @Test
    public void testCreateChainNoLoops() {
        // when
        generator.createChain(NO_LOOPS);

        // then
        assertEquals(5, generator.nodes.size());

        Node current = generator.rootNode;
        assertNotNull(current);
        assertEquals("", current.text);

        current = current.links.iterator().next();
        assertEquals("An", current.text);

        current = current.links.iterator().next();
        assertEquals("iterator", current.text);

        current = current.links.iterator().next();
        assertEquals("over", current.text);

        current = current.links.iterator().next();
        assertEquals("a", current.text);

        current = current.links.iterator().next();
        assertEquals("collection", current.text);
    }

    @Test
    public void testCreateChainOneLoop() {
        // when
        generator.createChain(ONE_LOOP);

        // then
        assertEquals(3, generator.nodes.size());

        Node current = generator.rootNode;
        assertNotNull(current);
        assertEquals("", current.text);

        current = current.links.iterator().next();
        assertEquals("Map", current.text);

        current = current.links.iterator().next();
        assertEquals("it", current.text);
        Node it = current;

        current = current.links.iterator().next();
        assertEquals("to", current.text);

        current = current.links.iterator().next();
        assertSame(it, current);
    }

    @Test
    public void testCreateChainOneLoopTwoLinks() {
        // when
        generator.createChain(ONE_LOOP_TWO_LINKS);

        // then
        assertEquals(4, generator.nodes.size());

        Node current = generator.rootNode;
        assertNotNull(current);
        assertEquals("", current.text);

        current = current.links.iterator().next();
        assertEquals("Map", current.text);

        current = current.links.iterator().next();
        assertEquals("it", current.text);
        assertEquals(2, current.links.size());
    }

    @Test
    public void testCreateChainOneLoopTwoLinksWeight() {
        // when
        generator.createChain(ONE_LOOP_TWO_LINKS_WEIGHT);

        // then
        assertEquals(4, generator.nodes.size());

        Node current = generator.rootNode;
        assertNotNull(current);
        assertEquals("", current.text);

        current = current.links.iterator().next();
        assertEquals("Map", current.text);

        current = current.links.iterator().next();
        assertEquals("it", current.text);
        assertEquals(2, current.links.uniqueSet().size());

        Bag<Node> links = current.links;
        for (Node node : links.toArray(new Node[0])) {
            if ("again".equals(node.text)) {
                assertEquals(2, links.getCount(node));
            } else {
                assertEquals(1, links.getCount(node));
            }
        }
    }

    @Test
    public void createChainWithSentences() {
        // when
        generator.createChain(SENTENCES);

        // then
        assertEquals(16, generator.nodes.size());

    }

    // TODO Sentence terminations
    // TODO Handle sentence starts / capitalization
    // TODO ignore case?
}
