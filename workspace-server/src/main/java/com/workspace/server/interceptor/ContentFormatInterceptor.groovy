package com.workspace.server.interceptor

import com.workspace.server.exception.ContentFormatException
import com.workspace.server.util.ContentFormatter
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ContentFormatInterceptor implements HandlerInterceptor {

    public static final String DEFAULT_PARAM_NAME = 'format'
    public static final String CONTENT_FORMATTER = 'workspace.server.CONTENT_FORMATTER'
    String paramName = DEFAULT_PARAM_NAME

    String outputFormatTypes
    String defaultOutputFormatType

    @Override
    boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        ContentFormatter contentFormatter = new ContentFormatter(outputFormatTypes: outputFormatTypes, defaultOutputFormatType: defaultOutputFormatType)
        httpServletRequest.setAttribute(CONTENT_FORMATTER, contentFormatter)
        String formatType = httpServletRequest.getParameter(paramName)
        if(formatType!=null && !formatType?.trim()?.isEmpty()){
            switch (formatType?.trim()?.toUpperCase()){
                case ContentFormatter.ContentType.TYPE_JSON:
                    contentFormatter.initializeBuilder(ContentFormatter.ContentType.TYPE_JSON)
                    return true
                case ContentFormatter.ContentType.TYPE_XML:
                    contentFormatter.initializeBuilder(ContentFormatter.ContentType.TYPE_XML)
                    return true
                default:
                    throw new ContentFormatException()
            }
        }else{
            // use default format type
            contentFormatter.initializeBuilder(defaultOutputFormatType)
            return true
        }
    }

    @Override
    void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
