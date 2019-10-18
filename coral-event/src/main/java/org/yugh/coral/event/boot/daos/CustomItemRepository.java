package org.yugh.coral.event.boot.daos;

import org.springframework.stereotype.Repository;
import org.yugh.coral.event.boot.domain.Item;

@Repository
public interface CustomItemRepository {

    void deleteCustom(Item entity);

    Item findItemById(Long id);

    void findThenDelete(Long id);

}
