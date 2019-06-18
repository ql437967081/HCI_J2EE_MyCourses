package edu.nju.wsql.filters;

import edu.nju.wsql.filters.utils.LoginRoleUtil;
import edu.nju.wsql.model.enums.UserType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*"}, filterName = "AdminLoginFilter")
public class AdminLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if (LoginRoleUtil.filterLoginRole(req, resp, UserType.ADMINISTRATOR))
            chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
