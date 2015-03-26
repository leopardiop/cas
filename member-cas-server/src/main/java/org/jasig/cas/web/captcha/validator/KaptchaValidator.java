package org.jasig.cas.web.captcha.validator;

import org.springframework.webflow.execution.RequestContext;

/**
 * Created by peng.luwei on 2015/1/19.
 */
public interface KaptchaValidator {

    boolean validate(final RequestContext context);

}
