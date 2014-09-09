package org.me.markov;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParserTest {

    private Parser parser;

    @Test
    public void wordParse() {
        parser = new Parser(MarkovGeneratorTest.SENTENCE);
        parser.nextSentence();

        int count = 0;
        while (parser.hasNextWord()) {
            System.out.println(parser.nextWord());
            if (count == 0) {
                assertTrue(parser.isStart());
            } else {
                assertFalse(parser.isStart());
            }

            count++;
            if (count == 6) {
                assertTrue(parser.isTerminator());
            } else {
                assertFalse(parser.isTerminator());
            }
        }
        assertEquals(6, count);
    }

    @Test
    public void sentenceParse() {
        parser = new Parser(MarkovGeneratorTest.SENTENCES);

        for (int i = 0; i < 4; i++) {
            String sentence = parser.nextSentence();
            assertNotNull(sentence);
            System.out.println(sentence);
        }
        assertFalse(parser.hasNextSentence());

    }

    @Test
    public void sentenceWordParse() {
        parser = new Parser(MarkovGeneratorTest.SENTENCES);

        while (parser.hasNextSentence()) {
            parser.nextSentence();
            while (parser.hasNextWord()) {
                String token = parser.nextWord();
                System.out.println(token);
            }
        }
    }


}
