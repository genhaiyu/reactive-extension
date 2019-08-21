package org.yugh.cqrs.goods.entites;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.yugh.common.ddd.AbstractEntity;
import org.yugh.common.ddd.InstantLongConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Date;

/**
 * @author yugenhai
 */
@Data
@Entity
@Table(name = "goods")
public class GoodsEntity extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "token_time")
    private Date tokenTime;


    private void tokenTimeNow() {
        new Date();
    }

}
