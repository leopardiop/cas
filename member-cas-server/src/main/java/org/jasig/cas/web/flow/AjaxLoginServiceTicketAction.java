package org.jasig.cas.web.flow;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.util.UniqueTicketIdGenerator;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public final class AjaxLoginServiceTicketAction extends AbstractAction {

    private static final String PREFIX = "LT";

    private static final String JSESSIONID = "jsessionid";

    private static final String SERVICE = "lop";

    @NotNull
    private UniqueTicketIdGenerator ticketIdGenerator;
  
    protected Event doExecute(final RequestContext context) {

        HttpServletRequest request = WebUtils.getHttpServletRequest(context);

        String logined = (String) context.getFlowScope().get("logined");

        if(logined == null){
            if ((request.getParameter("get-lt") != null) && (request.getParameter("get-lt").equalsIgnoreCase("true"))) {

                final String loginTicket = this.ticketIdGenerator.getNewTicketId(PREFIX);

                WebUtils.putLoginTicket(context, loginTicket);

                return result("loginTicketRequested");
            }
        }else{
            logger.info("not logined ");
        }

        Event event = context.getCurrentEvent();

        boolean isLoginSuccess;

        if ("success".equals(event.getId())) { //是否登录成功
            final Service service = WebUtils.getService(context);
            final String serviceTicket = WebUtils.getServiceTicketFromRequestScope(context);
            if (service != null){
                //设置登录成功之后 跳转的地址
                request.setAttribute("service", service.getId());
            }
            request.setAttribute("ticket", serviceTicket);
            isLoginSuccess = true;
            request.setAttribute("isLogin", isLoginSuccess);

            logger.info("success ");
            return result("success");
        }else {
            String captchaValidatorError = (String) context.getRequestScope().get("captchaValidatorError");
            isLoginSuccess = false;
            request.setAttribute("isLogin", isLoginSuccess);
            request.setAttribute("captcha", captchaValidatorError);
            final String loginTicket = this.ticketIdGenerator.getNewTicketId(PREFIX);
            WebUtils.putLoginTicket(context, loginTicket);

            logger.info("error ");
            return result("error");
        }
    }

    public UniqueTicketIdGenerator getTicketIdGenerator() {
        return ticketIdGenerator;
    }

    public void setTicketIdGenerator(UniqueTicketIdGenerator ticketIdGenerator) {
        this.ticketIdGenerator = ticketIdGenerator;
    }
}