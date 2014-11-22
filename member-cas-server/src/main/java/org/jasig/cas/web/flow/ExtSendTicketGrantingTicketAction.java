package org.jasig.cas.web.flow;

import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.validation.constraints.NotNull;

/**
 * Created by peng.luwei on 2014/11/2.
 */
public class ExtSendTicketGrantingTicketAction extends AbstractAction {

    @NotNull
    private CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator;

    /** Instance of CentralAuthenticationService. */
    @NotNull
    private CentralAuthenticationService centralAuthenticationService;

    protected Event doExecute(final RequestContext context) {
        final String ticketGrantingTicketId = WebUtils.getTicketGrantingTicketId(context);
        final String ticketGrantingTicketValueFromCookie = (String) context.getFlowScope().get("ticketGrantingTicketId");

        if (ticketGrantingTicketId == null) {
            return success();
        }

        this.ticketGrantingTicketCookieGenerator.addCookie(WebUtils.getHttpServletRequest(context), WebUtils
                .getHttpServletResponse(context), ticketGrantingTicketId);

        if (ticketGrantingTicketValueFromCookie != null && !ticketGrantingTicketId.equals(ticketGrantingTicketValueFromCookie)) {
            this.centralAuthenticationService
                    .destroyTicketGrantingTicket(ticketGrantingTicketValueFromCookie);
        }

        System.out.println("ticketGrantingTicketId = " + ticketGrantingTicketId);

        return success();
    }

    public void setTicketGrantingTicketCookieGenerator(final CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator) {
        this.ticketGrantingTicketCookieGenerator= ticketGrantingTicketCookieGenerator;
    }

    public void setCentralAuthenticationService(
            final CentralAuthenticationService centralAuthenticationService) {
        this.centralAuthenticationService = centralAuthenticationService;
    }


}
