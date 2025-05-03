package org.example;

import java.util.List;

public class Word {
    private final String word;
    private List<MeaningInLanguage> meanings;

    public Word(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public List<MeaningInLanguage> getMeanings() {
        return meanings;
    }
}
