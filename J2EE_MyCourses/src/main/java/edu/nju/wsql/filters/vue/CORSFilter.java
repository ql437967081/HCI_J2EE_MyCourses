package edu.nju.wsql.filters.vue;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"}, filterName = "CORSFilter")
public class CORSFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) resp;
        httpResponse.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        httpResponse.addHeader("Access-Control-Allow-Credentials","true");
        if( "OPTIONS".equals(httpRequest.getMethod())){
            return;
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
