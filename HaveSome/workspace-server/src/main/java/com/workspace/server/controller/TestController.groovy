package com.workspace.server.controller

import com.workspace.server.exception.ServerException
import org.quartz.Scheduler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Controller
class TestController {

    @Autowired MessageSource messageSource
    @Autowired

    //加入Qulifier注解，通过名称注入bean
    @Autowired @Qualifier("Scheduler")
    private Scheduler scheduler;
    @GetMapping('/')
    String hello(){

        return '/index.html'
    }

}