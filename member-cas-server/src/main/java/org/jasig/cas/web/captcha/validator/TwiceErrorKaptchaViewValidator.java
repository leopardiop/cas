package org.jasig.cas.web.captcha.validator;

import com.octo.captcha.service.image.ImageCaptchaService;
import org.jasig.cas.util.FlowScopeUtils;
import org.springframework.webflow.execution.RequestContext;

/**
 * Created by peng.luwei on 2015/1/19.
 */
public class TwiceErrorKaptchaViewValidator extends AbstractKaptchaValidator{

    public TwiceErrorKaptchaViewValidator(ImageCaptchaService jcaptchaService) {
        super(jcaptchaService);
    }

    @Override
    public boolean validate(RequestContext context) {

        int count = FlowScopeUtils.isExist(context, "count") ? (Integer)context.getFlowScope().get("count") : 0;

        boolean valid = true;

        if (count > 2){
            valid = executeValidate(context);
        }

        return valid;
    }
}
