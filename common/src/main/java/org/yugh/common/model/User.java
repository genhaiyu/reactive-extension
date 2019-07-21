package org.yugh.common.model;

import lombok.Data;
import org.yugh.common.ddd.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * //usercenter微服务
 *
 * @author: 余根海
 * @creation: 2019-07-10 11:32
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Data
@Entity
@Table(name = "dataworks_user1")
public class User extends AbstractEntity implements java.io.Serializable {

    private static final long serialVersionUID = 2959529355461841650L;

    /**
     * 员工号唯一
     */
    @Column(name = "no")
    private String no;

    /**
     * 邮箱前缀
     */
    @Column(name = "name")
    private String userName;


    /**
     * 别名
     */
    @Column(name = "real_name")
    private String aliasName;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 性别
     */
    @Column(name = "sex")
    private String sex;

    /**
     * 公司
     */
    @Column(name = "company")
    private String company;

    /**
     * 部门
     */
    @Column(name = "depart")
    private String depart;

    /**
     * 权限
     */
    @Column(name = "admin")
    private int admin;

    /**
     * 是否有效
     */
    @Column(name = "disabled")
    private int disabled;

}
