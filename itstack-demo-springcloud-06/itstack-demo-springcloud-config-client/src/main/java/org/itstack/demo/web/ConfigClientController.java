package org.itstack.demo.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 微信公众号：bugstack虫洞栈 | 沉淀、分享、成长，专注于原创专题案例
 * 论坛：http://bugstack.cn
 * Create by 付政委 on @2019
 */
@RestController
@RefreshScope
public class ConfigClientController {

    @Value("${info.profile:error}")
    private String profile;

    @GetMapping("/config")
    public Mono<String> config() {
        return Mono.justOrEmpty(profile);
    }

}
