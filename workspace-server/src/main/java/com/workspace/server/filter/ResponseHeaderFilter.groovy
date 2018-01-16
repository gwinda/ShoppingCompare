package com.workspace.server.filter

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletResponse

@Component
@WebFilter(filterName = "rspHeaderFilter", urlPatterns = "/*")
class ResponseHeaderFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(ResponseHeaderFilter.class)

    @Override
    void init(FilterConfig filterConfig) throws ServletException {
        logger.info('[workspace-server] Response Header Filter Initialize')
    }

    @Override
    void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse
        httpResponse.setHeader("Access-Control-Allow-Origin", '*')
        httpResponse.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept")
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true")
        filterChain.doFilter(servletRequest, httpResponse)
    }

    @Override
    void destroy() {

    }

}
