package it.introini.tilserver.db.manager.til;

import java.time.Instant;

/**
 * Created by thomas on 12/02/16.
 */
public class Til {
    private final long id;
    private final Instant timestamp;
    private final String content;

    public static Til of(long id,Instant timestamp,String content){
        return new Til(id, timestamp, content);
    }

    private Til(long id, Instant timestamp, String content) {
        this.id = id;
        this.timestamp = timestamp;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }
}
