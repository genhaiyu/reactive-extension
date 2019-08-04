package org.yugh.domainevent.boot.daos;

import org.springframework.data.repository.CrudRepository;
import org.yugh.domainevent.boot.domain.Customer;

/**
 * JPA CrudRepository interface
 * 
 * @author ysharma2512
 *
 */
public interface CustomerRepository extends CrudRepository<Customer, Long>{

}
