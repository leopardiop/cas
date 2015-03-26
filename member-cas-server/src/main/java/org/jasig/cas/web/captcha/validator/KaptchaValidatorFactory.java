package org.jasig.cas.web.captcha.validator;

import org.apache.commons.lang.StringUtils;

/**
 * Created by peng.luwei on 2015/1/19.
 */
public class KaptchaValidatorFactory {

    public static Class getValidator(String key){

        Class clazz = null;

        if(StringUtils.isNotBlank(key)){
            clazz = ValidatorEnum.switchValue(key);
        }else
            clazz = ValidatorEnum.switchValue(ValidatorEnum.ERRORTWICE.getKey());

        return clazz;
    }
}
