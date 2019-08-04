package org.yugh.domainevent.multipledb.dao.product;

import com.baeldung.multipledb.model.product.ProductMultipleDB;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<ProductMultipleDB, Integer> {

    List<ProductMultipleDB> findAllByPrice(double price, Pageable pageable);
}
