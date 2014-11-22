package org.jasig.cas.web.flow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.util.UniqueTicketIdGenerator;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.execution.RequestContext;

import javax.validation.constraints.NotNull;

/**
 * Created by peng.luwei on 2014/10/31.
 */
public class ExtGenerateLoginTicketAction {


    /** 3.5.1 - Login tickets SHOULD begin with characters "LT-" */
    private static final String PREFIX = "LT";

    /** Logger instance */
    private final Log logger = LogFactory.getLog(getClass());

    @NotNull
    private UniqueTicketIdGenerator ticketIdGenerator;

    public final String generate(final RequestContext context) {


        final String loginTicket = this.ticketIdGenerator.getNewTicketId(PREFIX);
        this.logger.debug("Generated login ticket " + loginTicket);
        WebUtils.putLoginTicket(context, loginTicket);

        String getlt=  WebUtils.getHttpServletRequest(context).getParameter("get-lt");

        System.out.println(" ================================= "+getlt);

        /*if(getlt != null && "true".equals(getlt)){

            context.getFlowScope().put("getlt",getlt);

            return "get";
        }*/

        return "generated";
    }

    public void setTicketIdGenerator(final UniqueTicketIdGenerator generator) {
        this.ticketIdGenerator = generator;
    }

}
