/*
 * Copyright (c) 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-starter
 * File Name: ResourceServerConfiguration.java
 * Author: gengwei.zheng
 * Date: 2020/6/6 下午12:51
 * LastModified: 2020/6/6 下午12:50
 */

package cn.herodotus.eurynome.athena.autoconfigure;

import cn.herodotus.eurynome.rest.properties.ApplicationProperties;
import cn.herodotus.eurynome.rest.properties.RestProperties;
import cn.herodotus.eurynome.security.authentication.access.HerodotusAccessDecisionManager;
import cn.herodotus.eurynome.security.authentication.access.HerodotusAccessDeniedHandler;
import cn.herodotus.eurynome.security.authentication.access.HerodotusSecurityMetadataSource;
import cn.herodotus.eurynome.security.authentication.access.RequestMappingScanner;
import cn.herodotus.eurynome.security.properties.SecurityProperties;
import cn.herodotus.eurynome.security.response.HerodotusAuthenticationEntryPoint;
import cn.herodotus.eurynome.security.strategy.LocalCacheSecurityMetadata;
import cn.herodotus.eurynome.security.strategy.SecurityMetadataStorage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.List;

/**
 * <p>Description: 通用的ResourceService配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/6/6 10:49
 */
@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerAutoConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private ResourceServerProperties resourceServerProperties;
    @Autowired
    private DefaultAccessTokenConverter defaultAccessTokenConverter;

    /**
     * 服务自身权限验证所需的Security Metadata存储配置
     *
     * 服务权限验证逻辑：
     * 1、配置服务本地Security Metadata存储
     */
    @Bean
    @ConditionalOnMissingBean(SecurityMetadataStorage.class)
    public SecurityMetadataStorage securityMetadataStorage() {
        LocalCacheSecurityMetadata localCacheSecurityMetadata = new LocalCacheSecurityMetadata();
        log.debug("[Eurynome] |- Bean [Security Metadata Local Storage] Auto Configure.");
        return localCacheSecurityMetadata;
    }

    /**
     * 自定义注解扫描器
     *
     * 服务权限验证逻辑
     * 2、根据配置扫描服务注解，并存入服务本地Security Metadata存储
     */
    @Bean
    @ConditionalOnMissingBean(RequestMappingScanner.class)
    @ConditionalOnBean(SecurityMetadataStorage.class)
    public RequestMappingScanner requestMappingScanner(RestProperties restProperties, ApplicationProperties applicationProperties, SecurityMetadataStorage securityMetadataStorage) {
        RequestMappingScanner requestMappingScan = new RequestMappingScanner(restProperties, applicationProperties, securityMetadataStorage, EnableResourceServer.class);
        log.debug("[Eurynome] |- Bean [Request Mapping Scan] Auto Configure.");
        return requestMappingScan;
    }

    /**
     * 权限信息存储器
     */
    @Bean
    @ConditionalOnMissingBean(HerodotusSecurityMetadataSource.class)
    @ConditionalOnBean(RequestMappingScanner.class)
    public HerodotusSecurityMetadataSource herodotusSecurityMetadataSource() {
        HerodotusSecurityMetadataSource herodotusSecurityMetadataSource = new HerodotusSecurityMetadataSource();
        herodotusSecurityMetadataSource.setSecurityMetadataStorage(securityMetadataStorage());
        herodotusSecurityMetadataSource.setSecurityProperties(securityProperties);
        log.debug("[Eurynome] |- Bean [Security Metadata Source] Auto Configure.");
        return herodotusSecurityMetadataSource;
    }

    /**
     * 权限信息判断器
     *
     * 服务权限验证逻辑：
     * 5、捕获用户访问的请求信息，从权限存储其中查找是否有对应的Security Metadata信息。如果有，就说明是权限管控请求；如果没有，就说明是非权限管控请求。
     * 6、权限控制主要针对权限管控请求，把这个请求对应的配置信息，与用户Token中带的权限信息进行比较。如果用户Token中没有这个权限信息，说明该用户就没有被授权。
     */
    @Bean
    public HerodotusAccessDecisionManager herodotusAccessDecisionManager() {
        HerodotusAccessDecisionManager herodotusAccessDecisionManager = new HerodotusAccessDecisionManager();
        log.debug("[Eurynome] |- Bean [Access Decision Manager] Auto Configure.");
        return herodotusAccessDecisionManager;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        log.debug("[Eurynome] |- Bean [Core Resource Server] Auto Configure.");

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        // @formatter:off
        http.authorizeRequests()
                .antMatchers(getWhitelist()).permitAll()
                // 指定监控访问权限
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                        fsi.setAccessDecisionManager(herodotusAccessDecisionManager());
                        fsi.setSecurityMetadataSource(herodotusSecurityMetadataSource());
                        return fsi;
                    }
                })
                .and() // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                .exceptionHandling()
                .accessDeniedHandler(new HerodotusAccessDeniedHandler())
                .authenticationEntryPoint(new HerodotusAuthenticationEntryPoint());

        // 关闭csrf 跨站（域）攻击防控
        http.csrf().disable();
        // @formatter:on
    }

    private String[] getWhitelist() {
        if (ObjectUtils.isNotEmpty(securityProperties)) {
            List<String> whitelist = securityProperties.getInterceptor().getWhitelist();
            if (CollectionUtils.isNotEmpty(whitelist)) {
                log.info("[Eurynome] |- OAuth2 Fetch The Resource White List.");
                return whitelist.toArray(new String[]{});
            }
        }

        log.warn("[Eurynome] |- OAuth2 Can not Fetch The Resource White List Configurations.");
        return new String[]{};
    }
}
