package edu.nju.wsql.filters;

import edu.nju.wsql.filters.utils.LoginRoleUtil;
import edu.nju.wsql.model.enums.UserType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = {"/student/*"}, filterName = "StudentLoginFilter")
public class StudentLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if (LoginRoleUtil.filterLoginRole(req, resp, UserType.STUDENT))
            chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
