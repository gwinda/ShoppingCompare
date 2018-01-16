package com.workspace.server.controller

import com.workspace.server.interceptor.ContentFormatInterceptor
import com.workspace.server.util.ContentFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @Autowired MessageSource messageSource

    @GetMapping('/')
    String hello(@RequestAttribute(value = ContentFormatInterceptor.CONTENT_FORMATTER) ContentFormatter contentFormatter){
        contentFormatter.content().'workspaceContent'{
            'resultCode' '200'
            'state' 'Server is running'
        }
        return contentFormatter.toString()
    }

}
