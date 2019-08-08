package org.yugh.repository.pojo.dto;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author yugenhai
 */
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -6619139287232404433L;

    private String name;

    private String phone;

    private String password;
}
