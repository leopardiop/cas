package org.jasig.cas.web;

/**
 * Created by peng.luwei on 2014/11/3.
 */
public interface PasswordParser {

    String parse(String password);

    String parse(String key,String password);

}
