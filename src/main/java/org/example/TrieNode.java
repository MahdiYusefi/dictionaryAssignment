package org.example;

import java.util.ArrayList;
import java.util.List;

public class TrieNode {
    private String value;
    private List<TrieNode> children = new ArrayList<>();
    private boolean isEndOfWord;

    public TrieNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public List<TrieNode> getChildren() {
        return children;
    }

    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }
}
