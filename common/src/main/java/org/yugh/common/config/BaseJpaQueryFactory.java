/*@PersistenceContext
    public EntityManager manager;
    public JPAQueryFactory jpaQueryFactory;

    @PostConstruct
    public void init() {
        jpaQueryFactory = new JPAQueryFactory(manager);
}*/
package org.yugh.common.config;
import org.springframework.stereotype.Repository;

/**
 * //SQL基类
 *
 * @author: 余根海
 * @creation: 2019-07-10 19:57
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Repository
public class BaseJpaQueryFactory {

   /* @PersistenceContext
    protected EntityManager manager;*/
}
