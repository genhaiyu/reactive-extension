package io.shixinyangyy.client.rsocket.repository;

import io.shixinyangyy.client.rsocket.model.MusicData;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// @Repository
public class MusicRepository {


    private final ReactiveRedisOperations<String, MusicData> redisTemplate;

    public MusicRepository(ReactiveRedisOperations<String, MusicData> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public Mono<Void> saveMusic(MusicData musicData){
        return Mono.when(
                redisTemplate.opsForHash().put("music", musicData.getId(), musicData),
                redisTemplate.opsForZSet().add(musicData.getAuthor().toLowerCase().replaceAll("\\s",""), musicData, musicData.getDate().toEpochDay())
        ).then();
    }

    public Flux<MusicData> getMusicData(String author){
        return redisTemplate.opsForZSet().reverseRange(author, Range.unbounded());
    }
}
