package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DictionaryRepository implements IDictionaryRepository {

    public static final String LIST_OF_WORDS_KEY = "list.of.words";

    @Override
    public Error insert(Word word, MeaningInLanguage meaning) {
        try {
            Jedis client = RedisUtil.getWorker();
            client.hset(word.getWord().toLowerCase(Locale.ROOT), meaning.getLanguage().name(), meaning.getMeaning().toLowerCase(Locale.ROOT));
            client.sadd(LIST_OF_WORDS_KEY, word.getWord().toLowerCase(Locale.ROOT));
            client.close();
            return null;
        } catch (Exception e) {
            return new Error(e.getMessage());
        }
    }

    @Override
    public void delete(Word word) {
        Jedis client = RedisUtil.getWorker();
        client.del(word.getWord().toLowerCase(Locale.ROOT));
        client.del(LIST_OF_WORDS_KEY);
        client.close();
    }

    @Override
    public List<MeaningInLanguage> search(String term) {

        return List.of();
    }

    @Override
    public MeaningInLanguage get(Word word, MeaningInLanguage.Language language) {
        try {
            Jedis client = RedisUtil.getWorker();
            String meaning = client.hget(word.getWord().toLowerCase(Locale.ROOT), language.name());
            client.close();
            MeaningInLanguage meaningInLanguage = new MeaningInLanguage(meaning, language);
            meaningInLanguage.setWord(word);
            return meaningInLanguage;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Word> listWords(int offset, int limit) {
        Jedis client = RedisUtil.getWorker();
        ScanParams scanParams = new ScanParams();
        scanParams.count(limit);
        ScanResult<String> result = client.sscan(LIST_OF_WORDS_KEY, String.valueOf(offset), scanParams);
        client.close();
        return result.getResult().stream().map(Word::new).collect(Collectors.toList());
    }
}
