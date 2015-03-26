package org.jasig.cas.web.captcha.validator;

/**
 * Created by peng.luwei on 2015/1/19.
 */
public enum ValidatorEnum {

    ERRORTWICE("twice",TwiceErrorKaptchaViewValidator.class),
    NOERROR("no",NoErrorKaptchaValidator.class);

    private String key;

    private Class clazz;

    private ValidatorEnum(String key, Class clazz) {
        this.key = key;
        this.clazz = clazz;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public static Class switchValue(String key){

        for (ValidatorEnum validatorEnum : values()) {
            if(validatorEnum.getKey().equals(key)){
                return validatorEnum.getClazz();
            }
        }
        return null;
    }
}
