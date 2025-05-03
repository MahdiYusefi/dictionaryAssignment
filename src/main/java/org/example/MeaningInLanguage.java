package org.example;

public class MeaningInLanguage {

    public enum Language {
        FRENCH, GERMAN, FARSI
    }

    private final String meaning;
    private final Language language;
    private Word word;

    public MeaningInLanguage(String meaning, Language language) {
        this.meaning = meaning;
        this.language = language;
    }

    public String getMeaning() {
        return meaning;
    }

    public Language getLanguage() {
        return language;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }
}
