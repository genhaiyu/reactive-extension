package org.yugh.coral.repository.ddd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 储存器基类
 *
 * @author: 余根海
 * @creation: 2019-04-05 12:46
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {


}
