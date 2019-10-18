package org.yugh.coral.event.boot.daos;

import org.springframework.data.repository.CrudRepository;
import org.yugh.coral.event.boot.domain.Customer;

/**
 * JPA CrudRepository interface
 * 
 * @author ysharma2512
 *
 */
public interface CustomerRepository extends CrudRepository<Customer, Long>{

}
