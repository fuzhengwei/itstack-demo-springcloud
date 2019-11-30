package org.itstack.demo.config;

import org.itstack.demo.router.RouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 路由配置
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@Configuration
public class ZuulConfig {

    @Autowired
    private ZuulProperties zuulProperties;
    @Autowired
    private ServerProperties server;

    @Bean
    public RouteLocator routeLocator() {
        return new RouteLocator(this.server.getServlet().getPath(), this.zuulProperties);
    }

}
