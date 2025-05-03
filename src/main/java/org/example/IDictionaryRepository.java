package org.example;

import java.util.List;

public interface IDictionaryRepository {
    Error insert(Word word, MeaningInLanguage meaning);

    void delete(Word word);

    List<MeaningInLanguage> search(String term);

    MeaningInLanguage get(Word word, MeaningInLanguage.Language language);

    List<Word> listWords(int offset, int limit);
}
