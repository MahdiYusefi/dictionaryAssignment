package org.example;

import java.util.List;

public class DictionaryCommand implements IDictionaryCommand {

    private final IDictionaryRepository repository;
    private final WordTree wordTree;

    public DictionaryCommand(IDictionaryRepository repository) {
        this.repository = repository;
        //can be done as an async task
        wordTree = new WordTree(repository.listWords(0, 100));
    }

    @Override
    public Error insert(Word word, MeaningInLanguage meaning) {
        wordTree.add(word);
        return repository.insert(word, meaning);
    }

    @Override
    public void delete(Word word) {
        //implement delete for word tree
        repository.delete(word);
    }

    @Override
    public List<Word> search(String term) {
        return wordTree.searchTerm(term);
    }

    @Override
    public MeaningInLanguage get(Word word, MeaningInLanguage.Language language) {
        return repository.get(word, language);
    }

    @Override
    public List<Word> listWords(int offset, int limit) {
        return repository.listWords(offset, limit);
    }
}
