package org.me.markov;

import java.text.BreakIterator;

import org.apache.commons.lang3.StringUtils;

public class Parser {

    private String contents;
    private String sentence;
    private int sStart, sEnd;
    private int wStart, wEnd;
    private BreakIterator sentenceBoundary;
    private BreakIterator wordBoundary;
    private int wordCount;

    public Parser(String contents) {
        this.contents = contents;

        initSentenceFields(contents);
        initWordFields();
    }

    private void initSentenceFields(String contents) {
        sentenceBoundary = BreakIterator.getSentenceInstance();
        sentenceBoundary.setText(contents);
        sStart = sentenceBoundary.first();
        sEnd = sentenceBoundary.next();
    }

    private void initWordFields() {
        wordBoundary = BreakIterator.getWordInstance();
        wordCount = 0;
    }

    public boolean hasNextSentence() {
        return sEnd != BreakIterator.DONE;
    }

    public String nextSentence() {
        if (sEnd == BreakIterator.DONE) {
            return null;
        }

        sentence = contents.substring(sStart, sEnd);

        // Point to next token
        sStart = sEnd;
        sEnd = sentenceBoundary.next();

        // Init word tokens
        resetWordTokens();

        return sentence;
    }

    private void resetWordTokens() {
        wordBoundary.setText(sentence);
        wStart = wordBoundary.first();
        wEnd = wordBoundary.next();
        wordCount = 0;
    }

    public boolean hasNextWord() {
        boolean hasNext = wEnd != BreakIterator.DONE;

        if (hasNext) {
            int nextEnd = wordBoundary.next();
            wordBoundary.previous(); // Move pointer back
            hasNext = nextEnd != BreakIterator.DONE;
        }

        return hasNext;
    }

    public String nextWord() {
        if (wEnd == BreakIterator.DONE) {
            return null;
        }

        String token = sentence.substring(wStart, wEnd);

        wStart = wEnd;
        wEnd = wordBoundary.next();

        if (StringUtils.isWhitespace(token)) {
            // System.out.println("Skipping over: " + token);
            token = nextWord();
        } else {
            wordCount++;
        }

        return token;
    }

    public boolean isStart() {
        return wordCount == 1;
    }

    public boolean isTerminator() {
        return wEnd == BreakIterator.DONE;
    }

}
