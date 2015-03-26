package org.jasig.cas.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by peng.luwei on 2014/11/7.
 */
public class CookieUtils {

    /**
     *设置cookie
     * @param response
     * @param cookieParams (cookie parementer. see Cookie )
     */
    public static void setCookie(HttpServletResponse response,Map<String,String> cookieParams){

        if(cookieParams == null){
            return;
        }

        Cookie cookie = new Cookie(cookieParams.get("name"),cookieParams.get("value"));

        setParam(response,cookieParams);

        response.addCookie(cookie);
    }

    /**
     * 得到指定名称cookie值
     * @param request
     * @param name
     * @return
     */
    public static String getCookie(HttpServletRequest request,String name){

        if (name == null || name.trim().length() == 0){
            return null;
        }

        Cookie[] cookies = request.getCookies();

        String value = "";

        if(cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies) {
                if(name.equals(cookie.getName())){
                    value = cookie.getValue();
                    break;
                }
            }
        }

        return value;
    }

    /**
     * 删除cookie
     * @param response
     * @param name cookie名称
     */
    public static void delCookie(HttpServletResponse response,String name){

        if (name == null || name.trim().length() == 0){
            return;
        }

        Cookie cookie = new Cookie(name,null);

        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private static void setParam(HttpServletResponse response, Map<String, String> cookieParams) {


    }

}
