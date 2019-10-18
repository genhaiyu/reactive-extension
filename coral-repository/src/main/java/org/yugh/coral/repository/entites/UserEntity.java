package org.yugh.coral.repository.entites;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Builder;
import lombok.Data;
import org.yugh.coral.repository.ddd.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * // 用户基本信息
 *
 * @author: 余根海
 * @creation: 2019-04-08 15:40
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Builder
@Data
@Table(name = "sys_user")
@Entity
@QueryEntity
public class UserEntity extends AbstractEntity {


    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    public UserEntity(String name, String password , String phone) {
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    public UserEntity() {
    }
}
