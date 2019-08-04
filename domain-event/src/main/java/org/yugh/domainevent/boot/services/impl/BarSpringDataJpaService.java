package org.yugh.domainevent.boot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.yugh.domainevent.boot.daos.IBarCrudRepository;
import org.yugh.domainevent.boot.domain.Bar;
import org.yugh.domainevent.boot.services.IBarService;

import java.io.Serializable;

public class BarSpringDataJpaService extends AbstractSpringDataJpaService<Bar> implements IBarService {

    @Autowired
    private IBarCrudRepository dao;

    public BarSpringDataJpaService() {
        super();
    }

    @Override
    protected CrudRepository<Bar, Serializable> getDao() {
        return dao;
    }

    @Override
    public Page<Bar> findPaginated(int page, int size) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
