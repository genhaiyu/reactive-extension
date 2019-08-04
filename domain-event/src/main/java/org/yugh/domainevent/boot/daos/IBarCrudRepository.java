package org.yugh.domainevent.boot.daos;

import org.springframework.data.repository.CrudRepository;
import org.yugh.domainevent.boot.domain.Bar;

import java.io.Serializable;

public interface IBarCrudRepository extends CrudRepository<Bar, Serializable> {
    //
}
