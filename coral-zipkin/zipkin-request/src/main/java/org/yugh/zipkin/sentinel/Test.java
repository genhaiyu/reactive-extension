package org.yugh.zipkin.sentinel;

import java.net.URLDecoder;

/**
 * @author genhai yu
 */
public class Test {


    public static void main(String[] args) throws  Exception{

        String string = URLDecoder.decode("%E5%8C%97%E4%BA%AC","UTF-8");

        System.out.println(string);
    }
}
