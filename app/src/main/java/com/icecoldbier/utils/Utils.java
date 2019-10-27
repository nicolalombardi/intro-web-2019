package com.icecoldbier.utils;

import javax.servlet.ServletContext;

public class Utils {
    public static String getServletContextPath(ServletContext context){
        String contextPath = context.getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }
        return contextPath;
    }
}
