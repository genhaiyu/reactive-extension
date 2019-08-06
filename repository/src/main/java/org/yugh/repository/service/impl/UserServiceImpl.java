package org.yugh.repository.service.impl;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.yugh.repository.entites.QUserEntity;
import org.yugh.repository.entites.UserEntity;
import org.yugh.repository.repositories.UserRepository;
import org.yugh.repository.service.IUserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * // JPA + QUERYDSL 语法
 *
 * @author: 余根海
 * @creation: 2019-07-10 14:20
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
public class UserServiceImpl implements IUserService {

    /**
     * extends BaseJpaQueryFactory
     **/

    @PersistenceContext
    protected EntityManager manager;
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserEntity getUserByUserName(final String userName) {
        final JPAQuery<UserEntity> query = new JPAQuery(manager);
        final QUserEntity qUserDo = QUserEntity.userEntity;
        return query.from(qUserDo).where(qUserDo.name.eq(userName)).fetchOne();
    }

    @Override
    public void addUser(UserEntity userDo) {
        userRepository.save(userDo);
    }


    @Override
    public void update(UserEntity userDo) {
        userDo.preUpdate();
        userRepository.updateUser(userDo);
    }


    @Override
    public boolean exists(String userName) {
        boolean success = false;
        final JPAQuery<UserEntity> query = new JPAQuery(manager);
        final QUserEntity qUser = QUserEntity.userEntity;
        if (!CollectionUtils.isEmpty(query.from(qUser)
                .where(qUser.name.eq(userName)).fetch())) {
            success = true;
        }
        return success;
    }
}
