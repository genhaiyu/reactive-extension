package org.yugh.domainevent.boot.daos;

import org.springframework.data.repository.CrudRepository;
import org.yugh.domainevent.boot.domain.MerchandiseEntity;

public interface InventoryRepository extends CrudRepository<MerchandiseEntity, Long> {
}
