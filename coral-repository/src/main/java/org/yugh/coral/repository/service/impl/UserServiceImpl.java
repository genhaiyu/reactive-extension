package org.yugh.coral.repository.service.impl;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.yugh.coral.repository.entites.QUserEntity;
import org.yugh.coral.repository.entites.UserEntity;
import org.yugh.coral.repository.pojo.dto.UserDTO;
import org.yugh.coral.repository.repositories.UserRepository;
import org.yugh.coral.repository.service.IUserService;

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
    public void addUser(UserDTO userDTO) {
        UserEntity userEntity = initUserEntity();
        BeanUtils.copyProperties(userDTO, userEntity);
        userRepository.save(userEntity);
    }


    @Override
    public void update(UserDTO userDTO) {
        UserEntity userEntity = initUserEntity();
        BeanUtils.copyProperties(userDTO, userEntity);
        userEntity.preUpdate();
        userRepository.updateUser(userEntity);
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

    private UserEntity initUserEntity() {
        return UserEntity.builder().build();
    }
}
