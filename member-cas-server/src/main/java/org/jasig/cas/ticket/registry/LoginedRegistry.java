package org.jasig.cas.ticket.registry;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peng.luwei on 2015/3/19.
 */
public class LoginedRegistry {

    private Map<String,String> loginMap = new HashMap<String,String>();

    public void put(String key,String value){
        loginMap.put(key,value);
    }

    public String get(String key){
        return loginMap.get(key);
    }

    public String remove(String key){
        return loginMap.remove(key);
    }

}
