package org.yugh.repository.repositories;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.yugh.repository.ddd.BaseRepository;
import org.yugh.repository.entites.QUserEntity;
import org.yugh.repository.entites.UserEntity;

/**
 * //用户
 *
 * @author: 余根海
 * @creation: 2019-07-10 13:14
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
public interface UserRepository extends BaseRepository<UserEntity, Long>, QuerydslPredicateExecutor<QUserEntity> {


    @Modifying
    @Query(value = "update UserEntity u set u.name = :#{#userEntity.name}," +
            " u.phone = :#{#userEntity.phone}, u.updatedAt = :#{#userEntity.updatedAt} where u.name = :#{#userEntity.name}")
    void updateUser(@Param("userEntity") UserEntity userEntity);

}
