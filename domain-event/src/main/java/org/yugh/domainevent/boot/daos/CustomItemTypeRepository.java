package org.yugh.domainevent.boot.daos;

import org.springframework.stereotype.Repository;
import org.yugh.domainevent.boot.domain.ItemType;

@Repository
public interface CustomItemTypeRepository {

    void deleteCustom(ItemType entity);

    void findThenDelete(Long id);
}
