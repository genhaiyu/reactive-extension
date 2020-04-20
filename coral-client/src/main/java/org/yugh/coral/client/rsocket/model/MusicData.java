package org.yugh.coral.client.rsocket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yugenhai
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicData {

    private String song;
    private int songIndex;

    public static MusicData musicDataException(Exception e){
        MusicData musicData = new MusicData();
        musicData.setSong(e.getMessage());
        return musicData;
    }
}
