package org.yugh.domainevent.boot.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.yugh.domainevent.boot.domain.Foo;

public interface IFooService extends IOperations<Foo> {

    Foo retrieveByName(String name);
    
    Page<Foo> findPaginated(Pageable pageable);

}
