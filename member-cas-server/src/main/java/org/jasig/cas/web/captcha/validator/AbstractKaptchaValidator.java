package org.jasig.cas.web.captcha.validator;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.apache.commons.lang.StringUtils;
import org.jasig.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.execution.RequestContext;

/**
 * Created by peng.luwei on 2015/1/19.
 */
public abstract  class  AbstractKaptchaValidator implements KaptchaValidator{

    private ImageCaptchaService jcaptchaService;

    private static final String CAPTCHA_PARAMETER = "captcha";

    private static final Logger log = LoggerFactory.getLogger(AbstractKaptchaValidator.class);

    protected AbstractKaptchaValidator(ImageCaptchaService jcaptchaService) {
        this.jcaptchaService = jcaptchaService;
    }

    public boolean executeValidate(final RequestContext context) {

        boolean valid = false;

        String sessionId = WebUtils.getHttpServletRequest(context).getSession().getId();
        String captcha = context.getRequestParameters().get(CAPTCHA_PARAMETER);

        if(StringUtils.isNotBlank(sessionId) && StringUtils.isNotBlank(captcha)){
            try {
                valid = jcaptchaService.validateResponseForID(sessionId, captcha).booleanValue();
            } catch (CaptchaServiceException cse) {
                log.info("验证异常");
            }
        }
        return valid;
    }

    public ImageCaptchaService getJcaptchaService() {
        return jcaptchaService;
    }

    public void setJcaptchaService(ImageCaptchaService jcaptchaService) {
        this.jcaptchaService = jcaptchaService;
    }
}
