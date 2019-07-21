package org.yugh.common.pojo.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * //传输
 *
 * @author: 余根海
 * @creation: 2019-07-10 11:47
 * @Copyright © 2019 yugenhai. All rights reserved.
 */
@Data
@Builder
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -8201209799583781642L;

    /**
     * 员工号唯一
     */
    @NotEmpty(message = "员工号不能为空")
    private String no;

    /**
     * 邮箱前缀
     */
    @NotEmpty(message = "姓名拼音不能为空")
    private String userName;


    /**
     * 别名
     */
    private String aliasName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private String sex;

    /**
     * 公司
     */
    private String company;

    /**
     * 部门
     */
    private String depart;

    /**
     * 权限
     */
    private int admin;

    /**
     * 是否有效
     */
    private int disabled;

}
