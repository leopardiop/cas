package org.jasig.cas.web.flow;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.ticket.registry.DefaultTicketRegistry;
import org.jasig.cas.ticket.registry.LoginedRegistry;
import org.jasig.cas.util.UniqueTicketIdGenerator;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public final class AjaxLoginServiceTicketAction extends AbstractAction {

    private static final String PREFIX = "LT";

    private static final String JSESSIONID = "jsessionid";

    private static final String SERVICE_NAME = "lop";

    @NotNull
    private UniqueTicketIdGenerator ticketIdGenerator;

    private LoginedRegistry loginedRegistry;

    private DefaultTicketRegistry ticketRegistry;
  
    protected Event doExecute(final RequestContext context) {

        HttpServletRequest request = WebUtils.getHttpServletRequest(context);

        final String ticketGrantingTicketId = WebUtils.getTicketGrantingTicketId(context);

        String logined = (String) context.getFlowScope().get("logined");

        String receiver = (String) context.getFlowScope().get("receiver");

        String username = (String) context.getFlowScope().get("username");

        String id = loginedRegistry.remove(username);

        Event event = context.getCurrentEvent();

        boolean suspend = false;

        logger.info("event : ["+event.getId()+"] , receiver : ["+receiver+"] ,username : ["+username+"],id : ["+id+"]");

        if("suspend".equals(event.getId()) || receiver.equals(id)){
            suspend = true;
            request.setAttribute("same", "suspend");
            request.setAttribute("username", username);
        }

        //获取登录ticket
        if(logined == null){
            if ((request.getParameter("get-lt") != null) && (request.getParameter("get-lt").equalsIgnoreCase("true"))) {

                getTicket(context);

                return result("loginTicketRequested");
            }
        }else{
            logger.info("not logined ");
        }

        if(suspend){
            getTicket(context);
            request.setAttribute("isLogin",false);
            return result("loginTicketRequested");
        }

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

            logger.info("username : ["+username+"] success");

            loginedRegistry.put(ticketGrantingTicketId, id);
            logger.info("lop : ["+ticketGrantingTicketId+"] put loginedRegistry");

            return result("success");
        }else {
            String captchaValidatorError = (String) context.getRequestScope().get("captchaValidatorError");
            isLoginSuccess = false;
            request.setAttribute("isLogin", isLoginSuccess);
            request.setAttribute("captcha", captchaValidatorError);
            getTicket(context);

            logger.info("error ");
            return result("error");
        }
    }

    private void getTicket(RequestContext context) {
        final String loginTicket = this.ticketIdGenerator.getNewTicketId(PREFIX);

        WebUtils.putLoginTicket(context, loginTicket);
    }

    public UniqueTicketIdGenerator getTicketIdGenerator() {
        return ticketIdGenerator;
    }

    public void setTicketIdGenerator(UniqueTicketIdGenerator ticketIdGenerator) {
        this.ticketIdGenerator = ticketIdGenerator;
    }

    public void setLoginedRegistry(LoginedRegistry loginedRegistry) {
        this.loginedRegistry = loginedRegistry;
    }

    public void setTicketRegistry(DefaultTicketRegistry ticketRegistry) {
        this.ticketRegistry = ticketRegistry;
    }
}