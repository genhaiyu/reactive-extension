package org.yugh.repository.service.impl;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.yugh.common.config.BaseJpaQueryFactory;
import org.yugh.common.model.QUser;
import org.yugh.common.model.User;
import org.yugh.repository.repositories.UserRepository;
import org.yugh.repository.service.IUserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * //
 *
 * @author: 余根海
 * @creation: 2019-07-10 14:20
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
public class UserServiceImpl extends BaseJpaQueryFactory implements IUserService {

    @PersistenceContext
    protected EntityManager manager;
    @Autowired
    private UserRepository userRepository;


    @Override
    public User getUserByUserName(final String userName) {
        final JPAQuery<User> query = new JPAQuery(manager);
        final QUser qUserDo = QUser.user;
        return query.from(qUserDo).where(qUserDo.userName.eq(userName)).fetchOne();
    }

    @Override
    public void addUser(User userDo) {
        userRepository.save(userDo);
    }


    @Override
    public void update(User userDo) {
        userDo.preUpdate();
        userRepository.updateUser(userDo);
    }


    @Override
    public boolean exists(String userName) {
        boolean success = false;
        final JPAQuery<User> query = new JPAQuery(manager);
        final QUser qUser = QUser.user;
        if (!CollectionUtils.isEmpty(query.from(qUser)
                .where(qUser.userName.eq(userName)).fetch())) {
            success = true;
        }
        return success;
    }
}
