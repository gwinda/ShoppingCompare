package com.workspace.server.configuration

import com.workspace.server.exception.ContentFormatException
import com.workspace.server.interceptor.ContentFormatInterceptor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
@Order(3)
class ContentFormatConfiguration extends WebMvcConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(ContentFormatConfiguration.class)

    /**
     * support content format
     */
    @Value('${workspace.server.output-format-types}')
    private String outputFormatTypes

    @Value('${workspace.server.default-output-format-type}')
    private String defaultOutputFormatType

    @Bean
    ContentFormatInterceptor contentFormatInterceptor() {
        ContentFormatInterceptor contentFormatInterceptor = new ContentFormatInterceptor()
        contentFormatInterceptor.setOutputFormatTypes(outputFormatTypes)
        contentFormatInterceptor.setDefaultOutputFormatType(defaultOutputFormatType)
        return contentFormatInterceptor
    }

    @Override
    void addInterceptors(InterceptorRegistry registry) {
        if(defaultOutputFormatType in outputFormatTypes.split(',')){
            logger.info("[workspace-server] Support Content Format Types: ${outputFormatTypes.split(',')}")
            logger.info("[workspace-server] Default Content Format Type: ${defaultOutputFormatType}")
        } else{
            logger.error("[workspace-server] Default Format Type: ${defaultOutputFormatType} Is Not In Support Content Format Types, Please Check Configuration!")
            throw new ContentFormatException()
        }

        logger.info('[workspace-server] Registration Content Format Interceptor')
        registry.addInterceptor(contentFormatInterceptor())
    }

}
