package org.jasig.cas.util;

import org.springframework.webflow.execution.RequestContext;

/**
 * Created by peng.luwei on 2014/11/16.
 */
public class FlowScopeUtils {

    public static boolean isExist(RequestContext context,String name){

        return context.getFlowScope().get(name) != null;
    }

}
