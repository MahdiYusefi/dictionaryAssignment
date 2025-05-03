package org.example;


import java.util.ArrayList;
import java.util.List;

public class WordTree {
    private final TrieNode root = new TrieNode("");


    private static WordTree instance;


    public WordTree() {
    }


    public WordTree(List<Word> words) {
        for (Word word : words) {
            add(word);
        }
    }


    public void add(Word word) {
        String[] split = word.getWord().split("");
        TrieNode node = root;
        for (String s : split) {
            TrieNode trieNode = searchOnTree(node, s);
            if (trieNode == null) {
                trieNode = new TrieNode(s);
                node.getChildren().add(trieNode);
            }
            node = trieNode;
        }
        node.setEndOfWord(true);
    }

    public List<Word> searchTerm(String term) {
        List<Word> words = new ArrayList<>();
        TrieNode node = root;
        for (String ch : term.split("")) {
            node = searchOnTree(node, ch);
            if (node == null) {
                return words;
            }
        }
        collectWords(node, new StringBuilder(term), words);
        return words;
    }

    private void collectWords(TrieNode node, StringBuilder prefix, List<Word> result) {
        if (node.isEndOfWord()) {
            result.add(new Word(prefix.toString()));
        }
        for (TrieNode child : node.getChildren()) {
            prefix.append(child.getValue());
            collectWords(child, prefix, result);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    private TrieNode searchOnTree(TrieNode node, String character) {
        for (TrieNode child : node.getChildren()) {
            if (character.equals(child.getValue())) {
                return child;
            }
        }
        return null;
    }


    public static void main(String[] args) {
        WordTree tree = new WordTree();
        tree.add(new Word("hello"));
        tree.add(new Word("hell"));
    }

}
