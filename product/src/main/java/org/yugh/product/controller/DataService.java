package org.yugh.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yugh.auth.util.ResultJson;
import org.yugh.product.service.SqlExecute;

/**
 * @author yugenhai
 */
@Service
public class DataService {

    @Autowired
    SqlExecute sqlExecute;

    public ResultJson preview(DataSetDTO dataSetDTO) {

        ExecuteVO execute = new ExecuteVO();
        execute.setConnectionId("e2e50915-c447-4adf-8115-d9797e2b322a");
        execute.setSqlContent("select * from  g3.app_data_app_list  limit 10");
        ResultJson resultJson = sqlExecute.sqlExecute(execute.getConnectionId(), execute.getSqlContent());
        System.out.println(resultJson);
        return resultJson;
    }
}
