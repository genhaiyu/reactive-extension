package org.yugh.product.controller;

import lombok.Data;

/**
 * @author yugenhai
 */
@Data
public class ExecuteVO {

    private String progressId;
    private String connectionId;
    private String sqlContent;
}
