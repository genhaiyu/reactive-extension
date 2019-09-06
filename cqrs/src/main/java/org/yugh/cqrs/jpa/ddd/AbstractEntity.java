package org.yugh.cqrs.jpa.ddd;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * //公共对象
 *
 * @author: 余根海
 * @creation: 2019-04-05 21:50
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Data
@MappedSuperclass
public class AbstractEntity extends AbstractAggregateRoot implements Serializable {

    private static final long serialVersionUID = -2198068039743596283L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    @Column(name = "id")
    private Long id;


    @Setter(AccessLevel.PRIVATE)
    @Column(name = "created_at")
    @Convert(converter = InstantLongConverter.class)
    private Instant createdAt;

    @Setter(AccessLevel.PRIVATE)
    @Column(name = "updated_at")
    @Convert(converter = InstantLongConverter.class)
    private Instant updatedAt;

    @Version
    @Setter(AccessLevel.PRIVATE)
    @Column(name = "version")
    private Integer version;


    @PrePersist
    public void prePersist() {
        setCreatedAt(Instant.now());
        setUpdatedAt(Instant.now());
    }

    @PreUpdate
    public void preUpdate() {
        setUpdatedAt(Instant.now());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }

}
