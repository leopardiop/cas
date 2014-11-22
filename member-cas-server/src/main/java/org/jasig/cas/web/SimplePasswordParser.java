package org.jasig.cas.web;

import org.jasig.cas.util.encoder.DesHelper;
import org.jasig.cas.util.encoder.MD5Utils;

/**
 * Created by peng.luwei on 2014/11/3.
 */
public class SimplePasswordParser implements PasswordParser{
    @Override
    public String parse(String password) {
        return MD5Utils.getMD5(password);
    }

    @Override
    public String parse(String key, String password) {
        return MD5Utils.getMD5(password+key);
    }
}