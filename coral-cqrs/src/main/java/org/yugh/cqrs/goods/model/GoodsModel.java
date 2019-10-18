package org.yugh.cqrs.goods.model;

import lombok.Data;
import lombok.Setter;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author yugenhai
 */
@Data
public class GoodsModel implements Serializable {

    private static final long serialVersionUID = -5146800514795542702L;

    private Integer id;

    private String name;

    private Integer stock;

    private int version;

    @Setter
    private static Instant createdAt;

    @PrePersist
    public void prePersist() {
        setCreatedAt(Instant.now());
    }


}
