package org.yugh.repository.repositories;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.yugh.common.ddd.BaseRepository;
import org.yugh.common.model.QUser;
import org.yugh.common.model.User;

/**
 * //用户
 *
 * @author: 余根海
 * @creation: 2019-07-10 13:14
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
public interface UserRepository extends BaseRepository<User, Long>, QuerydslPredicateExecutor<QUser> {


    @Modifying
    @Query(value = "update User u set u.aliasName = :#{#userDo.aliasName}," +
            " u.email = :#{#userDo.email}, u.updatedAt = :#{#userDo.updatedAt} where u.userName = :#{#userDo.userName}")
    void updateUser(@Param("userDo") User userDo);

}
