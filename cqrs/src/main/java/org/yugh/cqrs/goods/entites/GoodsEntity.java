package org.yugh.cqrs.goods.entites;

import lombok.Data;
import org.yugh.cqrs.jpa.ddd.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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
