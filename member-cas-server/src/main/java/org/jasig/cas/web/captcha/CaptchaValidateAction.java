package org.jasig.cas.web.captcha;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.jasig.cas.util.FlowScopeUtils;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public final class CaptchaValidateAction extends AbstractAction {

    private ImageCaptchaService jcaptchaService;

	private String captchaValidationParameter = "captcha";

	protected Event doExecute(final RequestContext context) {
		String captcha_response = context.getRequestParameters().get(captchaValidationParameter);
		String username = context.getRequestParameters().get("username");

        context.getFlowScope().put("username",username);
        boolean valid = true;

        int count = FlowScopeUtils.isExist(context,"count") ? (Integer)context.getFlowScope().get("count") : 0;

        logger.info("count : "+count);
        if (captcha_response != null && count > 2) {
            String id = WebUtils.getHttpServletRequest(context).getSession().getId();
			if (id != null) {
				try {
                    valid = jcaptchaService.validateResponseForID(id, captcha_response).booleanValue();
				} catch (CaptchaServiceException cse) {
                    cse.printStackTrace();
				}
			}
		}
        if (valid) {
            return success();
		}

        context.getRequestScope().put("captchaValidatorError", "bad");
        return error();
	}

    public ImageCaptchaService getJcaptchaService() {
        return jcaptchaService;
    }

    public void setJcaptchaService(ImageCaptchaService jcaptchaService) {
        this.jcaptchaService = jcaptchaService;
    }
}