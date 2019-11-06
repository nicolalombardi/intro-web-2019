package com.icecoldbier.filters;

import com.icecoldbier.persistence.dao.implementations.UserDAO;
import com.icecoldbier.persistence.entities.User;
import it.unitn.disi.wp.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.commons.persistence.dao.factories.DAOFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/restricted/*")
public class UsersFilter implements Filter {

    private void doBeforeProcessing(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            ServletContext servletContext = ((HttpServletRequest) request).getServletContext();

            String contextPath = servletContext.getContextPath();
            if (contextPath.endsWith("/")) {
                contextPath = contextPath.substring(0, contextPath.length() - 1);
            }
            request.setAttribute("contextPath", contextPath);

            HttpSession session = ((HttpServletRequest) request).getSession(false);
            User authenticatedUser = null;
            if (session != null) {
                authenticatedUser = (User) session.getAttribute("user");
            }

            if (authenticatedUser != null) {
                if(Integer.parseInt(request.getParameter("userId")) == authenticatedUser.getId()){                //Da fare???
                    String requestUrl = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
                    if (requestUrl == null) {
                        requestUrl = ((HttpServletRequest) request).getHeader("referer");
                    }
                    if (requestUrl == null) {
                        requestUrl = contextPath + "/homepage.html";
                    }
                    ((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response).encodeRedirectURL(requestUrl));
                    return;
                }
            }

            DAOFactory daoFactory = (DAOFactory) request.getServletContext().getAttribute("daoFactory");
            if (daoFactory == null) {
                throw new RuntimeException(new ServletException("Impossible to get dao factory for user storage system"));
            }
            try {
                UserDAO userDao = daoFactory.getDAO(UserDAO.class);
                if (userDao != null) {
                    request.setAttribute("userDao", userDao);
                }
            } catch (DAOFactoryException ex) {
                throw new RuntimeException(new ServletException("Impossible to get dao factory for user storage system", ex));
            }

        }

    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        doBeforeProcessing(servletRequest, servletResponse);
        Throwable problem = null;
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (IOException | ServletException | RuntimeException ex) {
            problem = ex;
            ex.printStackTrace();
        }


        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            ((HttpServletResponse) servletResponse).sendError(500, problem.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
