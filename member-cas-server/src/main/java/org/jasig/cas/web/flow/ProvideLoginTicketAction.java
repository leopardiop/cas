package org.jasig.cas.web.flow;

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

    private static final String SERVICE = "lop";

    @NotNull
    private UniqueTicketIdGenerator ticketIdGenerator;

    protected Event doExecute(RequestContext context)
            throws Exception
    {
        HttpServletRequest request = WebUtils.getHttpServletRequest(context);

        boolean jsBool = true;
        boolean serviceBool = false;

        Cookie[] cookies = request.getCookies();

        if(cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies) {
                if (SERVICE.equals(cookie.getName())){
                    serviceBool = true;
                }
            }
        }


        if(jsBool && serviceBool){
            context.getFlowScope().put("logined","true");
            return result("logined");
        }

        if ((request.getParameter("get-lt") != null) && (request.getParameter("get-lt").equalsIgnoreCase("true"))) {

            return result("loginTicketRequested");
        }
        return result("continue");
    }

    public UniqueTicketIdGenerator getTicketIdGenerator() {
        return ticketIdGenerator;
    }

    public void setTicketIdGenerator(UniqueTicketIdGenerator ticketIdGenerator) {
        this.ticketIdGenerator = ticketIdGenerator;
    }
}