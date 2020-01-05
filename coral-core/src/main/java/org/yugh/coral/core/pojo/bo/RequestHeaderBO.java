package org.yugh.coral.core.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author yugenhai
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestHeaderBO implements Serializable {

    private static final long serialVersionUID = -738100944072955230L;
    private List baseList;
    private Map<String, Object> baseMap;
}
