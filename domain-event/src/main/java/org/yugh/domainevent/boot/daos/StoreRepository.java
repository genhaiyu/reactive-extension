package org.yugh.domainevent.boot.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yugh.domainevent.boot.domain.Store;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findStoreByLocationId(Long locationId);
}
