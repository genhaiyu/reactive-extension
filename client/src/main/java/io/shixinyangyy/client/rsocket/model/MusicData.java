package io.shixinyangyy.client.rsocket.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicData {

    private String id;
    private String songInfo;
    private String author;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;


    public static MusicData of(MusicData musicData){
        return new MusicData(musicData.getSongInfo(), musicData.getAuthor());
    }

    public MusicData(String songInfo, String author){
        this.id = UUID.randomUUID().toString();
        this.songInfo = songInfo;
        this.author = author;
        this.date =getRandomDate();
    }

    private static LocalDate getRandomDate() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        return LocalDate.of(r.nextInt(1992, 2020), r.nextInt(1, 13), r.nextInt(1, 29));
    }


    public static MusicData musicDataException(Exception e){
        MusicData musicData = new MusicData();
        musicData.setSongInfo(e.getMessage());
        return musicData;
    }
}
