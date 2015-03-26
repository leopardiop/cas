package org.jasig.cas.web.captcha.validator;

import com.octo.captcha.service.image.ImageCaptchaService;
import org.springframework.webflow.execution.RequestContext;

/**
 * Created by peng.luwei on 2015/1/19.
 */
public class NoErrorKaptchaValidator extends AbstractKaptchaValidator{


    public NoErrorKaptchaValidator(ImageCaptchaService jcaptchaService) {
        super(jcaptchaService);
    }

    @Override
    public boolean validate(RequestContext context) {
        return executeValidate(context);
    }
}
