package org.yugh.coral.event.boot.daos;

import org.springframework.data.repository.CrudRepository;
import org.yugh.coral.event.boot.domain.Bar;

import java.io.Serializable;

public interface IBarCrudRepository extends CrudRepository<Bar, Serializable> {
    //
}
