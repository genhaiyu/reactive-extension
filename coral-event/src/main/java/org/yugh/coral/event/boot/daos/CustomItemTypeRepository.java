package org.yugh.coral.event.boot.daos;

import org.springframework.stereotype.Repository;
import org.yugh.coral.event.boot.domain.ItemType;

@Repository
public interface CustomItemTypeRepository {

    void deleteCustom(ItemType entity);

    void findThenDelete(Long id);
}
