package org.yugh.coral.core.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yugenhai
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestHeaderBO implements Serializable {

    private static final long serialVersionUID = -738100944072955230L;
    private String userAgent;
    private String hideVersion;
    private String hideType;
    private String xCountryTag;
    private String msgId;
    private Boolean verify = false;
}
