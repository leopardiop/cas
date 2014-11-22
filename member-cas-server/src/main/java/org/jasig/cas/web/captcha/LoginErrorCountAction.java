package org.jasig.cas.web.captcha;


import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public final class LoginErrorCountAction extends AbstractAction {
 
   protected Event doExecute(final RequestContext context) {
      int count;
      try {
         count = (Integer)context.getFlowScope().get("count");
      } catch (Exception e) {
         count=0;
      }
      count++;
 
      context.getFlowScope().put("count",count);

      return error();
   }
}