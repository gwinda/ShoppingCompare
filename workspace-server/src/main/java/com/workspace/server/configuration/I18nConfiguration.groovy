package com.workspace.server.configuration

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.i18n.CookieLocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver

@Configuration
@Order(2)
class I18nConfiguration extends WebMvcConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(I18nConfiguration.class)

    @Bean
    LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver()
        cookieLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE)
        return cookieLocaleResolver
    }

    @Bean
    LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor()
        lci.setParamName("lang")
        return lci
    }

    @Override
    void addInterceptors(InterceptorRegistry registry) {
        logger.info('[workspace-server] Registration I18N Interceptor')
        registry.addInterceptor(localeChangeInterceptor())
    }

}
