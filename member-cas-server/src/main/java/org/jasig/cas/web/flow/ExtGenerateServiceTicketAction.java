package org.jasig.cas.web.flow;

import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.ticket.TicketException;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.util.StringUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.validation.constraints.NotNull;

/**
 * Created by peng.luwei on 2014/11/2.
 */
public class ExtGenerateServiceTicketAction extends AbstractAction {

    /** Instance of CentralAuthenticationService. */
    @NotNull
    private CentralAuthenticationService centralAuthenticationService;

    protected Event doExecute(final RequestContext context) {
        final Service service = WebUtils.getService(context);
        final String ticketGrantingTicket = WebUtils.getTicketGrantingTicketId(context);

        try {
            final String serviceTicketId = this.centralAuthenticationService
                    .grantServiceTicket(ticketGrantingTicket,
                            service);
            WebUtils.putServiceTicketInRequestScope(context,
                    serviceTicketId);

            System.err.println("serviceTicketId = " + serviceTicketId);
            return success();
        } catch (final TicketException e) {
            if (isGatewayPresent(context)) {
                return result("gateway");
            }
        }

        return error();
    }

    public void setCentralAuthenticationService(
            final CentralAuthenticationService centralAuthenticationService) {
        this.centralAuthenticationService = centralAuthenticationService;
    }

    protected boolean isGatewayPresent(final RequestContext context) {
        return StringUtils.hasText(context.getExternalContext()
                .getRequestParameterMap().get("gateway"));
    }


}
