package com.workspace.server.handler

import com.workspace.server.exception.ContentFormatException
import com.workspace.server.interceptor.ContentFormatInterceptor
import com.workspace.server.util.ContentFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ContentFormatExceptionHandler {

    @Autowired MessageSource messageSource

    @Value('${workspace.server.default-output-format-type}')
    private String defaultOutputFormatType

    @ExceptionHandler(ContentFormatException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    String handler(@RequestAttribute(value = ContentFormatInterceptor.CONTENT_FORMATTER) ContentFormatter contentFormatter, ContentFormatException contentFormatException, HttpServletRequest request){
        contentFormatter.initializeDefaultBuilder()
        contentFormatter.content().'workspaceContent' {
            'resultCode' contentFormatException.exceptionCode
            'exceptionInformation' messageSource.getMessage(contentFormatException.toString(), null, LocaleContextHolder.getLocale())
        }
        return contentFormatter.toString()
    }

}
