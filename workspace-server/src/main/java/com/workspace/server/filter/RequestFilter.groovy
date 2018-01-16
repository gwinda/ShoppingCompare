package com.workspace.server.filter

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component
import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest

@Component
@WebFilter(filterName = "reqFilter", urlPatterns = "/*")
@Slf4j
class RequestFilter implements Filter {

    @Override
    void init(FilterConfig filterConfig) throws ServletException {
        log.info('[workspace-server] Request Filter Initialize')
    }

    @Override
    void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest
        log.info("Request From Address: ${servletRequest.getRemoteAddr()} Request URI: ${httpServletRequest.getRequestURI()}")
        filterChain.doFilter(servletRequest, servletResponse)
    }

    @Override
    void destroy() {

    }

}
