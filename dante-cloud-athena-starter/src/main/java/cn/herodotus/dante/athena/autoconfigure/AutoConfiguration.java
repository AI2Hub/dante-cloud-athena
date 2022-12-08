/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: dante-cloud-athena
 * Module Name: dante-cloud-athena-starter
 * File Name: AutoConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/05/15 08:26:15
 */

package cn.herodotus.dante.athena.autoconfigure;

import cn.herodotus.dante.athena.kernel.configuration.AthenaKernelConfiguration;
import cn.herodotus.engine.assistant.core.definition.constants.SymbolConstants;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


/**
 * <p>Project: dante-cloud-athena </p>
 * <p>File: AutoConfiguration.java </p>
 *
 * <p>Description: Starter Auto 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/12/29 20:52
 */
@Configuration(proxyBeanMethods = false)
@Import({AthenaKernelConfiguration.class, CorsConfiguration.class})
public class AutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AutoConfiguration.class);

    private static final String[] ACCESS_CONTROL_ALLOW_METHODS = new String[]{HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name()};

    @PostConstruct
    public void postConstruct() {
        log.info("[dante] |- Starter [Athena Starter] Auto Configure.");
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CorsFilter corsFilter() {

        log.debug("[dante] |- Bean [Cors Filter] Auto Configure.");

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedMethod(StringUtils.join(ACCESS_CONTROL_ALLOW_METHODS, SymbolConstants.COMMA));
        corsConfiguration.addAllowedHeader("x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        CorsFilter corsFilter = new CorsFilter(urlBasedCorsConfigurationSource);
        corsFilter.setCorsProcessor((configuration, request, response) -> {
            if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
            }
            return true;
        });

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
