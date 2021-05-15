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
 * Project Name: eurynome-cloud-athena
 * Module Name: eurynome-cloud-athena-starter
 * File Name: AutoConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/05/15 08:26:15
 */

package cn.herodotus.eurynome.athena.autoconfigure;

import cn.herodotus.eurynome.athena.kernel.configuration.AthenaKernelConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.PostConstruct;

/**
 * <p>Project: eurynome-cloud-athena </p>
 * <p>File: AutoConfiguration.java </p>
 *
 * <p>Description: Starter Auto 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/12/29 20:52
 */
@Slf4j
@Configuration
@Import(AthenaKernelConfiguration.class)
public class AutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.info("[Eurynome] |- Starter [Athena Starter] Auto Configure.");
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CorsFilter corsFilter() {

        log.debug("[Eurynome] |- Bean [Cors Filter] Auto Configure.");

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
