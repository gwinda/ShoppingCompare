package com.workspace.server.configuration

import com.alibaba.druid.pool.DruidDataSource
import com.alibaba.druid.support.http.StatViewServlet
import com.alibaba.druid.support.http.WebStatFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import java.sql.SQLException

/**
 * Alibaba Druid Data Source Framework configuration
 * @author Burgess Li
 * @date 2017/12/27
 */
@Configuration
@Order(1)
class DruidDateSourceConfiguration {

    private Logger logger = LoggerFactory.getLogger(DruidDateSourceConfiguration.class)

    @Value('${spring.datasource.url}')
    private String dbUrl

    @Value('${spring.datasource.username}')
    private String username

    @Value('${spring.datasource.password}')
    private String password

    @Value('${spring.datasource.driver-class-name}')
    private String driverClassName

    @Value('${spring.datasource.initialSize}')
    private int initialSize

    @Value('${spring.datasource.minIdle}')
    private int minIdle

    @Value('${spring.datasource.maxActive}')
    private int maxActive

    @Value('${spring.datasource.maxWait}')
    private int maxWait

    @Value('${spring.datasource.timeBetweenEvictionRunsMillis}')
    private int timeBetweenEvictionRunsMillis

    @Value('${spring.datasource.minEvictableIdleTimeMillis}')
    private int minEvictableIdleTimeMillis

    @Value('${spring.datasource.validationQuery}')
    private String validationQuery

    @Value('${spring.datasource.testWhileIdle}')
    private boolean testWhileIdle

    @Value('${spring.datasource.testOnBorrow}')
    private boolean testOnBorrow

    @Value('${spring.datasource.testOnReturn}')
    private boolean testOnReturn

    @Value('${spring.datasource.filters}')
    private String filters

    @Bean(initMethod = 'init', destroyMethod = 'close')
    DruidDataSource druidDataSource() {
        logger.info('[workspace-server] Initialize Druid DataSource')
        DruidDataSource datasource = new DruidDataSource()
        datasource.setUrl(dbUrl)
        datasource.setUsername(username)
        datasource.setPassword(password)
        datasource.setDriverClassName(driverClassName)
        datasource.setInitialSize(initialSize)
        datasource.setMinIdle(minIdle)
        datasource.setMaxActive(maxActive)
        datasource.setMaxWait(maxWait)
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis)
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis)
        datasource.setValidationQuery(validationQuery)
        datasource.setTestWhileIdle(testWhileIdle)
        datasource.setTestOnBorrow(testOnBorrow)
        datasource.setTestOnReturn(testOnReturn)
        try {
            datasource.setFilters(filters)
        } catch (SQLException e) {
            logger.error('druid configuration initialization filter', e)
        }
        return datasource
    }

    private static final String DRUID_MANAGER_USERNAME = 'admin'
    private static final String DRUID_MANAGER_PASSWORD = 'admin'

    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        logger.info('[workspace-server] Registration Druid Servlet')
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new StatViewServlet(), '/druid/*')
        registrationBean.addInitParameter('allow', '127.0.0.1')
        registrationBean.addInitParameter('deny', '192.168.31.234')
        registrationBean.addInitParameter('loginUsername', DRUID_MANAGER_USERNAME)
        registrationBean.addInitParameter('loginPassword', DRUID_MANAGER_PASSWORD)
        registrationBean.addInitParameter('resetEnable', 'false')
        return registrationBean
    }

    @Bean
    public FilterRegistrationBean druidWebStatViewFilter() {
        logger.info('[workspace-server] Registration Druid Filter')
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new WebStatFilter())
        registrationBean.addInitParameter('urlPatterns', '/*')
        registrationBean.addInitParameter('exclusions', '*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*')
        return registrationBean
    }

}
