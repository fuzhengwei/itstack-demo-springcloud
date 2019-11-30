package org.itstack.demo.router;

import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 微信公众号：bugstack虫洞栈 | 专注原创技术专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
public class RouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    private ZuulProperties properties;

    public RouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulRoute> routesMap = new LinkedHashMap<String, ZuulRoute>();
        //从application.properties中加载路由信息
        routesMap.putAll(super.locateRoutes());
        //从db中加载路由信息
        routesMap.putAll(routesConfigGroup());
        //优化一下配置
        LinkedHashMap<String, ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            // Prepend with slash if not already present.
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }
        return values;
    }

    /**
     * 路由配置组，可以从数据库中读取
     * 基本配置与写在文件中配置类似，如下；
     * #  routes:
     * #    api-a:
     * #      path: /route-a/**
     * #      serviceId: itstack-demo-springcloud-feign
     * #    api-b:
     * #      path: /route-b/**
     * #      serviceId: itstack-demo-springcloud-ribbon
     * @return 配置组内容
     */
    private Map<String, ZuulRoute> routesConfigGroup() {
        Map<String, ZuulRoute> routes = new LinkedHashMap<>();

        ZuulRoute zuulRoute = new ZuulRoute();
        zuulRoute.setId("route-a");
        zuulRoute.setPath("/route-a/**");
        zuulRoute.setServiceId("itstack-demo-springcloud-feign");
        // 如果使用了注册中心，那么可以根据serviceId进行访问。
        // zuulRoute.setUrl("http://localhost:9001");
        zuulRoute.setRetryable(false);
        zuulRoute.setStripPrefix(true);
        zuulRoute.setSensitiveHeaders(new HashSet<>());

        routes.put(zuulRoute.getPath(), zuulRoute);

        return routes;
    }

}
