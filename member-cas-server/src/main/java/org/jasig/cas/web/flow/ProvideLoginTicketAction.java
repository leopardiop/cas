package org.jasig.cas.web.flow;

import org.apache.commons.lang.StringUtils;
import org.jasig.cas.ticket.registry.LoginedRegistry;
import org.jasig.cas.util.UniqueTicketIdGenerator;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * Created by peng.luwei on 2014/10/31.
 */
public class ProvideLoginTicketAction extends AbstractAction
{

    private static final String PREFIX = "LT";

    private static final String JSESSIONID = "jsessionid";

    private static final String SERVICE_NAME = "lop";

    private LoginedRegistry loginedRegistry;

    @NotNull
    private UniqueTicketIdGenerator ticketIdGenerator;

    protected Event doExecute(RequestContext context)
            throws Exception
    {
        HttpServletRequest request = WebUtils.getHttpServletRequest(context);

        String receiver = request.getParameter("receiver");
        String username = request.getParameter("username");
        logger.info("receiver : ["+receiver+"], username : ["+username+"]");

        context.getFlowScope().put("receiver",receiver);
        context.getFlowScope().put("username",username);

        boolean serviceBool = false;

        Cookie[] cookies = request.getCookies();

        if(cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies) {
                logger.info("cooke : ["+cookie.getName()+"],value : ["+cookie.getValue()+"]");
                if (SERVICE_NAME.equals(cookie.getName())){
                    serviceBool = true;
                    String value = loginedRegistry.get(cookie.getValue());

                    logger.info("loginedRegistry value : ["+value+"]");
                    if(StringUtils.isNotBlank(value) && value.equals(receiver)){
                        logger.info("receiver : ["+receiver+"] logined");
                        return result("suspend");
                    }
                    break;
                }
            }
        }
        if(serviceBool) {
            context.getFlowScope().put("logined", "true");
            return result("logined");
        }

        if ((request.getParameter("get-lt") != null) && (request.getParameter("get-lt").equalsIgnoreCase("true"))) {

            return result("loginTicketRequested");
        }
        return result("countinue");
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
}