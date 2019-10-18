package org.yugh.coral.auth;

import org.yugh.coral.auth.base.TestAbstract;

import java.net.URLEncoder;

/**
 * @author yugenhai
 */
public class EncodeTest extends TestAbstract {


    public static void main(String[] args) throws Exception {

        String s = "name=新增报表测试&createUserId=&pageIndex=1&perPage=10";
        String str = URLEncoder.encode(s, "UTF-8");
        System.out.println(str);
    }

}
